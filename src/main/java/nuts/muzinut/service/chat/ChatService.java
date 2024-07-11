package nuts.muzinut.service.chat;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import nuts.muzinut.domain.chat.Chat;
import nuts.muzinut.domain.chat.ChatMember;
import nuts.muzinut.domain.chat.Message;
import nuts.muzinut.domain.member.User;
import nuts.muzinut.exception.NotFoundEntityException;
import nuts.muzinut.exception.NotFoundMemberException;
import nuts.muzinut.repository.chat.ChatMemberRepository;
import nuts.muzinut.repository.chat.ChatRepository;
import nuts.muzinut.repository.chat.MessageRepository;
import nuts.muzinut.repository.chat.ReadMessageRepository;
import nuts.muzinut.repository.member.UserRepository;
import nuts.muzinut.service.member.RedisUtil;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;
import java.util.stream.Collectors;

@Slf4j
@Transactional
@Service
@RequiredArgsConstructor
public class ChatService {

    private final ChatRepository chatRepository;
    private final ChatMemberRepository chatMemberRepository;
    private final MessageRepository messageRepository;
    private final UserRepository userRepository;
    private final RedisUtil redisUtil;

    public Chat findChatRoom(Long id) {
        return chatRepository.findById(id).orElseThrow(() -> new NotFoundEntityException("없는 채팅방입니다"));
    }

    /**
     * @param user1: 채팅방 첫번째 참가 유저 (닉네임)
     * @param user2: 채팅방 두번째 참가 유저 (닉네임)
     * @return
     */
    public Chat createRoom(String user1, String user2) {
        List<User> users = userRepository.findUsersByNickname(user1, user2);

        if (users.size() != 2) {
            throw new NotFoundMemberException("채팅은 두명의 회원과 이루어져야 합니다");
        }

        Chat chat = chatRepository.save(new Chat()); //새로운 채팅방 생성
        for (User user : users) {
            ChatMember chatMember = new ChatMember(user, chat); //user 채팅방 참가
            chatMemberRepository.save(chatMember);
        }
        return chat;
    }

    public void connectChatRoom(String chatRoomNumber, String username) {
//        log.info("chatService connectChatRoom");
        List<String> redisData = redisUtil.getMultiData(chatRoomNumber);
        Chat chat = chatRepository.findById(Long.parseLong(chatRoomNumber)).orElseThrow(
                () -> new NotFoundEntityException("없는 채팅방입니다"));

        //채팅방에 접속한 유저가 없는 경우
        if (redisData.isEmpty()) {
            log.info("채팅방 1명 접속");
            redisUtil.setMultiData(chatRoomNumber, username);

        } else if (redisData.size() == 1 && !redisData.getFirst().equals(username)){
            log.info("채팅방 2명 접속");
            log.info("이미 접속중인 사용자: {}", redisData.getFirst());
            redisUtil.setMultiData(chatRoomNumber, username);
            messageRepository.updateAllRead(chat); //모든 메시지 읽음 처리
        }
    }

    public void disconnectChatRoom(String chatRoomNumber, String username) {
        log.info("chatService disconnectChatRoom");
        List<String> redisData = redisUtil.getMultiData(chatRoomNumber);

        //기존에 두명의 채팅방 참가자가 있는 경우
        if (redisData.size() == 2 && redisData.contains(username)) {
            //채팅방을 나가지 않은 사용자
            String notExitUser = redisData.stream().filter(Predicate.not(r -> r.equals(username))).findFirst()
                    .orElseThrow(() -> new NotFoundMemberException("잘못된 회원이 채팅방을 나가려고 합니다"));
            log.info("{} 채팅방 2명중 한명 퇴장: {}", chatRoomNumber, notExitUser);
            redisUtil.deleteData(chatRoomNumber); //삭제 후 생성
            redisUtil.setMultiData(chatRoomNumber, notExitUser); //덮어쓰기

        } else if (redisData.size() == 1 && redisData.getFirst().equals(username)) {
            log.info("채팅방 모두 퇴장");
            redisUtil.deleteData(chatRoomNumber);
        } else {
            log.info("채팅방에 접속중인 사용자가 없었음");
        }
    }
<<<<<<< HEAD
=======

    // 맞팔되어 있는 사용자 목록을 가져오는 메서드
    /**
     * @param userId: 현재 사용자 ID
     * @return 맞팔 되어 있는 사용자 리스트
     */
    public List<User> getMutualFollowUsers(Long userId) {
        List<Long> mutualFollowIds = followRepository.findMutualFollowIds(userId);
        return userRepository.findAllById(mutualFollowIds);
    }
>>>>>>> parent of f8fb2fc (맞팔 목록 불러오는 메소드 개발 완료)
}
