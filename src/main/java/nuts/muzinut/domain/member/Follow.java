package nuts.muzinut.domain.member;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
public class Follow {
    @Id
    @GeneratedValue
    @Column(name = "follow_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "member_id")
    private Member member;

    @Column(name = "following_member_id")
    private Long followingMemberId;

    private boolean notification;

    // 연관 관계 메서드
    public void createFollow(Member member, Long followingMemberId) {
        this.member = member;
        this.followingMemberId = followingMemberId;
        this.notification = true;
        member.getFollowings().add(this);
    }
}