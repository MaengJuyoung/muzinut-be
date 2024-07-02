package nuts.muzinut.controller.board;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jdk.jfr.Event;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import nuts.muzinut.domain.board.EventBoard;
import nuts.muzinut.domain.board.FreeBoard;
import nuts.muzinut.domain.member.User;
import nuts.muzinut.dto.MessageDto;
import nuts.muzinut.dto.board.event.EventBoardForm;
import nuts.muzinut.dto.board.event.EventBoardsDto;
import nuts.muzinut.dto.board.free.DetailFreeBoardDto;
import nuts.muzinut.dto.board.free.FreeBoardForm;
import nuts.muzinut.dto.board.free.FreeBoardsDto;
import nuts.muzinut.exception.BoardNotExistException;
import nuts.muzinut.exception.NoUploadFileException;
import nuts.muzinut.exception.NotFoundMemberException;
import nuts.muzinut.repository.board.EventBoardRepository;
import nuts.muzinut.service.board.EventBoardService;
import nuts.muzinut.service.board.FileStore;
import nuts.muzinut.service.board.FreeBoardService;
import nuts.muzinut.service.member.UserService;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.*;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.URI;
import java.util.Map;
import java.util.Set;

import static nuts.muzinut.controller.board.FileType.STORE_FILENAME;

@Slf4j
//@Controller
@RequestMapping("/community/event-boards")
@RequiredArgsConstructor
public class EventBoardController {

    private final UserService userService;
    private final FileStore fileStore;
    private final FreeBoardService freeBoardService;
    private final EventBoardService eventBoardService;
    private final ObjectMapper objectMapper;

    /**
     * 이벤트 게시판 생성
     * @param quillFile: 리액트 퀼 파일
     * @param img: 섬네일 이미지
     * @param form: 제목
     * @throws NoUploadFileException: 업로드 할 파일이 없는 경우
     * @throws IOException
     */
    @PreAuthorize("hasAnyRole('ADMIN')")
    @PostMapping
    public ResponseEntity<MessageDto> createBoard(
            @RequestPart MultipartFile quillFile, @RequestPart MultipartFile img,
            @Validated @RequestPart EventBoardForm form) throws IOException {
        User user = userService.getUserWithUsername()
                .orElseThrow(() -> new NotFoundMemberException("회원이 아닙니다."));

        EventBoard eventBoard = new EventBoard(form.getTitle());
        eventBoard.addBoard(user);

        Map<FileType, String> filename = fileStore.storeFile(quillFile); //이벤트 게시판 퀼 파일 저장
        eventBoard.setFilename(filename.get(STORE_FILENAME)); //저장 파일 이름 설정
        Map<FileType, String> imgFilename = fileStore.storeFile(img); //이벤트 게시판 썸네일 이미지 파일 저장
        eventBoard.setImg(imgFilename.get(STORE_FILENAME)); //저장 파일 이름 설정

        eventBoardService.save(eventBoard); //이벤트 게시판 저장

        HttpHeaders header = new HttpHeaders();
        header.setLocation(URI.create("/community/event-boards/" + eventBoard.getId())); //생성한 게시판으로 리다이렉트

        return ResponseEntity.status(HttpStatus.MOVED_PERMANENTLY)
                .headers(header)
                .body(new MessageDto("이벤트 게시판이 생성되었습니다"));
    }

