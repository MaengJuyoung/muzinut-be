package nuts.muzinut.repository.board;

import jakarta.persistence.EntityManager;
import nuts.muzinut.domain.board.recruit.RecruitBoard;
import nuts.muzinut.domain.board.recruit.RecruitBoardGenre;
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
class RecruitBoardGenreRepositoryTest {

    @Autowired
    MemberRepository memberRepository;
    @Autowired
    RecruitBoardRepository recruitBoardRepository;
    @Autowired
    RecruitBoardGenreRepository recruitBoardGenreRepository;
    @Autowired
    EntityManager em;

    @Test
    void save() {
        // given
        Member member = new Member();
        memberRepository.save(member);

        RecruitBoard recruitBoard = new RecruitBoard();
        recruitBoard.createRecruitBoard(member);
        recruitBoardRepository.save(recruitBoard);

        RecruitBoardGenre recruitBoardGenre = new RecruitBoardGenre();
        recruitBoardGenre.addRecruitBoardGenre(recruitBoard);
        recruitBoardGenre.setGenre("Action");

        // when
        recruitBoardGenreRepository.save(recruitBoardGenre);

        // then
        Optional<RecruitBoardGenre> findRecruitBoardGenre = recruitBoardGenreRepository.findById(recruitBoardGenre.getId());
        assertTrue(findRecruitBoardGenre.isPresent()); // 저장된 RecruitBoardGenre가 존재하는지 확인
        assertEquals(recruitBoardGenre, findRecruitBoardGenre.get()); // 저장된 RecruitBoardGenre가 올바른지 확인
        assertEquals(recruitBoard, findRecruitBoardGenre.get().getRecruitBoard()); // RecruitBoardGenre와 연관된 RecruitBoard가 올바른지 확인
    }

    @Test
    void delete() {
        // given
        RecruitBoard recruitBoard = new RecruitBoard();
        recruitBoardRepository.save(recruitBoard);

        RecruitBoardGenre recruitBoardGenre = new RecruitBoardGenre();
        recruitBoardGenre.addRecruitBoardGenre(recruitBoard);
        recruitBoardGenre.setGenre("Action");
        recruitBoardGenreRepository.save(recruitBoardGenre);

        // when
        recruitBoardGenreRepository.delete(recruitBoardGenre);

        // then
        Optional<RecruitBoardGenre> findRecruitBoardGenre = recruitBoardGenreRepository.findById(recruitBoardGenre.getId());
        assertFalse(findRecruitBoardGenre.isPresent()); // 삭제된 RecruitBoardGenre가 존재하지 않는지 확인
    }

    @Test
    void update() {
        // given
        Member member = new Member();
        memberRepository.save(member);

        RecruitBoard recruitBoard = new RecruitBoard();
        recruitBoard.createRecruitBoard(member);
        recruitBoardRepository.save(recruitBoard);

        RecruitBoardGenre recruitBoardGenre = new RecruitBoardGenre();
        recruitBoardGenre.addRecruitBoardGenre(recruitBoard);
        recruitBoardGenre.setGenre("Action");
        recruitBoardGenreRepository.save(recruitBoardGenre);

        LocalDateTime beforeUpdate = recruitBoardGenre.getModified_dt();

        // when
        recruitBoardGenre.setGenre("Adventure"); // 장르를 수정
        recruitBoardGenreRepository.save(recruitBoardGenre);

        // 엔티티 상태를 강제로 갱신하도록 변경
        em.flush();
        em.clear();

        // then
        Optional<RecruitBoardGenre> findRecruitBoardGenre = recruitBoardGenreRepository.findById(recruitBoardGenre.getId());
        assertTrue(findRecruitBoardGenre.isPresent()); // 수정된 RecruitBoardGenre가 존재하는지 확인
        assertEquals("Adventure", findRecruitBoardGenre.get().getGenre()); // 수정된 장르가 올바른지 확인
        assertNotEquals(beforeUpdate, findRecruitBoardGenre.get().getModified_dt());  // 수정 시간이 변경되었는지 확인
    }

    @Test
    void findById_NotFound() {
        // given

        // when
        Optional<RecruitBoardGenre> findRecruitBoardGenre = recruitBoardGenreRepository.findById(999L);

        // then
        assertFalse(findRecruitBoardGenre.isPresent()); // 존재하지 않는 ID로 조회 시 RecruitBoardGenre가 존재하지 않는지 확인
    }
}
