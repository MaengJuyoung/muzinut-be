package nuts.muzinut.domain.board;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import nuts.muzinut.domain.baseEntity.BaseBoardEntity;
import nuts.muzinut.domain.member.Member;

import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor
public class Lounge extends BaseBoardEntity {

    @Id
    @GeneratedValue
    @Column(name = "lounge_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "member_id")
    private Member member;

    private String content;

    // 연관 관계 메서드
    public void createLounge(Member member){
        this.member = member;
        member.getLounges().add(this);
    }
}