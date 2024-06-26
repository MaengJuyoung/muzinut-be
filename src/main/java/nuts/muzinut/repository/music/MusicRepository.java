package nuts.muzinut.repository.music;

import nuts.muzinut.domain.music.Song;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MusicRepository extends JpaRepository<Song, Long> {
}
