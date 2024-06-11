package nuts.muzinut.repository.board;

import jakarta.persistence.EntityManager;
import nuts.muzinut.domain.board.admin.AdminBoard;
import nuts.muzinut.domain.member.Member;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class AdminBoardRepositoryTest {

    @Autowired
    AdminBoardRepository adminBoardRepository;
    @Autowired
    EntityManager em;

    @Test
    void save() {
        // given
        Member member = new Member();
        AdminBoard adminBoard = new AdminBoard(member, "Initial Content");

        // when
        adminBoardRepository.save(adminBoard);

        // then
        Optional<AdminBoard> findAdminBoard = adminBoardRepository.findById(adminBoard.getId());
        assertTrue(findAdminBoard.isPresent()); // 저장된 엔티티가 존재하는지 확인
        assertEquals(adminBoard, findAdminBoard.get()); // 저장된 엔티티와 조회된 엔티티가 동일한지 확인
    }

    @Test
    void delete() {
        // given
        Member member = new Member();
        AdminBoard adminBoard = new AdminBoard(member, "Content to be deleted");
        adminBoardRepository.save(adminBoard);

        // when
        adminBoardRepository.delete(adminBoard);

        // then
        Optional<AdminBoard> findAdminBoard = adminBoardRepository.findById(adminBoard.getId());
        assertTrue(findAdminBoard.isEmpty()); // 삭제된 엔티티가 더 이상 존재하지 않는지 확인
    }

    @Test
    void update() {
        // given
        Member member = new Member();
        AdminBoard adminBoard = new AdminBoard(member, "Original Content");
        adminBoardRepository.save(adminBoard);

        // when
        LocalDateTime beforeUpdateTime = adminBoard.getModified_dt();
        adminBoard.setContent("Updated Content");
        adminBoardRepository.save(adminBoard);
        em.flush();
        em.clear();

        // then
        Optional<AdminBoard> findAdminBoard = adminBoardRepository.findById(adminBoard.getId());
        assertTrue(findAdminBoard.isPresent()); // 업데이트 된 엔티티가 존재하는지 확인
        assertTrue(findAdminBoard.get().getModified_dt().isAfter(beforeUpdateTime)); // 엔티티의 수정 시간이 업데이트 이전 시간보다 이후인지 확인
    }

    @Test
    void findAll() {
        // given
        Member member1 = new Member();
        Member member2 = new Member();
        AdminBoard adminBoard1 = new AdminBoard(member1, "Content 1");
        AdminBoard adminBoard2 = new AdminBoard(member2, "Content 2");
        adminBoardRepository.save(adminBoard1);
        adminBoardRepository.save(adminBoard2);

        // when
        List<AdminBoard> adminBoards = adminBoardRepository.findAll();

        // then
        assertTrue(adminBoards.contains(adminBoard1)); // 모든 엔티티 목록에 첫 번째 엔티티가 포함되어 있는지 확인
        assertTrue(adminBoards.contains(adminBoard2)); // 모든 엔티티 목록에 두 번째 엔티티가 포함되어 있는지 확인
    }
}
