package nuts.muzinut.domain.music;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import nuts.muzinut.domain.member.Member;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor
public class PleNut {
    @Id
    @GeneratedValue
    @Column(name = "plenut_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "member_id")
    private Member member;

    @OneToMany(mappedBy = "pleNut", cascade = CascadeType.ALL)
    private List<pleNutMusic> pleNutMusics = new ArrayList<>();

    // 연관 관계 메서드
    public void createPleNut(Member member){
        this.member = member;
        member.getPleNut().add(this);
    }
}