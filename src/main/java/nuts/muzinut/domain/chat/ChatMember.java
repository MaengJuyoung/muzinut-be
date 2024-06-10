package nuts.muzinut.domain.chat;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import nuts.muzinut.domain.member.Member;

@Entity
@Getter @Setter
@Table(name = "chat_member")
public class ChatMember {

    @Id
    @GeneratedValue
    @Column(name = "chat_member_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "chat_id")
    private Chat chat;

    //채팅창 생성
    public void createChat(Chat chat) {
        this.chat = chat;
        chat.getChatMembers().add(this);
    }

    //채팅창에 참가
    public void joinChatRoom(Member member, Chat chat) {
        this.member = member;
        member.getChatMembers().add(this);
        chat.addParticipate(); //채팅방 인원수 증가
    }
}