package study.querydsl.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import study.querydsl.response.MemberResponse;
import study.querydsl.response.MemberSearchCondition;
import study.querydsl.response.MemberTeamResponse;

import java.util.List;

public interface MemberRepositoryCustom {
    Page<MemberResponse> findMembers(Pageable pageable);
    Page<MemberTeamResponse> findMemberWithTeam(MemberSearchCondition condition, Pageable pageable);
    Page<MemberTeamResponse> findMemberWithTeamV2(MemberSearchCondition condition, Pageable pageable);
}
