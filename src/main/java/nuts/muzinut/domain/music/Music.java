package nuts.muzinut.domain.music;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import nuts.muzinut.domain.baseEntity.BaseTimeEntity;
import nuts.muzinut.domain.member.Member;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor
public class Music extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "music_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    private String albumName;
    private String albumIntro;
    private String article; // 가사
    private String musicOriginFilename;
    private String musicStoreFilename;
    private String albumImgOriginFilename;
    private String albumImgStoreFilename;
    private Integer view;
    private Integer weeklyGood;

    @OneToMany(mappedBy = "music", cascade = CascadeType.ALL)
    private List<MusicGenre> genres = new ArrayList<>();

    @OneToMany(mappedBy = "music", cascade = CascadeType.ALL)
    private List<MusicCoorpArtist> coorpArtists = new ArrayList<>();

    public Music(Member member, String albumName, String albumIntro, String article,
                 String musicOriginFilename, String musicStoreFilename, String albumImgOriginFilename,
                 String albumImgStoreFilename) {
        this.member = member;
        this.albumName = albumName;
        this.albumIntro = albumIntro;
        this.article = article;
        this.musicOriginFilename = musicOriginFilename;
        this.musicStoreFilename = musicStoreFilename;
        this.albumImgOriginFilename = albumImgOriginFilename;
        this.albumImgStoreFilename = albumImgStoreFilename;
        this.view = 0;
        this.weeklyGood = 0;
    }
}
