package nuts.muzinut.repository.board;

import nuts.muzinut.domain.board.*;
import nuts.muzinut.domain.board.recruit.RecruitBoard;
import nuts.muzinut.domain.member.Member;
import nuts.muzinut.repository.member.MemberRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class BookmarkRepositoryTest {

    @Autowired MemberRepository memberRepository;
    @Autowired FreeBoardRepository freeBoardRepository;
    @Autowired LoungeRepository loungeRepository;
    @Autowired RecruitBoardRepository recruitBoardRepository;
    @Autowired BookmarkRepository bookmarkRepository;

    @Test
    void bookmarkFreeBoard() {
        // 자유 게시판을 북마크하는 테스트

        //given
        Member member = new Member();
        memberRepository.save(member); // 회원 저장

        FreeBoard freeBoard = new FreeBoard();
        freeBoardRepository.save(freeBoard); // 자유 게시판 저장

        Bookmark bookmark = new Bookmark();
        bookmark.addBookmark(member, freeBoard.getId(), BoardType.FREE); // 북마크 생성 및 설정

        //when
        bookmarkRepository.save(bookmark); // 북마크 저장

        //then
        Optional<Bookmark> findBookmark = bookmarkRepository.findById(bookmark.getId());
        assertTrue(findBookmark.isPresent()); // 북마크가 존재하는지 확인
        assertEquals(bookmark, findBookmark.get()); // 저장한 북마크와 찾은 북마크가 같은지 확인
        assertEquals(member, findBookmark.get().getMember()); // 북마크의 회원이 맞는지 확인
        assertEquals(freeBoard.getId(), findBookmark.get().getBoardId()); // 북마크된 게시판 ID가 맞는지 확인
        assertEquals(BoardType.FREE, findBookmark.get().getBoardType()); // 게시판 타입이 맞는지 확인
    }

    @Test
    void bookmarkRecruitBoard() {
        // 모집 게시판을 북마크하는 테스트

        //given
        Member member = new Member();
        memberRepository.save(member); // 회원 저장

        RecruitBoard recruitBoard = new RecruitBoard();
        recruitBoardRepository.save(recruitBoard); // 모집 게시판 저장

        Bookmark bookmark = new Bookmark();
        bookmark.addBookmark(member, recruitBoard.getId(), BoardType.RECRUIT); // 북마크 생성 및 설정

        //when
        bookmarkRepository.save(bookmark); // 북마크 저장

        //then
        Optional<Bookmark> findBookmark = bookmarkRepository.findById(bookmark.getId());
        assertTrue(findBookmark.isPresent()); // 북마크가 존재하는지 확인
        assertEquals(bookmark, findBookmark.get()); // 저장한 북마크와 찾은 북마크가 같은지 확인
        assertEquals(member, findBookmark.get().getMember()); // 북마크의 회원이 맞는지 확인
        assertEquals(recruitBoard.getId(), findBookmark.get().getBoardId()); // 북마크된 게시판 ID가 맞는지 확인
        assertEquals(BoardType.RECRUIT, findBookmark.get().getBoardType()); // 게시판 타입이 맞는지 확인
    }

    @Test
    void bookmarkLounge() {
        // 라운지 게시판을 북마크하는 테스트

        //given
        Member member = new Member();
        memberRepository.save(member); // 회원 저장

        Lounge lounge = new Lounge();
        loungeRepository.save(lounge); // 라운지 게시판 저장

        Bookmark bookmark = new Bookmark();
        bookmark.addBookmark(member, lounge.getId(), BoardType.LOUNGE); // 북마크 생성 및 설정

        //when
        bookmarkRepository.save(bookmark); // 북마크 저장

        //then
        Optional<Bookmark> findBookmark = bookmarkRepository.findById(bookmark.getId());
        assertTrue(findBookmark.isPresent()); // 북마크가 존재하는지 확인
        assertEquals(bookmark, findBookmark.get()); // 저장한 북마크와 찾은 북마크가 같은지 확인
        assertEquals(member, findBookmark.get().getMember()); // 북마크의 회원이 맞는지 확인
        assertEquals(lounge.getId(), findBookmark.get().getBoardId()); // 북마크된 게시판 ID가 맞는지 확인
        assertEquals(BoardType.LOUNGE, findBookmark.get().getBoardType()); // 게시판 타입이 맞는지 확인
    }

    @Test
    void delete() {
        // 북마크 삭제 테스트

        //given
        Bookmark bookmark = new Bookmark();
        bookmarkRepository.save(bookmark); // 북마크 저장

        //when
        bookmarkRepository.delete(bookmark); // 북마크 삭제

        //then
        Optional<Bookmark> findBookmark = bookmarkRepository.findById(bookmark.getId());
        assertTrue(findBookmark.isEmpty()); // 북마크가 삭제되었는지 확인
    }

    //회원이 삭제되면 북마크 내용도 삭제된다
    @Test
    void deleteMember() {
        // 회원 삭제 시 북마크도 함께 삭제되는지 테스트

        //given
        Member member = new Member();
        memberRepository.save(member); // 회원 저장

        Lounge lounge = new Lounge();
        loungeRepository.save(lounge); // 라운지 게시판 저장

        Bookmark bookmark = new Bookmark();
        bookmark.addBookmark(member, lounge.getId(), BoardType.LOUNGE); // 북마크 생성 및 설정
        bookmarkRepository.save(bookmark); // 북마크 저장

        //when
        memberRepository.delete(member.getId()); // 회원 삭제

        //then
        Optional<Bookmark> findBookmark = bookmarkRepository.findById(bookmark.getId());
        assertTrue(findBookmark.isEmpty()); // 회원 삭제 시 북마크도 삭제되었는지 확인
    }
}
