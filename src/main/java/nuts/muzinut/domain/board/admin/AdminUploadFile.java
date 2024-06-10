package nuts.muzinut.domain.board.admin;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
public class AdminUploadFile {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "admin_upload_file_id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "admin_board_id")
    private AdminBoard adminBoard;

    private String storeFilename;
    private String originFilename;
}