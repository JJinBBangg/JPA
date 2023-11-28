package study.querydsl.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;
import study.querydsl.entity.Member;
import study.querydsl.entity.Team;
import study.querydsl.response.MemberSearchCondition;
import study.querydsl.response.MemberTeamResponse;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class MemberRepositoryTest {

    EntityManager em;

    JPAQueryFactory query;

    MemberRepository memberRepository;

    TeamRepository teamRepository;

    @Autowired
    public MemberRepositoryTest(EntityManager em, MemberRepository memberRepository, TeamRepository teamRepository) {
        this.em = em;
        this.memberRepository = memberRepository;
        this.teamRepository = teamRepository;
        this.query = new JPAQueryFactory(em);

    }

    @BeforeEach
    void rollbackAndInitData() {
        memberRepository.deleteAll();
        teamRepository.deleteAll();
        Team teamA = Team.builder()
                .name("teamA")
                .build();
        Team teamB = Team.builder()
                .name("teamB")
                .build();
        em.persist(teamA);
        em.persist(teamB);

        Member member1 = Member.builder()
                .username("member1")
                .age(11)
                .team(teamA)
                .build();
        Member member2 = Member.builder()
                .username("member2")
                .age(22)
                .team(teamA)
                .build();
        Member member3 = Member.builder()
                .username("member3")
                .age(33)
                .team(teamB)
                .build();
        Member member4 = Member.builder()
                .username("member4")
                .age(44)
                .team(teamB)
                .build();
        em.persist(member1);
        em.persist(member2);
        em.persist(member3);
        em.persist(member4);

    }

    @Test
    @DisplayName("")
    void test1() throws Exception {
        //given
        MemberSearchCondition condition = MemberSearchCondition.builder()
//                .username("member1")
//                .teamName("teamA")
                .ageGoe(20)
                .ageLoe(40)
                .build();
        //when
        List<MemberTeamResponse> findMembers = memberRepository.findMemberWithTeam(condition);

        //then
        findMembers.stream().forEach(System.out::println);
    }
}