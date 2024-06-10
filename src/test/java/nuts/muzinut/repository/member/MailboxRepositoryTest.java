package nuts.muzinut.repository.member;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import nuts.muzinut.domain.member.Mailbox;
import nuts.muzinut.domain.member.Member;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class MailboxRepositoryTest {

    @PersistenceContext
    EntityManager em;

    @Autowired
    private MemberRepository memberRepository;
    @Autowired
    private MailboxRepository mailboxRepository;

    @Test
    void save() {

        //given
        Member member = new Member("email", "password");
        memberRepository.save(member);
        Mailbox mailbox = new Mailbox();
        mailbox.createMailbox(member);

        //when
        mailboxRepository.save(mailbox);

        //then
        Optional<Mailbox> findMailbox = mailboxRepository.findById(mailbox.getId());
        assertTrue(findMailbox.isPresent()); // 저장된 메일함이 존재하는지 확인
        assertEquals(member, findMailbox.get().getMember()); // 저장된 메일함의 멤버를 확인
    }

    @Test
    void delete() {

        //given
        Member member = new Member("email", "password");
        memberRepository.save(member);
        Mailbox mailbox = new Mailbox();
        mailbox.createMailbox(member);
        mailboxRepository.save(mailbox);

        //when
        mailboxRepository.delete(mailbox);

        //then
        Optional<Mailbox> findMailbox = mailboxRepository.findById(mailbox.getId());
        assertTrue(findMailbox.isEmpty());
    }

    /**
     * 회원 삭제시 해당 회원의 우편함도 삭제한다
     */
    @Test
    void delete_with_member() {

        //given
        Member member = new Member("email", "password");
        memberRepository.save(member);
        Mailbox mailbox = new Mailbox();
        mailbox.createMailbox(member);
        mailboxRepository.save(mailbox);

        //when
        memberRepository.delete(member.getId());

        //then
        Optional<Mailbox> findMailbox = mailboxRepository.findById(mailbox.getId());
        assertTrue(findMailbox.isEmpty()); //회원 삭제시 해당 회원의 메일함이 없다.
    }
}
