package nuts.muzinut.domain.music;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
public class PlaybackRecordList {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "playback_record_list_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "playback_record_id")
    private PlaybackRecord playbackRecord;

    private Long musicId; // 실제 음악 엔티티와의 연결은 생략하고, ID 참조로만 표현합니다.
}