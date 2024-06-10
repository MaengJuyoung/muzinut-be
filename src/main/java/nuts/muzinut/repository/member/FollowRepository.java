package nuts.muzinut.repository.member;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import nuts.muzinut.domain.member.Follow;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class FollowRepository {

    @PersistenceContext
    private EntityManager em;

    @Transactional
    public void save(Follow follow) {
        if (follow.getId() == null) {
            em.persist(follow);
        } else {
            em.merge(follow);
        }
    }

    @Transactional
    public Follow findById(Long id) {
        return em.find(Follow.class, id);
    }

    @Transactional
    public List<Follow> findAll() {
        return em.createQuery("SELECT f FROM Follow f", Follow.class).getResultList();
    }

    @Transactional
    public List<Follow> findByMemberId(Long memberId) {
        return em.createQuery("SELECT f FROM Follow f WHERE f.member.id = :memberId", Follow.class)
                .setParameter("memberId", memberId)
                .getResultList();
    }

    @Transactional
    public List<Follow> findByFollowingMemberId(Long followingMemberId) {
        return em.createQuery("SELECT f FROM Follow f WHERE f.followingMemberId = :followingMemberId", Follow.class)
                .setParameter("followingMemberId", followingMemberId)
                .getResultList();
    }

    @Transactional
    public void delete(Long id) {
        Follow follow = findById(id);
        if (follow != null) {
            em.remove(follow);
        }
    }
}