    //특정 게시판 조회
    @GetMapping(value = "/{id}", produces = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<MultiValueMap<String, Object>> getDetailFreeBoard(@PathVariable Long id) throws JsonProcessingException {
        MultiValueMap<String, Object> formData = new LinkedMultiValueMap<String, Object>();

        User findUser = userService.getUserWithUsername().orElse(null);
        DetailFreeBoardDto detailFreeBoardDto = freeBoardService.detailFreeBoard(id, findUser);

        String jsonString = objectMapper.writeValueAsString(detailFreeBoardDto);

        // JSON 데이터를 Multipart-form 데이터에 추가
        HttpHeaders jsonHeaders = new HttpHeaders();
        jsonHeaders.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> jsonEntity = new HttpEntity<>(jsonString, jsonHeaders);
        formData.add("json_data", jsonEntity);

        //해당 게시판의 quill 파일 추가
        String quillFilename = detailFreeBoardDto.getQuillFilename();
        String fullPath = fileStore.getFullPath(quillFilename);
        formData.add("quillFile", new FileSystemResource(fullPath));

        //해당 게시판의 작성자, 댓글 & 대댓글 작성자의 프로필 추가
        Set<String> profileImages = freeBoardService.getProfileImages(detailFreeBoardDto.getProfileImg(),
                detailFreeBoardDto.getComments());
        fileStore.setImageHeaderWithData(profileImages, formData);

        return new ResponseEntity<MultiValueMap<String, Object>>(formData, HttpStatus.OK);
    }

    //모든 이벤트 게시판 조회
    @GetMapping()
    public ResponseEntity<EventBoardsDto> getFreeBoards(
            @RequestParam(value = "page", defaultValue = "0") int page) {
        try {
            EventBoardsDto eventBoards = eventBoardService.getEventBoards(page);
            return ResponseEntity.ok()
                    .body(eventBoards);
        } catch (BoardNotExistException e) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT)
                    .body(null);
        }
    }
    
    //라운지 게시판 수정
    @PreAuthorize("hasAnyRole('USER','ADMIN')")
    @PutMapping("/{id}")
    public ResponseEntity<MessageDto> updateFreeBoard(
            @RequestPart MultipartFile quillFile, @RequestPart FreeBoardForm freeBoardForm, @PathVariable Long id) throws IOException {
        User user = userService.getUserWithUsername()
                .orElseThrow(() -> new NotFoundMemberException("닉네임을 설정해주세요"));
        boolean isAuthorized = freeBoardService.checkAuth(id, user);

        if (isAuthorized) {
            FreeBoard freeBoard = freeBoardService.getFreeBoard(id);
            //자유 게시판 파일 저장 및 기존 퀼 파일 삭제
            String changeFilename = fileStore.updateFile(quillFile, freeBoard.getFilename());
            freeBoardService.updateFreeBoard(freeBoard.getId(), freeBoardForm.getTitle(), changeFilename); //자유 게시판 저장
            HttpHeaders header = new HttpHeaders();
            header.setLocation(URI.create("/community/free-boards/" + freeBoard.getId())); //수정한 게시판으로 리다이렉트

            return ResponseEntity.status(HttpStatus.MOVED_PERMANENTLY)
                    .headers(header)
                    .body(new MessageDto("자유 게시판이 수정되었습니다"));

        }
        return ResponseEntity.status(HttpStatus.FORBIDDEN)
                .body(null);
    }


    //자유 게시판 삭제
    @PreAuthorize("hasAnyRole('USER','ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<MessageDto> deleteFreeBoard(@PathVariable Long id) throws IOException {
        User user = userService.getUserWithUsername()
                .orElseThrow(() -> new NotFoundMemberException("닉네임을 설정해주세요"));
        boolean isAuthorized = freeBoardService.checkAuth(id, user);
        if (isAuthorized) {
            FreeBoard freeBoard = freeBoardService.getFreeBoard(id); //게시판 조회
            fileStore.deleteFile(freeBoard.getFilename()); //자유 게시판의 파일 삭제
            freeBoardService.deleteFreeBoard(id); //자유 게시판 삭제

            HttpHeaders header = new HttpHeaders();
            header.setLocation(URI.create("/community/free-boards")); //자유 게시판 홈페이지로 리다이렉트

            return ResponseEntity.status(HttpStatus.MOVED_PERMANENTLY)
                    .headers(header)
                    .body(new MessageDto("자유 게시판이 삭제되었습니다"));

        }
        return ResponseEntity.status(HttpStatus.FORBIDDEN)
                .body(null);
    }


    //for test
    @GetMapping(value = "/multipartdata", produces = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<MultiValueMap<String, Object>> gerMultipartData() {
        MultiValueMap<String, Object> formData = new LinkedMultiValueMap<String, Object>();
        formData.add("first_name",  "ganesh");
        formData.add("last_name", "patil");
        formData.add("file-data_1", new FileSystemResource("C:\\Users\\dnjswo\\study\\project\\muzinut\\file\\sample1.png"));
        return new ResponseEntity<MultiValueMap<String, Object>>(formData, HttpStatus.OK);
    }

}