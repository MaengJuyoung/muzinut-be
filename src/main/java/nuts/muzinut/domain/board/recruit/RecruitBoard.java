package nuts.muzinut.domain.board.recruit;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import nuts.muzinut.domain.baseEntity.BaseBoardEntity;
import nuts.muzinut.domain.baseEntity.BaseTimeEntity;
import nuts.muzinut.domain.member.Member;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor
@Table(name = "recruit_board")
public class RecruitBoard extends BaseBoardEntity {

    @Id
    @GeneratedValue
    @Column(name = "recruit_board_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "member_id")
    private Member member;

    private String content;

    @Column(name = "recruit_member")
    private int recruit_member;

    @Column(name = "start_duration")
    private LocalDateTime startDuration;

    @Column(name = "end_duration")
    private LocalDateTime endDuration;

    @Column(name = "start_work_duration")
    private LocalDateTime startWorkDuration;

    @Column(name = "end_work_duration")
    private LocalDateTime endWorkDuration;

    @OneToMany(mappedBy = "recruitBoard", cascade = CascadeType.ALL)
    private List<RecruitBoardGenre> genres = new ArrayList<>();

    // 연관 관계 메서드
    public void createRecruitBoard(Member member) {
        this.member = member;
        member.getRecruitBoards().add(this);
    }
}