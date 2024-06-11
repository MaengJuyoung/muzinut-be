package nuts.muzinut.domain.board.admin;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
public class AdminUploadFile {

    @Id
    @GeneratedValue
    @Column(name = "admin_upload_file_id")
    private Long id;

    @ManyToOne(fetch =  FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "admin_board_id")
    private AdminBoard adminBoard;

    private String storeFilename;
    private String originFilename;

    //연관 관계 메서드
    public void addFiles(AdminBoard adminBoard) {
        this.adminBoard = adminBoard;
        adminBoard.getUploadFiles().add(this);
    }
}