package nuts.muzinut.repository.board;

import nuts.muzinut.domain.board.FreeBoard;
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
class FreeBoardRepositoryTest {

    @Autowired
    MemberRepository memberRepository;
    @Autowired
    FreeBoardRepository freeBoardRepository;

    @Test
    void save() {
        // given
        Member member = new Member();
        memberRepository.save(member); // 멤버 저장

        FreeBoard freeBoard = new FreeBoard();
        freeBoard.createFreeBoard(member); // 멤버와 함께 FreeBoard 생성

        // when
        freeBoardRepository.save(freeBoard); // FreeBoard 저장

        // then
        Optional<FreeBoard> findFreeBoard = freeBoardRepository.findById(freeBoard.getId()); // 저장된 FreeBoard 조회
        assertTrue(findFreeBoard.isPresent(), "FreeBoard should be present"); // 조회된 FreeBoard가 존재하는지 확인
        assertEquals(freeBoard, findFreeBoard.get(), "Saved FreeBoard should match the found FreeBoard"); // 저장된 객체와 조회된 객체가 동일한지 확인
        assertEquals(member, findFreeBoard.get().getMember(), "Member of the FreeBoard should match the saved member"); // FreeBoard의 Member가 저장된 Member와 동일한지 확인
    }

    @Test
    void delete() {
        // given
        FreeBoard freeBoard = new FreeBoard();
        freeBoardRepository.save(freeBoard); // FreeBoard 저장

        // when
        freeBoardRepository.delete(freeBoard); // FreeBoard 삭제

        // then
        Optional<FreeBoard> findFreeBoard = freeBoardRepository.findById(freeBoard.getId()); // 삭제된 FreeBoard 조회
        assertTrue(findFreeBoard.isEmpty(), "FreeBoard should be deleted"); // 조회된 FreeBoard가 존재하지 않는지 확인
    }

    @Test
    void update() {
        // given
        Member member = new Member();
        memberRepository.save(member); // 멤버 저장

        FreeBoard freeBoard = new FreeBoard();
        freeBoard.createFreeBoard(member); // 멤버와 함께 FreeBoard 생성
        freeBoardRepository.save(freeBoard); // FreeBoard 저장

        // when
        freeBoard.setTitle("Updated Title"); // FreeBoard의 제목 수정
        freeBoardRepository.save(freeBoard); // 수정된 FreeBoard 저장

        // then
        Optional<FreeBoard> findFreeBoard = freeBoardRepository.findById(freeBoard.getId()); // 수정된 FreeBoard 조회
        assertTrue(findFreeBoard.isPresent(), "FreeBoard should be present after update"); // 조회된 FreeBoard가 존재하는지 확인
        assertEquals("Updated Title", findFreeBoard.get().getTitle(), "Title should be updated"); // FreeBoard의 제목이 수정된 제목과 동일한지 확인
    }
}
