package nuts.muzinut.dto.member.profile.Album;

import lombok.Data;
import nuts.muzinut.dto.member.profile.ProfileDto;

import java.util.List;

@Data
public class ProfileSongDto extends ProfileDto {

    // 좋아요 높은 곡
    private String songTitle;
    private String genre;
    private String lyricist; // 작사가
    private String composer; // 작곡가
    private int likeCount;
    private String albumImage; // 앨범 이미지 추가

    // 앨범 리스트
    private List<ProfileAlbumDto> profileAlbumDtos; // 추가

    // 기본 생성자
    public ProfileSongDto(String profileBannerImgName, String profileImgName, String nickname, String intro, Long followingCount, Long followersCount, boolean followStatus, String songTitle, String genre, String lyricist, String composer, int likeCount, String albumImage, List<ProfileAlbumDto> profileAlbumDtos) {
        super(profileBannerImgName, profileImgName, nickname, intro, followingCount, followersCount, followStatus);
        this.songTitle = songTitle;
        this.genre = genre;
        this.lyricist = lyricist;
        this.composer = composer;
        this.likeCount = likeCount;
        this.albumImage = albumImage;
        this.profileAlbumDtos = profileAlbumDtos;
    }

    // 새로운 생성자: 곡 정보 없이 프로필 정보만 반환
    public ProfileSongDto(String profileBannerImgName, String profileImgName, String nickname, String intro, Long followingCount, Long followersCount, boolean followStatus) {
        super(profileBannerImgName, profileImgName, nickname, intro, followingCount, followersCount, followStatus);
    }
}
