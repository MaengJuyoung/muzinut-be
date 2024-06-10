package nuts.muzinut.repository.board;

import nuts.muzinut.domain.board.recruit.RecruitBoard;
import nuts.muzinut.domain.member.Member;
import nuts.muzinut.repository.member.MemberRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class RecruitBoardRepositoryTest {

    @Autowired
    MemberRepository memberRepository;
    @Autowired
    RecruitBoardRepository recruitBoardRepository;

    @Test
    void save() {
        // given: 테스트에 필요한 객체들을 생성 및 저장
        Member member = new Member();
        memberRepository.save(member);

        RecruitBoard recruitBoard = new RecruitBoard();
        recruitBoard.createRecruitBoard(member);

        // when: recruitBoard 객체를 저장
        recruitBoardRepository.save(recruitBoard);

        // then: 저장된 recruitBoard 객체를 조회하여 검증
        Optional<RecruitBoard> findRecruitBoard = recruitBoardRepository.findById(recruitBoard.getId());
        assertTrue(findRecruitBoard.isPresent());
        assertEquals(recruitBoard, findRecruitBoard.get());
        assertEquals(member, findRecruitBoard.get().getMember());
    }

    @Test
    void delete() {
        // given: recruitBoard 객체를 생성 및 저장
        RecruitBoard recruitBoard = new RecruitBoard();
        recruitBoardRepository.save(recruitBoard);

        // when: recruitBoard 객체를 삭제
        recruitBoardRepository.delete(recruitBoard);

        // then: 삭제된 recruitBoard 객체를 조회하여 존재하지 않음을 검증
        Optional<RecruitBoard> findRecruitBoard = recruitBoardRepository.findById(recruitBoard.getId());
        assertFalse(findRecruitBoard.isPresent());
    }

    @Test
    void update() {
        // given: recruitBoard 객체를 생성 및 저장
        Member member = new Member();
        memberRepository.save(member);

        RecruitBoard recruitBoard = new RecruitBoard();
        recruitBoard.createRecruitBoard(member);
        recruitBoardRepository.save(recruitBoard);

        // when: recruitBoard 객체의 내용을 업데이트하고 저장
        Member newMember = new Member();
        memberRepository.save(newMember);
        recruitBoard.createRecruitBoard(newMember);
        recruitBoardRepository.save(recruitBoard);

        // then: 업데이트된 recruitBoard 객체를 조회하여 검증
        Optional<RecruitBoard> findRecruitBoard = recruitBoardRepository.findById(recruitBoard.getId());
        assertTrue(findRecruitBoard.isPresent());
        assertEquals(newMember, findRecruitBoard.get().getMember());
    }

    @Test
    void findById_NotFound() {
        // given: 존재하지 않는 ID로 조회 시도

        // when: 조회 시도
        Optional<RecruitBoard> findRecruitBoard = recruitBoardRepository.findById(999L);

        // then: 조회 결과가 존재하지 않음을 검증
        assertFalse(findRecruitBoard.isPresent());
    }
}
