package nuts.muzinut.repository.board;

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
        assertTrue(findComment.isPresent());
        assertEquals(findComment.get(), comment);
        assertEquals(findComment.get().getMember(), member);
        assertEquals(findComment.get().getBoardId(), music.getId());
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
        assertTrue(findComment.isPresent());
        assertEquals(findComment.get(), comment);
        assertEquals(findComment.get().getMember(), member);
        assertEquals(findComment.get().getBoardId(), freeBoard.getId());
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
        assertTrue(findComment.isPresent());
        assertEquals(findComment.get(), comment);
        assertEquals(findComment.get().getMember(), member);
        assertEquals(findComment.get().getBoardId(), lounge.getId());
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
        assertTrue(findComment.isPresent());
        assertEquals(findComment.get(), comment);
        assertEquals(findComment.get().getMember(), member);
        assertEquals(findComment.get().getBoardId(), recruitBoard.getId());
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
        assertTrue(findComment.isEmpty());
    }

    // 게시판이 삭제될 때 해당 게시판의 댓글이 삭제되는 로직은 cascade로 구현하지 않았기에 따로 쿼리를 작성해야 함.
}
