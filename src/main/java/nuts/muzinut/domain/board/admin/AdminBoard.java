package nuts.muzinut.domain.board.admin;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import nuts.muzinut.domain.baseEntity.BaseBoardEntity;
import nuts.muzinut.domain.baseEntity.BaseTimeEntity;
import nuts.muzinut.domain.member.Member;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor
public class AdminBoard extends BaseBoardEntity {

    @Id
    @GeneratedValue
    @Column(name = "admin_board_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "member_id")
    private Member member;

    private String content;

    @OneToMany(mappedBy = "adminBoard", cascade = CascadeType.ALL)
    private List<AdminUploadFile> uploadFiles = new ArrayList<>();

    // 생성자
    public AdminBoard(Member member, String content) {
        this.member = member;
        this.content = content;
    }
    public void setContent(String content) {
        this.content = content;
    }
}