package nuts.muzinut.domain.board;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import nuts.muzinut.domain.member.Member;

@Entity
@Getter
@NoArgsConstructor
@Table(name = "`likes`") // 데이터베이스 테이블 이름을 예약어가 아닌 다른 것으로 지정
public class Like {

    @Id
    @GeneratedValue
    @Column(name = "like_id")
    private Long id;

    @Column(name = "board_id")
    private Long boardId;

    @Column(name = "board_type")
    @Enumerated(EnumType.STRING)
    private BoardType boardType;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    public Like(Long boardId, BoardType boardType, Member member) {
        this.boardId = boardId;
        this.boardType = boardType;
        this.member = member;
        member.getLikeList().add(this);
    }
}