package nuts.muzinut.repository.board.query;

import com.querydsl.core.Tuple;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.CaseBuilder;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import nuts.muzinut.domain.board.QLounge;
import nuts.muzinut.domain.member.QUser;
import nuts.muzinut.domain.member.User;
import nuts.muzinut.dto.board.DetailBaseDto;
import nuts.muzinut.dto.board.comment.CommentDto;
import nuts.muzinut.dto.board.comment.ReplyDto;
import org.springframework.stereotype.Repository;

import java.util.List;

import static nuts.muzinut.domain.board.QAdminBoard.adminBoard;
import static nuts.muzinut.domain.board.QBoard.board;
import static nuts.muzinut.domain.board.QBookmark.bookmark;
import static nuts.muzinut.domain.board.QComment.comment;
import static nuts.muzinut.domain.board.QFreeBoard.freeBoard;
import static nuts.muzinut.domain.board.QLike.like;
import static nuts.muzinut.domain.board.QLounge.*;
import static nuts.muzinut.domain.board.QReply.reply;
import static nuts.muzinut.domain.member.QUser.user;

@Slf4j
@RequiredArgsConstructor
@Repository
public class LoungeQueryRepository {

    private final JPAQueryFactory queryFactory;

/*    public List<Tuple> getDetailLounge(Long boardId) {

        return queryFactory
                .select(board, lounge,
                        JPAExpressions
                        .select(like.count())
                        .from(like)
                        .where(like.board.id.eq(boardId)))
                .from(board)
                .leftJoin(lounge).on(board.id.eq(lounge.id))
                .leftJoin(board.user, user).fetchJoin() //추가
                .leftJoin(board.comments, comment).fetchJoin()
                .leftJoin(comment.replies, reply)
                .where(board.id.eq(boardId))
                .fetch();
    }*/

    public List<Tuple> getDetailLounge(Long boardId, User user) {

        CaseBuilder caseBuilder = new CaseBuilder();

        BooleanExpression isLikeExpression = isLikeUserNotNullAndEquals(user, boardId);
        BooleanExpression isBookmarkExpression = isBookmarkUserNotNullAndEquals(user, boardId);

        return queryFactory
                .select(board, lounge,
                        Projections.fields(DetailBaseDto.class,
                                isLikeExpression.as("boardLikeStatus"),
                                isBookmarkExpression.as("isBookmark")))
                .from(board)
                .leftJoin(lounge).on(board.id.eq(lounge.id))
                .leftJoin(board.user, QUser.user) //추가
                .leftJoin(board.comments, comment).fetchJoin()
                .where(board.id.eq(boardId))
                .fetch();
    }

    private BooleanExpression isLikeUserNotNullAndEquals(User user, Long boardId) {
        if (user == null) {
            return Expressions.FALSE;
        } else {
            return JPAExpressions
                    .selectOne()
                    .from(like)
                    .where(like.board.id.eq(boardId)
                            .and(like.user.eq(user)))
                    .exists();
        }
    }

    private BooleanExpression isBookmarkUserNotNullAndEquals(User user, Long boardId) {
        if (user == null) {
            return Expressions.FALSE;
        } else {
            return JPAExpressions
                    .selectOne()
                    .from(bookmark)
                    .where(bookmark.board.id.eq(boardId)
                            .and(bookmark.user.eq(user)))
                    .exists();
        }
    }
}
