package nuts.muzinut.repository.board;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import nuts.muzinut.domain.board.comment.Comment;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class CommentRepository {

    @PersistenceContext
    private EntityManager em;

    public void save(Comment comment) {
        if (comment.getId() == null) {
            em.persist(comment);
        } else {
            em.merge(comment);
        }
    }

    public Optional<Comment> findById(Long id) {
        Comment comment = em.find(Comment.class, id);
        return Optional.ofNullable(comment);
    }

    public void delete(Comment comment) {
        em.remove(em.contains(comment) ? comment : em.merge(comment));
    }

    public void update(Long id, String newContent) {
        Comment comment = em.find(Comment.class, id);
        if(comment != null) {
            comment.setContent(newContent);
        }
    }
}
