package nuts.muzinut.repository.board;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;
import jakarta.transaction.Transactional;
import nuts.muzinut.domain.board.admin.AdminUploadFile;

import java.util.List;
import java.util.Optional;

@Repository
@Transactional
public class AdminUploadFileRepository {

    @PersistenceContext
    private EntityManager em;

    public void save(AdminUploadFile adminUploadFile) {
        if (adminUploadFile.getId() == null) {
            em.persist(adminUploadFile);  // 새 엔티티 저장
        } else {
            em.merge(adminUploadFile);  // 기존 엔티티 업데이트
        }
    }

    public void delete(AdminUploadFile adminUploadFile) {
        if (em.contains(adminUploadFile)) {
            em.remove(adminUploadFile);  // 엔티티 매니저 관리하에 있으면 삭제
        } else {
            em.remove(em.merge(adminUploadFile));  // 엔티티 매니저 관리하에 없으면 병합 후 삭제
        }
    }

    public Optional<AdminUploadFile> findById(Long id) {
        AdminUploadFile adminUploadFile = em.find(AdminUploadFile.class, id);
        return Optional.ofNullable(adminUploadFile);  // Optional로 반환
    }

    public List<AdminUploadFile> findAll() {
        return em.createQuery("SELECT a FROM AdminUploadFile a", AdminUploadFile.class)
                .getResultList();  // 모든 AdminUploadFile 엔티티를 조회
    }
}
