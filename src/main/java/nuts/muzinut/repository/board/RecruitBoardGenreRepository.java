package nuts.muzinut.repository.board;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import nuts.muzinut.domain.board.recruit.RecruitBoardGenre;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class RecruitBoardGenreRepository {

    @PersistenceContext
    private EntityManager em;

    public void save(RecruitBoardGenre recruitBoardGenre) {
        if (recruitBoardGenre.getId() == null) {
            em.persist(recruitBoardGenre);
        } else {
            em.merge(recruitBoardGenre);
        }
    }

    public Optional<RecruitBoardGenre> findById(Long id) {
        RecruitBoardGenre recruitBoardGenre = em.find(RecruitBoardGenre.class, id);
        return Optional.ofNullable(recruitBoardGenre);
    }

    public void delete(RecruitBoardGenre recruitBoardGenre) {
        if (em.contains(recruitBoardGenre)) {
            em.remove(recruitBoardGenre);
        } else {
            em.remove(em.merge(recruitBoardGenre));
        }
    }
}
