package study.querydsl.entity;

import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;
import study.querydsl.repository.MemberRepository;
import study.querydsl.repository.TeamRepository;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
@Rollback(value = false)
class EntityTest {

    EntityManager em;

    JPAQueryFactory query;

    MemberRepository memberRepository;

    TeamRepository teamRepository;

    @Autowired
    public EntityTest(EntityManager em, MemberRepository memberRepository, TeamRepository teamRepository) {
        this.em = em;
        this.query = new JPAQueryFactory(em);
        this.memberRepository = memberRepository;
        this.teamRepository = teamRepository;
    }

    @BeforeEach
    void rollbackAndInitData(){
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
    @DisplayName("회원등록")
    void test1() throws Exception{
        //given

        // when
        List<Member> members = em.createQuery("select m from Member m", Member.class)
                .getResultList();
        members.stream().forEach(System.out::println);

        //then
        assertEquals(members.size(), 4, "회원등록 검증");

    }

    @Test
    @DisplayName("test 중복데이터 제거 ")
    void test2() throws Exception{
        Member member1 = Member.builder()
                .username("member1")
                .age(11)
                .build();
        Member member2 = Member.builder()
                .username("member2")
                .age(22)
                .build();
        em.persist(member1);
        em.persist(member2);

        List<Member> members = em.createQuery("select m from Member m", Member.class)
                .getResultList();

        assertEquals(members.size(), 6,"등록된 회원 수는 2명이어야 한다.");
    }

}