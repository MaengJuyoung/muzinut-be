package nuts.muzinut.repository.board;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import nuts.muzinut.domain.board.Bookmark;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class BookmarkRepository {

    @PersistenceContext
    private EntityManager em;

    public void save(Bookmark bookmark) {
        if (bookmark.getId() == null) {
            em.persist(bookmark);
        } else {
            em.merge(bookmark);
        }
    }

    public Optional<Bookmark> findById(Long id) {
        Bookmark bookmark = em.find(Bookmark.class, id);
        return Optional.ofNullable(bookmark);
    }

    public void delete(Bookmark bookmark) {
        if (em.contains(bookmark)) {
            em.remove(bookmark);
        } else {
            em.remove(em.merge(bookmark));
        }
    }
}
