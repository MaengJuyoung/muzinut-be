package nuts.muzinut.dto.member.profile.Board;

import lombok.Data;
import nuts.muzinut.dto.member.profile.ProfileDto;

import java.util.List;

@Data
public class ProfileBoardDto extends ProfileDto {

    List<String> boardsTitle;
    List<String> bookmarkTitle;

    // 기본 생성자
    public ProfileBoardDto(String profileBannerImgName, String profileImgName, String nickname, String intro, Long followingCount, Long followersCount, boolean followStatus, List<String> boardsTitle, List<String> bookmarkTitle) {
        super(profileBannerImgName, profileImgName, nickname, intro, followingCount, followersCount, followStatus);
        this.boardsTitle = boardsTitle;
        this.bookmarkTitle = bookmarkTitle;
    }

    // 새로운 생성자: 보드 정보 없이 프로필 정보만 반환
    public ProfileBoardDto(String profileBannerImgName, String profileImgName, String nickname, String intro, Long followingCount, Long followersCount, boolean followStatus) {
        super(profileBannerImgName, profileImgName, nickname, intro, followingCount, followersCount, followStatus);
    }
}
