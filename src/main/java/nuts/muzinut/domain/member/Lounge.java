package nuts.muzinut.domain.member;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
public class Lounge {

    @Id
    @GeneratedValue
    @Column(name = "lounge_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    private String content;

    public Lounge(Member member, String content) {
        this.member = member;
        this.content = content;
    }
}