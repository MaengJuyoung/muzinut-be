package nuts.muzinut.repository.board;

import nuts.muzinut.domain.board.Board;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface BoardRepository extends JpaRepository<Board, Long> {

    void deleteById(Long id);

    @Query(value = "SELECT DTYPE FROM BOARD WHERE board_id = :id", nativeQuery = true)
    String findBoardTypeById(@Param("id") Long id);
}
