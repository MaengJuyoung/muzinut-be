package nuts.muzinut.domain.music;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import nuts.muzinut.domain.baseEntity.BaseBoardEntity;
import nuts.muzinut.domain.baseEntity.BaseTimeEntity;
import nuts.muzinut.domain.member.Member;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter @Setter
@NoArgsConstructor
public class Music extends BaseBoardEntity {

    @Id
    @GeneratedValue
    @Column(name = "music_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "member_id")
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "album_id")
    private Album album;

    private String intro;
    private String article; // 가사

    @Column(name = "music_origin_filename")
    private String musicOriginFilename;

    @Column(name = "music_store_filename")
    private String musicStoreFilename;

    @Column(name = "music_img_origin_filename")
    private String musicImgOriginFilename;

    @Column(name = "music_img_store_filename")
    private String musicImgStoreFilename;


    @OneToMany(mappedBy = "music", cascade = CascadeType.ALL)
    private List<MusicGenre> genres = new ArrayList<>();

    @OneToMany(mappedBy = "music", cascade = CascadeType.ALL)
    private List<MusicCoorpArtist> coorpArtists = new ArrayList<>();

    // 연관 관계 메서드
    public void createMusic(Member member) {
        this.member = member;
        member.getMusicList().add(this);
    }
}
