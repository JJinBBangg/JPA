package study.querydsl;

import com.querydsl.core.QueryResults;
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
import study.querydsl.repository.MemberRepository;
import study.querydsl.repository.TeamRepository;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static study.querydsl.entity.QMember.member;

@SpringBootTest
@Transactional
@Rollback(value = false)
public class QuerydslBasicTest {

    EntityManager em;

    JPAQueryFactory query;

    MemberRepository memberRepository;

    TeamRepository teamRepository;

    @Autowired
    public QuerydslBasicTest(EntityManager em, MemberRepository memberRepository, TeamRepository teamRepository) {
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
    @DisplayName("JPQL test")
    void test1() {
        Member findMember = em.createQuery("select m from Member m where m.username = :username", Member.class)
                .setParameter("username", "member1")
                .getSingleResult();

        assertEquals("member12", findMember.getUsername(), "member1을 찾고 이름비교");

    }

    @Test
    @DisplayName("Querydsl test")
    void test2() {
//        QMember qMember = new QMember("m");
//        QMember qMember = QMember.member;

        Member findMember = query
                .select(member)
                .from(member)
                .where(member.username.eq("member1")).fetchOne();

        assertEquals("member1", findMember.getUsername(), "member1을 찾고 이름비교");
    }

    @Test
    @DisplayName("Querydsl 검색쿼리")
    void test3() {
//        List<Member> findMembers = query
//                .select(member)
//                .from(member)
//                .where(member.username.eq("member1")
//                        .and(member.age.gt(10)))
//                .fetch();


        // where 절의 검색 조건은 ,로 이어나갈 시 and 조건으로 작성됨
        List<Member> findMembers = query
                .select(member)
                .from(member)
                .where(
                        member.username.eq("member1"),
                        member.age.gt(10)
                )
                .fetch();
        assertEquals("member1", findMembers.get(0).getUsername());
    }

    @Test
    @DisplayName("Result Type")
    void test4(){
        List<Member> fetch = query
                .selectFrom(member)
                .fetch();

        Member fetchOne = query
                .selectFrom(member)
                .fetchOne();

        Member fetchFirst = query
                .selectFrom(member)
//                .limit(1).fetchOne();
                .fetchFirst();

        QueryResults<Member> fetchResults = query
                .selectFrom(member)
                .fetchResults();

        long total = fetchResults.getTotal();
        long limit = fetchResults.getLimit();
        long offset = fetchResults.getOffset();
        List<Member> results = fetchResults.getResults();

        long totalCount = query
                .selectFrom(member)
                .fetchCount();
    }

}
