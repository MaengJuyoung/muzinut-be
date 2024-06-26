package nuts.muzinut.repository.board;

import nuts.muzinut.domain.board.FreeBoard;
import nuts.muzinut.domain.board.Lounge;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface LoungeRepository extends JpaRepository<Lounge, Long> {

    @Query(value = "select l from Lounge l join fetch l.user where l.id = :id")
    Optional<Lounge> findLoungeWithUser(@Param("id") Long boardId);

    @Modifying
    @Query("update Lounge l set l.filename = :filename where l.id = :id")
    void updateLounge(@Param("filename") String filename, @Param("id") Long id);

}
