package study.querydsl.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import study.querydsl.repository.MemberRepository;
import study.querydsl.response.MemberResponse;
import study.querydsl.response.MemberSearchCondition;
import study.querydsl.response.MemberTeamResponse;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class MemberService {

    private final MemberRepository memberRepository;

    @Transactional(readOnly = true)
    public Page<MemberResponse> findMembers(Pageable pageable){
        return memberRepository.findMembers(pageable);
    }

    @Transactional(readOnly = true)
    public Page<MemberTeamResponse> findMemberWithTeam(MemberSearchCondition condition, Pageable pageable){
        return memberRepository.findMemberWithTeam(condition, pageable);
    }
}
