package study.querydsl.repository;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.QueryResults;
import com.querydsl.core.types.Expression;
import com.querydsl.core.types.Predicate;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;
import org.springframework.util.StringUtils;
import study.querydsl.entity.Member;
import study.querydsl.entity.QTeam;
import study.querydsl.response.*;

import java.util.List;
import java.util.Optional;

import static org.springframework.util.StringUtils.*;
import static study.querydsl.entity.QMember.member;
import static study.querydsl.entity.QTeam.*;

@RequiredArgsConstructor
public class MemberRepositoryImpl implements MemberRepositoryCustom {

    private final EntityManager em;
    private final JPAQueryFactory query;

    @Override
    public Page<MemberResponse> findMembers(Pageable pageable) {
        QueryResults<MemberResponse> result = query
                .select(new QMemberResponse(member.username, member.age))
                .from(member)
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .orderBy(member.id.desc())
                .fetchResults();
        List<MemberResponse> members = result.getResults();
        long total = result.getTotal();

        return new PageImpl<>(members, pageable, total);
    }

    @Override
    public Page<MemberTeamResponse> findMemberWithTeam(MemberSearchCondition condition, Pageable pageable) {
        QueryResults<MemberTeamResponse> getResult = query
                .select(new QMemberTeamResponse(
                        member.id.as("memberId"),
                        member.username,
                        member.age,
                        team.id.as("teamId"),
                        team.name.as("teamName")))
                .from(member)
                .join(member.team, team)
                .where(search(condition)/*, usernameLike(condition.getUsername()*/)
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .orderBy(member.id.desc())
                .fetchResults();

        List<MemberTeamResponse> contents = getResult.getResults();
        long totalCount = getResult.getTotal();

        return new PageImpl<>(contents, pageable, totalCount);
    }

    @Override
    public Page<MemberTeamResponse> findMemberWithTeamV2(MemberSearchCondition condition, Pageable pageable) {
        List<MemberTeamResponse> contents = query
                .select(new QMemberTeamResponse(
                        member.id.as("memberId"),
                        member.username,
                        member.age,
                        team.id.as("teamId"),
                        team.name.as("teamName")))
                .from(member)
                .leftJoin(member.team, team)
                .where(search(condition))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .orderBy(/*member.id.desc()*/)
                .fetch();

        Long totalCount = query
                .select(member)
                .from(member)
                .join(member.team, team)
                .where(search(condition))
                .fetchCount();

        JPAQuery<Member> memberJPAQuery = query
                .select(member)
                .from(member)
                .join(member.team, team)
                .where(search(condition));

//        return new PageImpl<>(contents, pageable, totalCount);
        //page 최적화
        return PageableExecutionUtils.getPage(contents, pageable, memberJPAQuery::fetchCount);
    }

    // 내부 메서드
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
        return (hasText(condition)) ? member.username.eq(condition) : null;
    }

    private BooleanExpression teamNameEq(String condition) {
        return (hasText(condition)) ? team.name.eq(condition) : null;
    }

    private BooleanExpression ageGoe(Integer condition) {
        return (condition != null) ? member.age.goe(condition) : null;
    }

    private BooleanExpression ageLoe(Integer condition) {
      return (condition != null) ? member.age.loe(condition) : null;
    }

    private BooleanExpression usernameLike(String condition) {
        return (hasText(condition)) ? member.username.like("%" + condition + "%") : null;
    }
}

