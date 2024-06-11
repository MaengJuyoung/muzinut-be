package nuts.muzinut.repository.board;

import nuts.muzinut.domain.board.admin.AdminBoard;
import nuts.muzinut.domain.board.admin.AdminUploadFile;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional
class AdminUploadFileRepositoryTest {

    @Autowired
    AdminBoardRepository adminBoardRepository;
    @Autowired
    AdminUploadFileRepository uploadFileRepository;

    @Test
    void save() {
        //given
        AdminBoard adminBoard = new AdminBoard();
        adminBoardRepository.save(adminBoard);

        AdminUploadFile adminUploadFile = new AdminUploadFile();
        adminUploadFile.addFiles(adminBoard);

        //when
        uploadFileRepository.save(adminUploadFile);

        //then
        Optional<AdminUploadFile> result = uploadFileRepository.findById(adminUploadFile.getId());
        assertThat(result.get()).isEqualTo(adminUploadFile); // 저장된 파일 엔티티가 동일한지 확인
        assertThat(result.get().getAdminBoard()).isEqualTo(adminBoard); // 파일 엔티티가 연결된 게시판 엔티티가 동일한지 확인
    }

    @Test
    void delete() {
        //given
        AdminBoard adminBoard = new AdminBoard();
        adminBoardRepository.save(adminBoard);

        AdminUploadFile adminUploadFile = new AdminUploadFile();
        adminUploadFile.addFiles(adminBoard);
        uploadFileRepository.save(adminUploadFile);

        //when
        uploadFileRepository.delete(adminUploadFile);

        //then
        Optional<AdminUploadFile> result = uploadFileRepository.findById(adminUploadFile.getId());
        assertThat(result.isEmpty()).isTrue(); // 삭제된 파일 엔티티가 더 이상 존재하지 않는지 확인
    }

    // adminBoard 가 삭제되면 게시판에 해당하는 첨부파일 모두 삭제
    @Test
    void deleteAdminBoard() {
        //given
        AdminBoard adminBoard = new AdminBoard();
        adminBoardRepository.save(adminBoard);

        AdminUploadFile adminUploadFile = new AdminUploadFile();
        adminUploadFile.addFiles(adminBoard);
        uploadFileRepository.save(adminUploadFile);

        //when
        adminBoardRepository.delete(adminBoard);

        //then
        Optional<AdminUploadFile> result = uploadFileRepository.findById(adminUploadFile.getId());
        assertThat(result.isEmpty()).isTrue(); // 게시판이 삭제되면 연결된 파일 엔티티도 삭제되었는지 확인
    }
}
