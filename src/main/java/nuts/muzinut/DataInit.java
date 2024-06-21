package nuts.muzinut;

import jakarta.annotation.PostConstruct;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.PersistenceContext;
import lombok.RequiredArgsConstructor;
import nuts.muzinut.domain.board.AdminBoard;
import nuts.muzinut.domain.board.RecruitBoard;
import nuts.muzinut.domain.board.*;
import nuts.muzinut.domain.member.User;
import nuts.muzinut.dto.member.UserDto;
import nuts.muzinut.dto.security.AuthorityDto;
import nuts.muzinut.repository.board.AdminBoardRepository;
import nuts.muzinut.repository.board.RecruitBoardRepository;
import nuts.muzinut.repository.board.CommentRepository;
import nuts.muzinut.repository.board.LikeRepository;
import nuts.muzinut.repository.board.ReplyRepository;
import nuts.muzinut.repository.member.UserRepository;
import nuts.muzinut.service.security.UserService;
import org.hibernate.Hibernate;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import jakarta.persistence.NoResultException;

import java.time.LocalDateTime;

@Component
@RequiredArgsConstructor
public class DataInit {

    private final UserRepository userRepository;
    private final UserService userService;
    private final AdminBoardRepository adminBoardRepository;
    private final RecruitBoardRepository recruitBoardRepository;
    private final CommentRepository commentRepository;
    private final LikeRepository likeRepository;
    private final ReplyRepository replyRepository;
    @PersistenceContext
    EntityManager em;


    @PostConstruct
    @Transactional
    public void init() {
        createUsers();
//        adminBoardScenario();
        recruitBoardBoardScenario();
    }

    private void createUsers() {
        AuthorityDto authorityDto = new AuthorityDto("admin");
        UserDto userDto = new UserDto("admin@naver.com", "admin", "add!");
        userService.adminSignup(userDto);
        UserDto userDto2 = new UserDto("user@naver.com", "user", "user!");
        userService.signup(userDto2);
        UserDto userDto3 = new UserDto("user2@naver.com", "user2", "user2!");
        userService.signup(userDto3);
    }

    public void adminBoardScenario() {
        User user1 = new User();
        user1.setNickname("tom");
        User user2 = new User();
        user2.setNickname("nick");

        Board adminBoard = new AdminBoard();

        Comment comment1 = new Comment();
        comment1.addComment(user1, adminBoard, "sample");
        Reply reply1 = new Reply();
        reply1.addReply(comment1, "댓글1", user1);
//        replyRepository.save(reply1);

        Comment comment2 = new Comment();
        comment2.addComment(user1, adminBoard, "sample");
        Reply reply2 = new Reply();
        reply2.addReply(comment1, "댓글2", user1);
        replyRepository.save(reply2);
    }

    private void createAdminBoards() {
        for (int i = 0; i < 20; i++) {
            AdminBoard adminBoard = new AdminBoard();
            adminBoardRepository.save(adminBoard);
        }
    }

    private AdminBoard createBoard() {
        AdminBoard adminBoard = new AdminBoard();
        return adminBoardRepository.save(adminBoard);
    }

    private Comment createComment(User user, Board board) {
        Comment comment = new Comment();
        comment.addComment(user, board, "sample");
        return commentRepository.save(comment);
    }

    private Comment createCommentReply(User user, Board board, Reply reply) {
        Comment comment = new Comment();
        comment.addComment(user, board, "sample");
        reply.addReply(comment, "댓글1", user);
        return commentRepository.save(comment);
    }

    private Like createLike(User user, Board board) {
        Like like = new Like();
        like.addLike(user, board);
        return likeRepository.save(like);
    }

    private void clearContext() {
        em.flush();
        em.clear();
    }

    @Transactional
    public void recruitBoardBoardScenario() {
        try {
            // 기존 유저를 데이터베이스에서 조회하거나 새로 생성하여 회원가입합니다.
            UserDto userDto1 = new UserDto("tom@naver.com", "tom", "tom");
            userService.signup(userDto1);
            User user1 = userRepository.findByNickname("tom").orElseThrow(() -> new IllegalArgumentException("User not found"));

            UserDto userDto2 = new UserDto("nick@naver.com", "nick", "nick");
            userService.signup(userDto2);
            User user2 = userRepository.findByNickname("nick").orElseThrow(() -> new IllegalArgumentException("User not found"));

            // User 객체를 초기화합니다.
            Hibernate.initialize(user1.getRecruitBoards());
            Hibernate.initialize(user2.getRecruitBoards());

            // RecruitBoard 객체를 생성하고 작성자를 설정합니다.
            RecruitBoard recruitBoard = new RecruitBoard(user1, "Sample Content", 5,
                    LocalDateTime.now(), LocalDateTime.now().plusDays(7),
                    LocalDateTime.now().plusDays(8), LocalDateTime.now().plusDays(14),
                    "Sample Title");

            // comment1을 생성하고 recruitBoard에 추가합니다.
            Comment comment1 = new Comment();
            comment1.addComment(user1, recruitBoard, "sample");
            commentRepository.save(comment1); // comment1 저장

            Reply reply1 = new Reply();
            reply1.addReply(comment1, "댓글1", user1);
            replyRepository.save(reply1); // reply1 저장

            // comment2를 생성하고 recruitBoard에 추가합니다.
            Comment comment2 = new Comment();
            comment2.addComment(user1, recruitBoard, "sample");
            commentRepository.save(comment2); // comment2 저장

            Reply reply2 = new Reply();
            reply2.addReply(comment2, "댓글2", user1);
            replyRepository.save(reply2); // reply2 저장

            // RecruitBoard를 데이터베이스에 저장합니다.
            recruitBoardRepository.save(recruitBoard);
        } catch (Exception e) {
            throw new RuntimeException("Error during recruit board scenario", e);
        }
    }

}
