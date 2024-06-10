package nuts.muzinut.domain.chat;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import nuts.muzinut.domain.member.Member;

@Entity
@Getter
@NoArgsConstructor
@Table(name = "read_message")
public class ReadMessage {

    @Id
    @GeneratedValue
    @Column(name = "read_message_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "message_id")
    private Message message;

    @Column(name = "member_id")
    private Long memberId;

    // 연관 관계 메소드
    public void read(Member member, Message message) {
        this.memberId = member.getId();
        this.message = message;
        message.getReadMessages().add(this);

    }
}
