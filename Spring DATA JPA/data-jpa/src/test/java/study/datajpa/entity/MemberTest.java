package study.datajpa.entity;

import jakarta.persistence.EntityManager;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;
import study.datajpa.repository.MemberRepository;
import study.datajpa.repository.TeamRepository;

import java.util.List;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
@Rollback(false)
class MemberTest {

    @Autowired EntityManager em;
    @Autowired MemberRepository memberRepository;
    @Autowired TeamRepository teamRepository;

    @BeforeEach
    void beForeEachMethod(){
        memberRepository.deleteAll();
    }


    @Test
    @DisplayName("회원 등록")
    void test1(){
        //given
        Team team1 = Team.builder()
                .name("teamA")
                .build();
        Team team2 = Team.builder()
                .name("teamB")
                .build();
        Team team3 = Team.builder()
                .name("teamC")
                .build();
        em.persist(team1); // cascadeType.ALL 설정 시 추가하지 않아도 member.Builder().team(team) 타고 등록됨
        em.persist(team2); // cascadeType.ALL 설정 시 추가하지 않아도 member.Builder().team(team) 타고 등록됨
        em.persist(team3); // cascadeType.ALL 설정 시 추가하지 않아도 member.Builder().team(team) 타고 등록됨

        Member member1 = Member.builder()
                .name("memberA")
                .age(33)
                .team(team1)
                .build();
        Member member2 = Member.builder()
                .name("memberB")
                .age(34)
                .team(team2)
                .build();

        em.persist(member1);
        em.persist(member2);
        em.flush();
        em.clear();

        //when
        List<Member> findMember = em.createQuery("select m from Member m", Member.class).getResultList();
        //then
        assertThat(findMember.get(0).getTeam().getName()).isEqualTo(team1.getName());

    }

    @Test
    @DisplayName("회원의 팀을 변경")
    void test2() {
    }
}