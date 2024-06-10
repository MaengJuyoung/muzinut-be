package nuts.muzinut.domain.member;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
public class Follow {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "following_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @Column(name = "following_member_id")
    private Long followingMemberId;

    private boolean notification;

    public Follow(Member member, Long followingMemberId, boolean notification) {
        this.member = member;
        this.followingMemberId = followingMemberId;
        this.notification = notification;
    }
}