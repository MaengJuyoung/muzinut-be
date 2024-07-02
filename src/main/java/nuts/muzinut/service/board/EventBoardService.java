package nuts.muzinut.service.board;

import com.querydsl.core.Tuple;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import nuts.muzinut.domain.board.Board;
import nuts.muzinut.domain.board.EventBoard;
import nuts.muzinut.domain.board.FreeBoard;
import nuts.muzinut.domain.board.QEventBoard;
import nuts.muzinut.domain.member.User;
import nuts.muzinut.dto.board.DetailBaseDto;
import nuts.muzinut.dto.board.event.DetailEventBoardDto;
import nuts.muzinut.dto.board.event.EventBoardsDto;
import nuts.muzinut.dto.board.event.EventBoardsForm;
import nuts.muzinut.dto.board.free.DetailFreeBoardDto;
import nuts.muzinut.dto.board.free.FreeBoardsDto;
import nuts.muzinut.dto.board.free.FreeBoardsForm;
import nuts.muzinut.exception.BoardNotExistException;
import nuts.muzinut.exception.NotFoundEntityException;
import nuts.muzinut.repository.board.EventBoardRepository;
import nuts.muzinut.repository.board.query.EventBoardQueryRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static nuts.muzinut.domain.board.QBoard.board;
import static nuts.muzinut.domain.board.QFreeBoard.freeBoard;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class EventBoardService extends DetailCommon{

    private final EventBoardRepository eventBoardRepository;
    private final EventBoardQueryRepository queryRepository;

    public EventBoard save(EventBoard eventBoard) {
        return eventBoardRepository.save(eventBoard);
    }

    public DetailEventBoardDto getDetailEventBoard(Long boardId) {
        return null;
    }

    /**
     * 특정 이벤트 게시판 조회
     * tuple (board, eventBoard, like.count)
     */
    public DetailEventBoardDto detailEventBoard(Long boardId, User user) {
        List<Tuple> result = queryRepository.getDetailEventBoard(boardId, user);

        log.info("tuple: {}", result);
        if (result.isEmpty()) {
            return null;
        }

        Tuple first = result.getFirst();
        Board findBoard = first.get(board);
        EventBoard eventBoard = first.get(QEventBoard.eventBoard);
        log.info("comment size: {}", eventBoard.getComments().size());
        int view = eventBoard.addView();

        if (findBoard == null) {
            return null;
        }

        DetailEventBoardDto detailEventBoardDto =
                new DetailEventBoardDto(eventBoard.getId() ,eventBoard.getTitle(),
                        eventBoard.getUser().getNickname(), view, eventBoard.getFilename(), eventBoard.getUser().getProfileImgFilename());

        Long likeCount = first.get(2, Long.class);
        DetailBaseDto detailBaseDto = first.get(3, DetailBaseDto.class);
        detailEventBoardDto.setLikeCount(likeCount); //좋아요 수 셋팅
        detailEventBoardDto.setBoardLikeStatus(detailBaseDto.getBoardLikeStatus()); //사용자가 특정 게시판의 좋아요를 눌렀는지 여부
        detailEventBoardDto.setIsBookmark(detailBaseDto.getIsBookmark()); //사용자가 특정 게시판을 북마크했는지 여부

        //게시판 댓글 & 대댓글 셋팅
        detailEventBoardDto.setComments(setCommentsAndReplies(user, findBoard));
        return detailEventBoardDto;
    }

    //모든 게시판 조회
    public EventBoardsDto getEventBoards(int startPage){
        //Todo 페이지 수 결정하기
        PageRequest pageRequest = PageRequest.of(startPage, 10, Sort.by(Sort.Direction.DESC, "createdDt")); //Todo 한 페이지에 가져올 게시판 수를 정하기
        Page<EventBoard> page = eventBoardRepository.findAll(pageRequest);
        List<EventBoard> eventBoards = page.getContent();

        if (eventBoards.isEmpty()) {
            throw new BoardNotExistException("기재된 어드민 게시판이 없습니다.");
        }

        EventBoardsDto boardsDto = new EventBoardsDto();
        boardsDto.setPaging(page.getNumber(), page.getTotalPages(), page.getTotalElements()); //paging 처리
        for (EventBoard e : eventBoards) {
            boardsDto.getFreeBoardsForms().add(new EventBoardsForm(e.getId() ,e.getTitle(), e.getUser().getNickname(),
                    e.getCreatedDt(), e.getLikes().size(), e.getView()));
        }
        return boardsDto;
    }
}