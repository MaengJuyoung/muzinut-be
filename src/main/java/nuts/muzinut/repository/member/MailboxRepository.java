package nuts.muzinut.repository.member;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import nuts.muzinut.domain.member.Mailbox;
import nuts.muzinut.domain.member.Member;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class MailboxRepository {

    @PersistenceContext
    private EntityManager em;

    public void save(Mailbox mailbox){
        em.persist(mailbox);
    }

    public Optional<Mailbox> findById(Long id) {
        Mailbox mailbox = em.find(Mailbox.class, id);
        return Optional.ofNullable(mailbox);
    }

    @Transactional
    public void delete(Mailbox mailbox) {
        if (em.contains(mailbox)) {
            em.remove(mailbox);
        } else {
            Mailbox attachedMailbox = em.find(Mailbox.class, mailbox.getId());
            if (attachedMailbox != null) {
                em.remove(attachedMailbox);
            }
        }
    }

    public List<Mailbox> findAll() {
        return em.createQuery("select m from Mailbox m", Mailbox.class)
                .getResultList();
    }

    public List<Mailbox> findByMemberId(Long memberId) {
        return em.createQuery("select m from Mailbox m where m.member.id = :memberId", Mailbox.class)
                .setParameter("memberId", memberId)
                .getResultList();
    }
}
