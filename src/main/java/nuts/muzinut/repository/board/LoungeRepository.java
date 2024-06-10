package nuts.muzinut.repository.board;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import nuts.muzinut.domain.board.Lounge;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class LoungeRepository {

    @PersistenceContext
    private EntityManager em;

    public void save(Lounge lounge) {
        if (lounge.getId() == null) {
            em.persist(lounge);
        } else {
            em.merge(lounge);
        }
    }

    public Optional<Lounge> findById(Long id) {
        Lounge lounge = em.find(Lounge.class, id);
        return Optional.ofNullable(lounge);
    }

    public void delete(Lounge lounge) {
        if (em.contains(lounge)) {
            em.remove(lounge);
        } else {
            em.remove(em.merge(lounge));
        }
    }
}
