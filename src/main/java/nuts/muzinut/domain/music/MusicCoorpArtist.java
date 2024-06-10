package nuts.muzinut.domain.music;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import nuts.muzinut.domain.baseEntity.BaseTimeEntity;
import nuts.muzinut.domain.member.Member;

@Entity
@Getter
@NoArgsConstructor
@Table(name = "music_corp_artist")
public class MusicCoorpArtist {

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

    // 연관 관계 메서드
    public void addMusicCoorpArtist(Music music, Member member) {
        this.music = music;
        this.member = member;
        music.getCoorpArtists().add(this);
    }
}