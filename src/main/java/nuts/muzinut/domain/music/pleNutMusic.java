package nuts.muzinut.domain.music;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
public class pleNutMusic {
    @Id
    @GeneratedValue
    @Column(name = "playlist_music_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "playlist_id")
    private PleNut pleNut;

    @Column(name = "music_id")
    private Long musicId; // 실제 음악 엔티티와의 연결은 생략하고, ID 참조로만 표현합니다.

    // 연관 관계 메서드
    public void addpleNutMusic(PleNut pleNut, Music music) {
        this.pleNut = pleNut;
        this.musicId = music.getId();
        pleNut.getPleNutMusics().add(this);
    }
}