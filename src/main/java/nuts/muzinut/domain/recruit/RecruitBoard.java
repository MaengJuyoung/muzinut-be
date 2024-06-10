package nuts.muzinut.domain.recruit;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import nuts.muzinut.domain.baseEntity.BaseTimeEntity;
import nuts.muzinut.domain.member.Member;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor
public class RecruitBoard extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "recruit_board_id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "member_id")
    private Member member;

    private Integer recruitMember;
    private Timestamp duration;
    private Timestamp workDuration;
    private Integer view;
    private Integer weeklyGood;

    @OneToMany(mappedBy = "recruitBoard", cascade = CascadeType.ALL)
    private List<RecruitBoardGenre> genres = new ArrayList<>();
}