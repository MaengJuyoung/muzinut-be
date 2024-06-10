package nuts.muzinut.domain.member;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import nuts.muzinut.domain.baseEntity.BaseTimeEntity;

@Entity
@Getter
@NoArgsConstructor
public class Message extends BaseTimeEntity {

    @Id
    @GeneratedValue
    @Column(name = "message_id")
    private Long id;

    private String content;
    private boolean isRead;

    // 관계
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "mailbox_id")
    private Mailbox mailbox;

    public Message(String content, Mailbox mailbox) {
        this.content = content;
        this.mailbox = mailbox;
        this.isRead = false;
    }

    public void markAsRead() {
        this.isRead = true;
    }
}