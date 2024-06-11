package nuts.muzinut.repository.board;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import nuts.muzinut.domain.board.admin.AdminBoard;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@Transactional
public class AdminBoardRepository {

    @PersistenceContext
    private EntityManager em;

    public void save(AdminBoard adminBoard) {
        if (adminBoard.getId() == null) {
            em.persist(adminBoard);  // 새 엔티티 저장
        } else {
            em.merge(adminBoard);  // 기존 엔티티 업데이트
        }
    }

    public void delete(AdminBoard adminBoard) {
        if (em.contains(adminBoard)) {
            em.remove(adminBoard);  // 엔티티 매니저 관리하에 있으면 삭제
        } else {
            em.remove(em.merge(adminBoard));  // 엔티티 매니저 관리하에 없으면 병합 후 삭제
        }
    }

    public Optional<AdminBoard> findById(Long id) {
        AdminBoard adminBoard = em.find(AdminBoard.class, id);
        return Optional.ofNullable(adminBoard);  // Optional로 반환
    }

    public List<AdminBoard> findAll() {
        return em.createQuery("SELECT a FROM AdminBoard a", AdminBoard.class)
                .getResultList();  // 모든 AdminBoard 엔티티를 조회
    }
}
