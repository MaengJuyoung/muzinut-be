package nuts.muzinut.domain.board.recruit;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import nuts.muzinut.domain.baseEntity.BaseBoardEntity;

@Entity
@Getter
@NoArgsConstructor
@Table(name = "recruit_board_genre")
public class RecruitBoardGenre extends BaseBoardEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "recruit_board_genre_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "recruit_board_id")
    private RecruitBoard recruitBoard;

    private String genre;

    // 연관 관계 메서드
    public void addRecruitBoardGenre(RecruitBoard recruitBoard) {
        this.recruitBoard = recruitBoard;
        recruitBoard.getGenres().add(this);
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }
}