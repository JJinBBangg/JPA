package study.querydsl.repository;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Predicate;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.JPQLQuery;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.springframework.data.support.PageableExecutionUtils;
import study.querydsl.entity.Member;
import study.querydsl.entity.QMember;
import study.querydsl.response.MemberSearchCondition;

import java.util.List;

import static com.querydsl.jpa.JPAExpressions.select;
import static com.querydsl.jpa.JPAExpressions.selectFrom;
import static org.springframework.util.StringUtils.hasText;
import static study.querydsl.entity.QMember.*;
import static study.querydsl.entity.QTeam.team;

public class MemberTestRepository extends Querydsl4RepositorySupport {

    public MemberTestRepository() {
        super(Member.class);
    }

    public List<Member> basicSelect(){
        return select(member)
                .from(member)
                .fetch();
    }
    public List<Member> basicSelectFrom(){
        return selectFrom(member)
                .fetch();
    }
    public Page<Member> memberPage(MemberSearchCondition condition, Pageable pageable){
        JPQLQuery<Member> query = selectFrom(member)
                .where(search(condition));

        JPQLQuery<Member> memberJPQLQuery = getQuerydsl().applyPagination(pageable, query);
        List<Member> content = memberJPQLQuery.fetch();

        return PageableExecutionUtils.getPage(content, pageable, query::fetchCount);
    }
    public Page<Member> memberPageApply(MemberSearchCondition condition, Pageable pageable){
        return applyPagination(pageable, query ->
                query.selectFrom(member)
                        .where(search(condition))
        );
    }
    public Page<Member> memberPageApply2(MemberSearchCondition condition, Pageable pageable){
        return applyPagination(pageable,
                query ->
                    query
                        .selectFrom(member)
                        .leftJoin(member.team, team)
                        .where(search(condition)),
                countQuery ->
                    countQuery
                        .select(member.id)
                        .from(member)
//                        .leftJoin(member.team, team)
                        .where(search(condition))

        );
    }
    private Predicate search(MemberSearchCondition condition) {
        BooleanBuilder builder = new BooleanBuilder();
        if (hasText(condition.getUsername())) builder.and(userNameEq(condition.getUsername()));
        if (hasText(condition.getTeamName())) builder.and(teamNameEq(condition.getTeamName()));
        if (condition.getAgeGoe() != null) builder.and(ageGoe(condition.getAgeGoe()));
        if (condition.getAgeLoe() != null) builder.and(ageLoe(condition.getAgeLoe()));
        if (hasText(condition.getUsernameLike())) builder.and(usernameLike(condition.getUsernameLike()));
        return builder;
    }

    private BooleanExpression userNameEq(String condition) {
        if (hasText(condition)) return member.username.eq(condition);
        return null;
    }

    private BooleanExpression teamNameEq(String condition) {
        if (hasText(condition)) return team.name.eq(condition);
        return null;
    }

    private BooleanExpression ageGoe(Integer condition) {
        if (condition != null) return member.age.goe(condition);
        return null;
    }

    private BooleanExpression ageLoe(Integer condition) {
        if (condition != null) return member.age.loe(condition);
        return null;
    }

    private BooleanExpression usernameLike(String condition) {
        if (hasText(condition)) return member.username.like("%" + condition + "%");
        return null;
    }
}


