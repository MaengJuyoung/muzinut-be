package nuts.muzinut.repository.board;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import nuts.muzinut.domain.board.recruit.RecruitBoard;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public class RecruitBoardRepository {

    @PersistenceContext
    private EntityManager em;

    public void save(RecruitBoard recruitBoard) {
        if (recruitBoard.getId() == null) {
            em.persist(recruitBoard);
        } else {
            em.merge(recruitBoard);
        }
    }

    public Optional<RecruitBoard> findById(Long id) {
        RecruitBoard recruitBoard = em.find(RecruitBoard.class, id);
        return Optional.ofNullable(recruitBoard);
    }

    public void delete(RecruitBoard recruitBoard) {
        if (em.contains(recruitBoard)) {
            em.remove(recruitBoard);
        } else {
            em.remove(em.merge(recruitBoard));
        }
    }
}
