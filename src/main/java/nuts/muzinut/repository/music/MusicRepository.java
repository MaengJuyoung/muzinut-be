package nuts.muzinut.repository.music;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import nuts.muzinut.domain.music.Music;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class MusicRepository {

    @PersistenceContext
    private EntityManager em;

    public void save(Music music) {
        if (music.getId() == null) {
            em.persist(music);
        } else {
            em.merge(music);
        }
    }

    public Music findById(Long id) {
        return em.find(Music.class, id);
    }

    public List<Music> findAll() {
        return em.createQuery("select m from Music m", Music.class).getResultList();
    }

    public List<Music> findByMemberId(Long memberId) {
        return em.createQuery("select m from Music m where m.member.id = :memberId", Music.class)
                .setParameter("memberId", memberId)
                .getResultList();
    }

    public void delete(Long id) {
        Music music = findById(id);
        if (music != null) {
            em.remove(music);
        }
    }
}
