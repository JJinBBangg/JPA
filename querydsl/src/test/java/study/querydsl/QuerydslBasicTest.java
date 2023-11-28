package study.querydsl;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.QueryResults;
import com.querydsl.core.Tuple;
import com.querydsl.core.types.ExpressionUtils;
import com.querydsl.core.types.Predicate;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.CaseBuilder;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.PersistenceUnit;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;
import study.querydsl.entity.Member;
import study.querydsl.entity.QMember;
import study.querydsl.entity.Team;
import study.querydsl.repository.MemberRepository;
import study.querydsl.repository.TeamRepository;
import study.querydsl.response.MemberResponse;
import study.querydsl.response.QMemberResponse;
import study.querydsl.response.UserResponse;

import java.util.List;

import static com.querydsl.jpa.JPAExpressions.select;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static study.querydsl.entity.QMember.member;
import static study.querydsl.entity.QTeam.team;

@SpringBootTest
@Transactional
@Rollback(value = false)
public class QuerydslBasicTest {

    EntityManager em;

    JPAQueryFactory query;

    MemberRepository memberRepository;

    TeamRepository teamRepository;

    @PersistenceUnit
    EntityManagerFactory emf;

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

        assertEquals("member1", findMember.getUsername(), "member1을 찾고 이름비교");

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
    void test4() {
        List<Member> fetch = query
                .selectFrom(member)
                .fetch();

        Member fetchOne = query
                .selectFrom(member)
                .where(member.username.eq("member1"))
                .fetchOne();

        Member fetchFirst = query
                .selectFrom(member)
//                .limit(1).fetchOne();
                .fetchFirst();

        QueryResults<Member> fetchResults = query
                .selectFrom(member)
                .fetchResults();
        // total count 를 가져와야하기떄문에 쿼리가 2번 실행됨

        long total = fetchResults.getTotal();
        long limit = fetchResults.getLimit();
        long offset = fetchResults.getOffset();
        List<Member> results = fetchResults.getResults();

        long totalCount = query
                .selectFrom(member)
                .fetchCount();
    }

    @Test
    @DisplayName("Querydsl Sort")
    void test5() {
        List<Member> members = query
                .selectFrom(member)
                .orderBy(member.age.desc(), member.username.asc().nullsLast())
                .fetch();

        members.stream().forEach(System.out::println);

    }

    @Test
    @DisplayName("Querydsl pageable")
    void test6() {
        List<Member> members = query
                .selectFrom(member)
                .orderBy(member.age.desc())
                .offset(0)
                .limit(2)
                .fetch();
        members.stream().forEach(System.out::println);
    }

    @Test
    @DisplayName("Querydsl aggregation")
    void test7() {
        Tuple tuple = query
                .select(
                        member.count(),
                        member.age.sum(),
                        member.age.avg(),
                        member.age.max(),
                        member.age.min()
                )
                .from(member)
                .fetchOne();

        System.out.println(tuple);
        // 아래처럼 Array 로 반환됨
        // [4, 110, 27.5, 44, 11]
        // 아래처럼 위에서 검색했던 각 값은 찍어서 검색이가능함
        Long totalCount = tuple.get(member.count());
        Integer sumAge = tuple.get(member.age.sum());
    }

    @Test
    @DisplayName("Querydsl aggregation 실습")
    void test8() throws Exception {
        //given
        List<Tuple> fetch = query
                .select(team.name, member.age.avg())
                .from(member)
                .join(member.team, team)
                .groupBy(team.name)
                .having(team.name.like("%team%"))
                .fetch();
        //when
        fetch.stream().forEach(System.out::println);
        // [teamA, 16.5]
        // [teamB, 38.5]

        Tuple teamA = fetch.get(0);
        Tuple teamB = fetch.get(1);

        //then
        assertEquals("teamA", teamA.get(team.name));
        assertEquals(16.5, teamA.get(member.age.avg()));
        assertEquals("teamB", teamB.get(team.name));
        assertEquals(38.5, teamB.get(member.age.avg()));
    }

    @Test
    @DisplayName("Querydsl Join")
    void test9() throws Exception {
        //given

        //when
        List<Member> members = query
                .select(member)
                .from(member)
                .join(member.team, team)
                .where(team.name.eq("teamA"))
                .fetch();
        //then
        assertEquals(2, members.size(), "검색된 회원의 수는 2명이어야 한다.");
    }

