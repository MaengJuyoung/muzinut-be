package nuts.muzinut.repository.member;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.extern.slf4j.Slf4j;
import nuts.muzinut.domain.member.Follow;
import nuts.muzinut.domain.member.Member;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
@Slf4j
class FollowRepositoryTest {

    @Autowired MemberRepository memberRepository;
    @Autowired FollowRepository followRepository;
    @PersistenceContext EntityManager em;

    /**
     * 1명의 회원이 2명의 회원을 팔로우
     */
    @Test
    void basicAddFollow() {

        //given
        Member m1 = new Member("m1", "1234");
        Member m2 = new Member("m2", "1234");
        Member m3 = new Member("m3", "1234");

        memberRepository.save(m1);
        memberRepository.save(m2);
        memberRepository.save(m3);

        Follow follow1 = new Follow();
        Follow follow2 = new Follow();

        //when
        follow1.createFollow(m1, m2.getId()); //m1 이 m2를 팔로우 했음
        follow2.createFollow(m1, m3.getId()); //m1 이 m3를 팔로우 했음

        //then
        List<Follow> result = followRepository.findAll();

        assertEquals(2, result.size()); //현재 팔로우에 대한 정보는 총 2개

        //m1 이 m2, m3를 팔로워 함
        assertTrue(result.stream().anyMatch(f -> f.getFollowingMemberId().equals(m2.getId())));
        assertTrue(result.stream().anyMatch(f -> f.getFollowingMemberId().equals(m3.getId())));

        //팔로우를 한 사람은 m1밖에 없음
        assertTrue(result.stream().allMatch(f -> f.getMember().equals(m1)));
    }
}
