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
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "like_id")
    private Long id;

    private Integer boardId;
    private String boardType;

    @ManyToOne
    @JoinColumn(name = "member_id")
    private Member member;
}