package nuts.muzinut.repository.board;

import nuts.muzinut.domain.board.Lounge;
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
class LoungeRepositoryTest {

    @Autowired
    MemberRepository memberRepository; // MemberRepository 자동 주입
    @Autowired
    LoungeRepository loungeRepository; // LoungeRepository 자동 주입

    @Test
    void save() {

        //given
        Member member = new Member(); // 새로운 멤버 객체 생성
        memberRepository.save(member); // 멤버 객체를 DB에 저장

        Lounge lounge = new Lounge(); // 새로운 라운지 객체 생성
        lounge.createLounge(member); // 라운지 객체에 멤버 설정

        //when
        loungeRepository.save(lounge); // 라운지 객체를 DB에 저장

        //then
        Optional<Lounge> findLounge = loungeRepository.findById(lounge.getId()); // 저장된 라운지를 DB에서 조회
        assertTrue(findLounge.isPresent()); // 라운지가 존재하는지 확인
        assertEquals(lounge, findLounge.get()); // 저장된 라운지와 조회된 라운지가 같은지 확인
        assertEquals(member, findLounge.get().getMember()); // 라운지에 설정된 멤버가 같은지 확인
        assertNotNull(findLounge.get().getCreated_dt());
        assertNotNull(findLounge.get().getModified_dt());
        assertEquals(findLounge.get().getCreated_dt(), findLounge.get().getModified_dt());
    }

    @Test
    void delete() {

        //given
        Lounge lounge = new Lounge(); // 새로운 라운지 객체 생성
        loungeRepository.save(lounge); // 라운지 객체를 DB에 저장

        //when
        loungeRepository.delete(lounge); // 라운지 객체를 DB에서 삭제

        //then
        Optional<Lounge> findLounge = loungeRepository.findById(lounge.getId()); // 삭제된 라운지를 DB에서 조회
        assertFalse(findLounge.isPresent()); // 라운지가 존재하지 않는지 확인
    }

    @Test
    void update() {
        //given
        Member member = new Member(); // 새로운 멤버 객체 생성
        memberRepository.save(member); // 멤버 객체를 DB에 저장

        Lounge lounge = new Lounge(); // 새로운 라운지 객체 생성
        lounge.createLounge(member); // 라운지 객체에 멤버 설정
        loungeRepository.save(lounge); // 라운지 객체를 DB에 저장

        // 기존 라운지의 수정 시간을 기록
        LocalDateTime initialModifiedTime = lounge.getModified_dt();

        //when
        Member newMember = new Member(); // 새로운 멤버 객체 생성
        memberRepository.save(newMember); // 새로운 멤버 객체를 DB에 저장
        lounge.createLounge(newMember); // 라운지 객체에 새로운 멤버 설정
        loungeRepository.save(lounge); // 변경된 라운지 객체를 DB에 저장

        //then
        Optional<Lounge> findLounge = loungeRepository.findById(lounge.getId()); // 변경된 라운지를 DB에서 조회
        assertTrue(findLounge.isPresent()); // 라운지가 존재하는지 확인
        assertEquals(newMember, findLounge.get().getMember()); // 라운지에 설정된 새로운 멤버가 같은지 확인

        // 수정 시간이 업데이트되었는지 확인
        LocalDateTime updatedModifiedTime = findLounge.get().getModified_dt();
        assertNotNull(updatedModifiedTime); // 수정 시간이 null이 아닌지 확인
        assertTrue(updatedModifiedTime.isAfter(initialModifiedTime)); // 수정 시간이 초기 수정 시간 이후인지 확인
    }

    @Test
    void findById_NotFound() {
        //given
        // (특별한 사전 조건 없음)

        //when
        Optional<Lounge> findLounge = loungeRepository.findById(999L); // 존재하지 않는 ID로 라운지 조회

        //then
        assertFalse(findLounge.isPresent()); // 라운지가 존재하지 않는지 확인
    }
}