    @Test
    @DisplayName("Querydsl theta join")
    void test10() throws Exception {
        //given
        Member member1 = Member.builder()
                .username("teamA")
                .build();
        Member member2 = Member.builder()
                .username("teamB")
                .build();
        em.persist(member1);
        em.persist(member2);

        //when
        List<Member> members = query
                .select(member)
                .from(member, team)
                .where(member.username.eq(team.name))
                .fetch();
        //then
        Assertions.assertThat(members)
                .extracting("username")
                .containsExactly("teamA", "teamB");
    }

    @Test
    @DisplayName("Querydsl join on")
    void test11() throws Exception {
        //given

        //when
        query
                .select(member, team)
                .from(member)
                .leftJoin(member.team, team)
                .on(team.name.eq("teamA"))
                .fetch();
        //then
    }

    @Test
    @DisplayName("Querydsl No fetch join")
    void test12() throws Exception {
        //given
        em.flush();
        em.clear();

        //when

        // fetchType.LAZY
        Member findMember = query
                .select(member)
                .from(member)
                .where(member.username.eq("member1"))
                .fetchOne();
        boolean loaded = emf.getPersistenceUnitUtil().isLoaded(findMember.getTeam());


        //then
        assertEquals(false, loaded, "team 은 fetchType.lazy 이기때문에 false 를 반환해야한다");
    }

    @Test
    @DisplayName("Querydsl fetch join")
    void test13() throws Exception {
        //given
        em.flush();
        em.clear();

        //when
        Member findMember = query
                .select(member)
                .from(member)
                .join(member.team, team).fetchJoin()
                .where(member.username.eq("member1"))
                .fetchOne();

        boolean loaded = emf.getPersistenceUnitUtil().isLoaded(findMember.getTeam());
        //then
        assertEquals(true, loaded, "fetchJoin 을 사용해서 true 를 반환해야한다.");
    }

    @Test
    @DisplayName("Querydsl subQuery")
    void test14() throws Exception {
        //given
        QMember qMember = new QMember("subMember");
        //when
        Member findMember = query
                .selectFrom(member)
                .where(member.age.eq(
                        select(qMember.age.max())
                                .from(qMember)
                ))
                .fetchOne();


        //then
        assertEquals(44, findMember.getAge(), "나이가 가장많은 사람은 44살이다");
    }

    @Test
    @DisplayName("Querydsl select subQuery")
    void test15() throws Exception {
        //given
        QMember qMember = new QMember("subMember");

        //when
        query
                .select(member.username,
                        select(qMember.age.max())
                                .from(qMember))
                .from(member)
                .fetch();

        //then
    }

    @Test
    @DisplayName("Querydsl Basic Case")
    void test16() throws Exception {
        //given

        //when
        List<String> fetch = query
                .select(member.age
                        .when(10).then("10살")
                        .when(20).then("20살")
                        .otherwise("기타"))
                .from(member)
                .fetch();

        //then
    }

    @Test
    @DisplayName("Querydsl Complex Case")
    void test17() throws Exception {
        //given

        //when
        List<String> fetch = query
                .select(new CaseBuilder()
                        .when(member.age.between(1, 19)).then("미성년자")
                        .when(member.age.between(20, 100)).then("성인")
                        .otherwise("기타"))
                .from(member)
                .fetch();

        //then
    }

    @Test
    @DisplayName("Querydsl Concat")
    void test18() throws Exception {
        //given

        //when
        // 아래처럼 stringValue 를 자주사용하게 되는데
        // Enum 의 경우에도 값을 비교하거나 더할 때 stringValue 사용
        List<String> fetch = query
                .select(member.username.concat("_").concat(member.age.stringValue()))
                .from(member)
                .fetch();

        //then
    }

    @Test
    @DisplayName("Querydsl projection singleResult")
    void test19() throws Exception {
        //given

        //when
        List<String> fetch = query
                .select(member.username)
                .from(member)
                .fetch();

        //then
    }

    @Test
    @DisplayName("Querydsl Projection Tuple")
    void test20() throws Exception {
        //given

        //when
        List<Tuple> fetch = query
                .select(member.username, member.age)
                .from(member)
                .fetch();

        //then
    }

    @Test
    @DisplayName("Querydsl Projection DTO")
    void test21() throws Exception {
        //given

        //when
        //JPQL
        List<MemberResponse> selectMFromMemberM = em.createQuery(
                        "select new study.querydsl.response.MemberResponse( " +
                                "m.username," +
                                "m.age) " +
                                "from Member m", MemberResponse.class)
                .getResultList();

        // Querydsl DTO
        // 프로퍼티 접근 ->Setter + 기본생성자가 public 이어야함
        List<MemberResponse> fetch1 = query
                .select(Projections.bean(MemberResponse.class,
                        member.username,
                        member.age))
                .from(member)
                .fetch();
        fetch1.stream().forEach(System.out::println);

        // 필드 직접 접근
        List<MemberResponse> fetch2 = query
                .select(Projections.fields(MemberResponse.class,
                        member.username,
                        member.age))
                .from(member)
                .fetch();
        fetch2.stream().forEach(System.out::println);

        // 생성자 사용 -> constructor 가 public 이어야함
        List<MemberResponse> fetch3 = query
                .select(Projections.constructor(MemberResponse.class,
                        member.username,
                        member.age))
                .from(member)
                .fetch();
        fetch3.stream().forEach(System.out::println);
        //then
    }

