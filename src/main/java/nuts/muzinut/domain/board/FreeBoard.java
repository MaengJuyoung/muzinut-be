package nuts.muzinut.domain.board;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import nuts.muzinut.domain.baseEntity.BaseBoardEntity;
import nuts.muzinut.domain.baseEntity.BaseTimeEntity;
import nuts.muzinut.domain.member.Member;

import java.time.LocalDateTime;

@Entity
@Getter
@Table(name = "free_board")
public class FreeBoard extends BaseBoardEntity {

    @Id
    @GeneratedValue
    @Column(name = "free_board_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "member_id")
    private Member member;

    private String filename;

    // 연관 관계 메서드
    public void createFreeBoard(Member member){
        this.member = member;
        member.getFreeBoards().add(this);
    }
}