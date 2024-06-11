package nuts.muzinut.repository.board;

import jakarta.persistence.EntityManager;
import nuts.muzinut.domain.board.recruit.RecruitBoard;
import nuts.muzinut.domain.member.Member;
import nuts.muzinut.repository.member.MemberRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class RecruitBoardRepositoryTest {

    @Autowired
    MemberRepository memberRepository;
    @Autowired
    RecruitBoardRepository recruitBoardRepository;
    @Autowired
    EntityManager em;

    @Test
    void save() {
        // given
        Member member = new Member();
        memberRepository.save(member);

        RecruitBoard recruitBoard = new RecruitBoard();
        recruitBoard.createRecruitBoard(member);

        // when
        recruitBoardRepository.save(recruitBoard);

        // then
        Optional<RecruitBoard> findRecruitBoard = recruitBoardRepository.findById(recruitBoard.getId());
        assertTrue(findRecruitBoard.isPresent()); // 저장된 RecruitBoard가 존재하는지 확인
        assertEquals(recruitBoard, findRecruitBoard.get()); // 저장된 RecruitBoard가 올바른지 확인
        assertEquals(member, findRecruitBoard.get().getMember()); // RecruitBoard와 연관된 Member가 올바른지 확인
    }

    @Test
    void delete() {
        // given
        RecruitBoard recruitBoard = new RecruitBoard();
        recruitBoardRepository.save(recruitBoard);

        // when
        recruitBoardRepository.delete(recruitBoard);

        // then
        Optional<RecruitBoard> findRecruitBoard = recruitBoardRepository.findById(recruitBoard.getId());
        assertFalse(findRecruitBoard.isPresent()); // 삭제된 RecruitBoard가 존재하지 않는지 확인
    }

    @Test
    void update() {
        // given
        Member member = new Member();
        memberRepository.save(member);

        RecruitBoard recruitBoard = new RecruitBoard();
        recruitBoard.createRecruitBoard(member);
        recruitBoardRepository.save(recruitBoard);

        LocalDateTime beforeUpdate = recruitBoard.getModified_dt();

        // when
        recruitBoard.setTitle("Updated Title"); // 제목을 수정하는 것으로 변경
        recruitBoardRepository.save(recruitBoard);

        // 엔티티 상태를 강제로 갱신하도록 변경
        em.flush();
        em.clear();

        // then
        Optional<RecruitBoard> findRecruitBoard = recruitBoardRepository.findById(recruitBoard.getId());
        assertTrue(findRecruitBoard.isPresent()); // 수정된 RecruitBoard가 존재하는지 확인
        assertEquals("Updated Title", findRecruitBoard.get().getTitle()); // 수정된 제목이 올바른지 확인
        assertNotEquals(beforeUpdate, findRecruitBoard.get().getModified_dt());  // 수정 시간이 변경되었는지 확인
    }

    @Test
    void findById_NotFound() {
        // given

        // when
        Optional<RecruitBoard> findRecruitBoard = recruitBoardRepository.findById(999L);

        // then
        assertFalse(findRecruitBoard.isPresent()); // 존재하지 않는 ID로 조회 시 RecruitBoard가 존재하지 않는지 확인
    }
}
