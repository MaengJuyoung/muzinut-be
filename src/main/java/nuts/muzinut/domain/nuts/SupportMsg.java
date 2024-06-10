package nuts.muzinut.domain.nuts;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import nuts.muzinut.domain.member.Member;

@Entity
@Getter
@NoArgsConstructor
@Table(name = "support_msg")
public class SupportMsg {

    @Id
    @GeneratedValue
    @Column(name = "support_msg_id")
    private Long id;

    @ManyToOne(fetch =  FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @Column(name = "sponsor_id")
    private Long sponsorId; //후원한 팬 pk

    private String message;

    // 연관 관계 메소드
    public void addSupportMsg(Member artist, Member sponsor, String message) {
        this.member = artist;
        this.sponsorId = sponsor.getId();
        this.message = message;
        artist.getSupportMsgs().add(this);
    }
}