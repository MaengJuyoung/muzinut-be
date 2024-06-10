package nuts.muzinut.domain.member;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import nuts.muzinut.domain.music.PlaybackRecord;
import nuts.muzinut.domain.music.Playlist;
import nuts.muzinut.domain.baseEntity.BaseTimeEntity;
import nuts.muzinut.domain.board.FreeBoard;
import nuts.muzinut.domain.board.Like;
import nuts.muzinut.domain.chat.Chat;
import nuts.muzinut.domain.comment.Comment;
import nuts.muzinut.domain.comment.Reply;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor
public class Member extends BaseTimeEntity {

    @Id @GeneratedValue
    @Column(name = "member_id")
    private Long id;

    private String email;
    private String password;
    private String nickname;
    private String intro;

    private Integer nuts;
    private String role;
    private int declaration;
    private String profile_img_filename;
    private Integer remainVote;


    public Member(String email, String password) {
        this.email = email;
        this.password = password;
    }

    //관계
    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL)
    private List<Mailbox> mailboxes = new ArrayList<>();

    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL)
    private List<NutsUsageHistory> nutsUsageHistories = new ArrayList<>();

    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL)
    private List<Lounge> lounges = new ArrayList<>();

    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL)
    private List<Comment> comments = new ArrayList<>();

    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL)
    private List<Reply> replies = new ArrayList<>();

    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL)
    private List<Follow> followings = new ArrayList<>();

    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL)
    private List<Bookmark> bookmarks = new ArrayList<>();

    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL)
    private List<PlaybackRecord> playbackRecords = new ArrayList<>();

    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL)
    private List<Playlist> playlists = new ArrayList<>();

    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL)
    private List<Chat> chats = new ArrayList<>();

    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL)
    private List<FreeBoard> freeBoards = new ArrayList<>();

    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL)
    private List<Like> likes = new ArrayList<>();
}
