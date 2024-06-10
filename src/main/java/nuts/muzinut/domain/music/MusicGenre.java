package nuts.muzinut.domain.music;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import nuts.muzinut.domain.baseEntity.BaseTimeEntity;

@Entity
@Getter
@NoArgsConstructor
public class MusicGenre extends BaseTimeEntity {

    @Id
    @GeneratedValue
    @Column(name = "music_genre_id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "music_board_id")
    private Music music;

    private String genre;

}