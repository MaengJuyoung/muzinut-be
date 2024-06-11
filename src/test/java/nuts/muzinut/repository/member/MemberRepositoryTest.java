package nuts.muzinut.repository.member;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import nuts.muzinut.domain.member.Member;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class MemberRepositoryTest {

    @PersistenceContext
    EntityManager em;

    @Autowired
    private MemberRepository memberRepository;

    @Test
    void save() {
        //given
        Member member = new Member("email", "password");

        //when
        memberRepository.save(member);

        //then
        List<Member> result = memberRepository.findAll();
        assertEquals(1, result.size());  // 저장된 엔티티의 수가 1개인지 확인
        assertEquals("email", result.get(0).getEmail());  // 저장된 엔티티의 이메일이 "email"인지 확인
        assertEquals("password", result.get(0).getPassword());  // 저장된 엔티티의 비밀번호가 "password"인지 확인
    }

    @Test
    void remove() {
        //given
        Member member = new Member("email", "password");

        //when
        memberRepository.save(member);

        //then
        memberRepository.delete(member.getId());
        em.flush();
        em.clear();

        List<Member> result = memberRepository.findAll();
        assertEquals(0, result.size());  // 엔티티가 삭제되어 수가 0개인지 확인
    }

    @Test
    void update() {
        //given
        Member member = new Member("email", "password");
        memberRepository.save(member);
        em.flush();
        em.clear();

        //when
        Member foundMember = memberRepository.findById(member.getId()).orElseThrow();
        LocalDateTime beforeUpdate = foundMember.getModified_dt();
        foundMember.setEmail("new_email");
        foundMember.setPassword("new_password");
        em.flush();
        em.clear();

        //then
        Member updatedMember = memberRepository.findById(member.getId()).orElseThrow();
        assertEquals("new_email", updatedMember.getEmail());  // 이메일이 "new_email"로 업데이트되었는지 확인
        assertEquals("new_password", updatedMember.getPassword());  // 비밀번호가 "new_password"로 업데이트되었는지 확인
        assertNotEquals(beforeUpdate, updatedMember.getModified_dt());  // 수정된 시간이 변경되었는지 확인
    }
}
