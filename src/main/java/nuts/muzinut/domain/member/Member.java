package nuts.muzinut.domain.member;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import nuts.muzinut.domain.board.Bookmark;
import nuts.muzinut.domain.board.Like;
import nuts.muzinut.domain.board.Lounge;
import nuts.muzinut.domain.board.recruit.RecruitBoard;
import nuts.muzinut.domain.chat.ChatMember;
import nuts.muzinut.domain.chat.Message;
import nuts.muzinut.domain.music.Music;
import nuts.muzinut.domain.music.PleNut;
import nuts.muzinut.domain.music.Playlist;
import nuts.muzinut.domain.baseEntity.BaseTimeEntity;
import nuts.muzinut.domain.board.FreeBoard;
import nuts.muzinut.domain.chat.Chat;
import nuts.muzinut.domain.board.comment.Comment;
import nuts.muzinut.domain.board.comment.Reply;
import nuts.muzinut.domain.nuts.NutsUsageHistory;
import nuts.muzinut.domain.nuts.PaymentHistory;
import nuts.muzinut.domain.nuts.SupportMsg;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter @Setter
@NoArgsConstructor
public class Member extends BaseTimeEntity {

    @Id @GeneratedValue
    @Column(name = "member_id")
    private Long id;

    private String email;
    private String password;
    private String nickname;
    private String intro;
    private int nuts;
    private int declaration;
    private int remainVote;

    @Column(name = "role")
    @Enumerated(EnumType.STRING)
    private Role role;


    @Column(name = "profile_img_filename")
    private String profile_img_filename;

    @Column(name = "account_number")
    private int accountNumber; //계좌 번호

    public Member(String email, String password) {
        this.email = email;
        this.password = password;
    }

    //관계
    // 회원
    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL)
    private List<Mailbox> mailboxes = new ArrayList<>();

    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL)
    private List<Follow> followings = new ArrayList<>();

    // 게시판
    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL)
    private List<Lounge> lounges = new ArrayList<>();

    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL)
    private List<Comment> comments = new ArrayList<>();

    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL)
    private List<Reply> replies = new ArrayList<>();

    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL)
    private List<Bookmark> bookmarks = new ArrayList<>();

    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL)
    private List<FreeBoard> freeBoards = new ArrayList<>();

    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL)
    private List<RecruitBoard> recruitBoards = new ArrayList<>();

    @OneToMany(mappedBy = "member")
    private List<Like> likeList = new ArrayList<>();

    // 음악
    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL)
    private List<Music> musicList = new ArrayList<>();

    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL)
    private List<PleNut> pleNut = new ArrayList<>();

    @OneToOne(mappedBy = "member", cascade = CascadeType.ALL)
    private Playlist playlist;

    // 채팅
    @OneToMany(mappedBy = "member")
    private List<ChatMember> chatMembers = new ArrayList<>();

    @OneToMany(mappedBy = "member")
    private List<Message> messages = new ArrayList<>();

    // 넛츠
    @OneToMany(mappedBy = "member")
    private List<PaymentHistory> paymentHistories = new ArrayList<>();

    @OneToMany(mappedBy = "member")
    private List<SupportMsg> supportMsgs = new ArrayList<>();

    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL)
    private List<NutsUsageHistory> nutsUsageHistories = new ArrayList<>();

}
