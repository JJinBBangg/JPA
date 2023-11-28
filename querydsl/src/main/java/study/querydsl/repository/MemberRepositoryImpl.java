package study.querydsl.repository;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Predicate;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.util.StringUtils;
import study.querydsl.entity.Member;
import study.querydsl.entity.QTeam;
import study.querydsl.response.*;

import java.util.List;
import java.util.Optional;

import static org.springframework.util.StringUtils.*;
import static study.querydsl.entity.QMember.member;
import static study.querydsl.entity.QTeam.*;

public class MemberRepositoryImpl implements MemberRepositoryCustom{

    private final EntityManager em;
    private final JPAQueryFactory query;

    public MemberRepositoryImpl(EntityManager em) {
        this.em = em;
        this.query = new JPAQueryFactory(em);
    }

    @Override
    public List<MemberResponse> findMembers() {
        return query
                .select(new QMemberResponse(member.username,member.age))
                .from(member)
                .fetch();
    }

    public void save(Member member){
        em.persist(member);
    }

    public Optional<Member> findById(Long id){
        return Optional.ofNullable(em.find(Member.class, id));
    }

    public List<Member> findAll(){
        return em.createQuery("select m from Member m", Member.class)
                .getResultList();
    }

    public List<Member> findByUsername(String username){
        return em.createQuery("select m from Member m where m.username = :username")
                .setParameter("username", username)
                .getResultList();
    }

    public List<MemberTeamResponse> findMemberWithTeam(MemberSearchCondition condition){
        return query
                .select(new QMemberTeamResponse(
                        member.id.as("memberId"),
                        member.username,
                        member.age,
                        team.id.as("teamId"),
                        team.name.as("teamName")))
                .from(member)
                .join(member.team, team)
                .where(search(condition))
                .fetch();
    }
    private Predicate search(MemberSearchCondition condition){
        BooleanBuilder builder = new BooleanBuilder();
        if(hasText(condition.getUsername())) builder.and(userNameEq(condition.getUsername()));
        if(hasText(condition.getTeamName())) builder.and(teamNameEq(condition.getTeamName()));
        if(condition.getAgeGoe() != null) builder.and(ageGoe(condition.getAgeGoe()));
        if(condition.getAgeLoe() != null) builder.and(ageLoe(condition.getAgeLoe()));
        return builder;
    }
    private BooleanExpression userNameEq(String condition) {
        if(hasText(condition)) return member.username.eq(condition);
        return null;
    }
    private BooleanExpression teamNameEq(String condition) {
        if(hasText(condition)) return team.name.eq(condition);
        return null;
    }
    private BooleanExpression ageGoe(Integer condition){
        if(condition != null) return member.age.goe(condition);
        return null;
    }
    private BooleanExpression ageLoe(Integer condition){
        if(condition != null) return member.age.loe(condition);
        return null;
    }
}

