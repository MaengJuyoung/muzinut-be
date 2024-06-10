package nuts.muzinut.domain.baseEntity;

import jakarta.persistence.MappedSuperclass;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@MappedSuperclass
@Getter @Setter
public abstract class BaseTimeEntity {
    private LocalDateTime created_dt;
    private LocalDateTime modified_dt;

    @PrePersist // 엔티티가 처음 생성될 때의 시간을 자동으로 기록
    public void prePersist() {
        this.created_dt = LocalDateTime.now();
        this.modified_dt = LocalDateTime.now();
    }

    @PreUpdate  // 엔티티가 수정될 때마다 수정 시간을 자동으로 기록
    public void preUpdate() {
        this.modified_dt = LocalDateTime.now();
    }
}