    @Test
    @DisplayName("Querydsl DTO 주입 + 필드명이 다를 때")
    void test22() throws Exception {
        //given
        QMember memberSub = new QMember("memberSub");
        //when
        // 필드 직접 접근
        List<UserResponse> fetch = query
                .select(Projections.fields(UserResponse.class,
                        member.username.as("name"), // 필드명을 맞춰서 하지않으면 null 로 주입됨
                        //expressionUtils 사용하여 subQuery 의 alias 를 age 로 맞춰서 주입
                        ExpressionUtils.as(
                                select(memberSub.age.max())
                                        .from(memberSub), "age")
                ))
                .from(member)
                .fetch();
        fetch.stream().forEach(System.out::println);

        // 생성자 접근 - 필드명이 달라도 생성자의 파라미터 순서에 맞춰 주입(Type 이 맞아야함)
        List<UserResponse> fetch1 = query
                .select(Projections.constructor(UserResponse.class,
                        member.username,
                        member.age
                ))
                .from(member)
                .fetch();
        fetch1.stream().forEach(System.out::println);
        //then
    }

    @Test
    @DisplayName("Querydsl DTO 주입 @QueryProjection 사용")
    void test23() throws Exception {
        //given

        //when
        List<MemberResponse> fetch = query
                .select(new QMemberResponse(member.username, member.age))
                .from(member)
                .fetch();

        //then
        fetch.stream().forEach(System.out::println);
    }

    @Test
    @DisplayName("Querydsl 동적쿼리 BooleanBuilder")
    void test24() throws Exception {
        //given
        String usernameParam = "member1";
        Integer ageParam = 11;


        //when
        List<Member> findMembers1 = searchMember1(usernameParam, ageParam);
        List<Member> findMembers2 = searchMember1(null, ageParam);
        List<Member> findMembers3 = searchMember1(usernameParam, null);

        //then
        assertEquals(1, findMembers1.size(), "검색된 회원의 수가 1개");
    }

    private List<Member> searchMember1(String usernameParam, Integer ageParam) {
        BooleanBuilder builder = new BooleanBuilder();

        if (ageParam != null) builder.and(member.age.eq(ageParam));

        if (usernameParam != null) builder.and(member.username.eq(usernameParam));

        return query
                .select(member)
                .from(member)
                .where(builder)
                .fetch();

    }

    @Test
    @DisplayName("Querydsl 동적쿼리 where 다중 파라미터")
    void test25() throws Exception {
        //given
        String usernameParam = "member1";
        Integer ageParam = 11;


        //when
        List<Member> findMembers1 = searchMember2(usernameParam, ageParam);
        List<Member> findMembers2 = searchMember2(null, ageParam);
        List<Member> findMembers3 = searchMember2(usernameParam, null);

        //then
        assertEquals(1, findMembers1.size(), "검색된 회원의 수가 1개");
    }

    private List<Member> searchMember2(String usernameParam, Integer ageParam) {
        return query
                .select(member)
                .from(member)
                .where(usernameEq(usernameParam), ageEq(ageParam))
                .fetch();

    }

    // 재사용, 합성 조립 가능
    private BooleanExpression usernameEq(String usernameCond) {
        return usernameCond != null ? member.username.eq(usernameCond) : null;
    }

    private BooleanExpression ageEq(Integer ageCond) {
        return ageCond != null ? member.age.eq(ageCond) : null;
    }

    private BooleanExpression allEq(String usernameCond, Integer ageCond) {
        return usernameEq(usernameCond).and(ageEq(ageCond));
    }

    @Test
    @DisplayName("Querydsl bulkUpdate")
    void test26() throws Exception {
        //given

        //when
        long executed = query
                .update(member)
                .set(member.age, member.age.add(1))
                .set(member.username, "비회원")
                .where(member.age.lt(25))
                .execute();

        em.clear();
        //then
        List<Member> members = query
                .select(member)
                .from(member)
                .fetch();

        members.stream().forEach(System.out::println);

        List<MemberResponse> findMembers = memberRepository.findMembers();
        findMembers.stream().forEach(System.out::println);
    }
}
