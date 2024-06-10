package nuts.muzinut.domain.recruit;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
public class RecruitBoardGenre {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "recruit_board_genre_id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "recruit_board_id")
    private RecruitBoard recruitBoard;

    private String genre;
}