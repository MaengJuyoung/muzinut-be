package nuts.muzinut.domain.admin;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import nuts.muzinut.domain.baseEntity.BaseTimeEntity;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor
public class AdminBoard extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "admin_board_id")
    private Long id;

    private Integer admin_id;
    private String title;
    private String content;
    private Integer view;

    @OneToMany(mappedBy = "adminBoard", cascade = CascadeType.ALL)
    private List<AdminUploadFile> uploadFiles = new ArrayList<>();
}