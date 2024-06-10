package nuts.muzinut.domain.member;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import nuts.muzinut.domain.baseEntity.BaseTimeEntity;

import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor
public class NutsUsageHistory extends BaseTimeEntity {

    @Id
    @GeneratedValue
    @Column(name = "nuts_usage_history_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    private Integer usedNutsCount;
    private LocalDateTime usedDt;
    private String usedContent;
    private String usedChannel;
    private String supportMsg;

    public NutsUsageHistory(Member member, Integer usedNutsCount, LocalDateTime usedDt, String usedContent, String usedChannel, String supportMsg) {
        this.member = member;
        this.usedNutsCount = usedNutsCount;
        this.usedDt = usedDt;
        this.usedContent = usedContent;
        this.usedChannel = usedChannel;
        this.supportMsg = supportMsg;
    }
}
