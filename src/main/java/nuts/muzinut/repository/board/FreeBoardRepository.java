package nuts.muzinut.repository.board;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import nuts.muzinut.domain.board.FreeBoard;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class FreeBoardRepository {

    @PersistenceContext
    private EntityManager em;

    public void save(FreeBoard freeBoard) {
        if (freeBoard.getId() == null) {
            em.persist(freeBoard);
        } else {
            em.merge(freeBoard);
        }
    }

    public Optional<FreeBoard> findById(Long id) {
        FreeBoard freeBoard = em.find(FreeBoard.class, id);
        return Optional.ofNullable(freeBoard);
    }

    public void delete(FreeBoard freeBoard) {
        if (em.contains(freeBoard)) {
            em.remove(freeBoard);
        } else {
            em.remove(em.merge(freeBoard));
        }
    }
}
