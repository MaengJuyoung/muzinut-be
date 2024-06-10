package nuts.muzinut.domain.music;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import nuts.muzinut.domain.baseEntity.BaseTimeEntity;
import nuts.muzinut.domain.member.Member;

@Entity
@Getter
@NoArgsConstructor
public class MusicCoorpArtist extends BaseTimeEntity {

    @Id
    @GeneratedValue
    @Column(name = "music_coorp_artist_id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "music_board_id")
    private Music music;

    @ManyToOne
    @JoinColumn(name = "member_id")
    private Member member;

    // 생성자 및 기타 메소드...
}