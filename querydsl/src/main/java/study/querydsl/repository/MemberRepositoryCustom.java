package study.querydsl.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import study.querydsl.response.MemberResponse;
import study.querydsl.response.MemberSearchCondition;
import study.querydsl.response.MemberTeamResponse;

import java.util.List;

public interface MemberRepositoryCustom {
    List<MemberResponse> findMembers();
    List<MemberTeamResponse> findMemberWithTeam(MemberSearchCondition condition);
}
