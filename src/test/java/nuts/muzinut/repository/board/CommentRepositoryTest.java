package nuts.muzinut.repository.board;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import nuts.muzinut.domain.board.*;
import nuts.muzinut.domain.board.comment.Comment;
import nuts.muzinut.domain.board.recruit.RecruitBoard;
import nuts.muzinut.domain.member.Member;
import nuts.muzinut.domain.music.Music;
import nuts.muzinut.repository.member.MemberRepository;
import nuts.muzinut.repository.music.MusicRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class CommentRepositoryTest {

    @Autowired MemberRepository memberRepository;
    @Autowired FreeBoardRepository freeBoardRepository;
    @Autowired LoungeRepository loungeRepository;
    @Autowired RecruitBoardRepository recruitBoardRepository;
    @Autowired MusicRepository musicRepository;
    @Autowired CommentRepository commentRepository;

    @PersistenceContext
    private EntityManager em;

    @Test
    void musicComment() {

        //given
        Member member = new Member();
        memberRepository.save(member);

        Music music = new Music();
        musicRepository.save(music);

        Comment comment = new Comment();
        comment.addComment(member, music.getId(), BoardType.MUSIC, "content");

        //when
        commentRepository.save(comment);

        //then
        Optional<Comment> findComment = commentRepository.findById(comment.getId());
        assertTrue(findComment.isPresent());  // 댓글이 존재하는지 확인
        assertEquals(findComment.get(), comment);  // 저장된 댓글과 조회된 댓글이 같은지 확인
        assertEquals(findComment.get().getMember(), member);  // 댓글 작성자가 맞는지 확인
        assertEquals(findComment.get().getBoardId(), music.getId());  // 댓글이 달린 음악 게시글 ID가 맞는지 확인
    }

    @Test
    void freeBoardComment() {

        //given
        Member member = new Member();
        memberRepository.save(member);

        FreeBoard freeBoard = new FreeBoard();
        freeBoardRepository.save(freeBoard);

        Comment comment = new Comment();
        comment.addComment(member, freeBoard.getId(), BoardType.FREE, "content");

        //when
        commentRepository.save(comment);

        //then
        Optional<Comment> findComment = commentRepository.findById(comment.getId());
        assertTrue(findComment.isPresent());  // 댓글이 존재하는지 확인
        assertEquals(findComment.get(), comment);  // 저장된 댓글과 조회된 댓글이 같은지 확인
        assertEquals(findComment.get().getMember(), member);  // 댓글 작성자가 맞는지 확인
        assertEquals(findComment.get().getBoardId(), freeBoard.getId());  // 댓글이 달린 자유 게시판 ID가 맞는지 확인
    }

    @Test
    void loungeComment() {

        //given
        Member member = new Member();
        memberRepository.save(member);

        Lounge lounge = new Lounge();
        loungeRepository.save(lounge);

        Comment comment = new Comment();
        comment.addComment(member, lounge.getId(), BoardType.LOUNGE, "content");

        //when
        commentRepository.save(comment);

        //then
        Optional<Comment> findComment = commentRepository.findById(comment.getId());
        assertTrue(findComment.isPresent());  // 댓글이 존재하는지 확인
        assertEquals(findComment.get(), comment);  // 저장된 댓글과 조회된 댓글이 같은지 확인
        assertEquals(findComment.get().getMember(), member);  // 댓글 작성자가 맞는지 확인
        assertEquals(findComment.get().getBoardId(), lounge.getId());  // 댓글이 달린 라운지 게시판 ID가 맞는지 확인
    }

    @Test
    void recruitBoardComment() {

        //given
        Member member = new Member();
        memberRepository.save(member);

        RecruitBoard recruitBoard = new RecruitBoard();
        recruitBoardRepository.save(recruitBoard);

        Comment comment = new Comment();
        comment.addComment(member, recruitBoard.getId(), BoardType.RECRUIT, "content");

        //when
        commentRepository.save(comment);

        //then
        Optional<Comment> findComment = commentRepository.findById(comment.getId());
        assertTrue(findComment.isPresent());  // 댓글이 존재하는지 확인
        assertEquals(findComment.get(), comment);  // 저장된 댓글과 조회된 댓글이 같은지 확인
        assertEquals(findComment.get().getMember(), member);  // 댓글 작성자가 맞는지 확인
        assertEquals(findComment.get().getBoardId(), recruitBoard.getId());  // 댓글이 달린 모집 게시판 ID가 맞는지 확인
    }

    @Test
    void delete() {

        //given
        Comment comment = new Comment();
        commentRepository.save(comment);

        //when
        commentRepository.delete(comment);

        //then
        Optional<Comment> findComment = commentRepository.findById(comment.getId());
        assertTrue(findComment.isEmpty());  // 댓글이 삭제되었는지 확인
    }

    // 게시판이 삭제될 때 해당 게시판의 댓글이 삭제되는 로직은 cascade로 구현하지 않았기에 따로 쿼리를 작성해야 함.
    @Test
    void updateCommentContent() {
        //given
        Member member = new Member();
        memberRepository.save(member);

        Music music = new Music();
        musicRepository.save(music);

        Comment comment = new Comment();
        comment.addComment(member, music.getId(), BoardType.MUSIC, "original content");
        commentRepository.save(comment);

        LocalDateTime beforeUpdate = comment.getModified_dt();

        //when
        comment.setContent("updated content");  // 댓글 내용을 업데이트
        commentRepository.save(comment);  // 변경된 내용을 저장

        // 엔티티 상태를 강제로 갱신하도록 변경
        em.flush();
        em.clear();

        //then
        Optional<Comment> findComment = commentRepository.findById(comment.getId());
        assertTrue(findComment.isPresent());  // 댓글이 존재하는지 확인
        assertEquals(findComment.get().getContent(), "updated content");  // 댓글 내용이 업데이트된 내용과 같은지 확인
        assertNotEquals(findComment.get().getModified_dt(), beforeUpdate);  // 수정된 날짜가 이전과 다른지 확인
    }

}
