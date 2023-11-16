package jpabook.jpashop.service;

import jpabook.jpashop.entity.Address;
import jpabook.jpashop.entity.Member;
import jpabook.jpashop.exception.DuplicateMember;
import jpabook.jpashop.repository.MemberRepository;
import jpabook.jpashop.request.CreateMember;
import jpabook.jpashop.request.UpdateMember;
import jpabook.jpashop.response.MemberResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;

    //조회만 담당하는 transaction에서는 readOnly 옵션을 true로 하면 성능최적화
    //대부분 조회인 경우 class level에서 @Transactional(readOnly = true)
    //입력이 필요한경우에만 @Transactional 붙여서 사용
    
    //회원 가입
    public Long join(CreateMember member){
        //name 중복 검증
        validateDuplicateMember(member);
        Member createMember = Member.builder()
                .name(member.getName())
                .address(Address.builder()
                        .city(member.getCity())
                        .street(member.getStreet())
                        .zipcode(member.getZipcode())
                        .build())
                .build();
        return memberRepository.save(createMember);
    }

    //전체회원조회
    @Transactional(readOnly = true)
    public List<MemberResponse> findMembers(){
        List<Member> findMembers = memberRepository.findAll();
        List<MemberResponse> members = findMembers.stream()
                .map(member -> MemberResponse.builder()
                        .id(member.getId())
                        .name(member.getName())
                        .build())
                .collect(Collectors.toList());

        return members;
    }

    @Transactional(readOnly = true)
    public Member findOne(Long id){
        return memberRepository.findOne(id);
    }

    public void updateMember(Long memberId, UpdateMember updateMember){
        if(memberRepository.findDuplicateName(updateMember.getName()) >0) throw new DuplicateMember();
        Member findMember = memberRepository.findOne(memberId);
        findMember.updateMember(updateMember);
    }
    // 아래처럼 사용시 was가 여러개 올라와있는 경우 동시성문제가 발생할 수 있음
    // 그렇기 때문에 회원가입처럼 중요한 로직에서는 db에서 한번더 체크가 가능하여
    // 동일한 name으로 저장되지 않도록 unique 제약조건을 추가하는것이 올바름
        // 최적화하기위해서는 아래는 모든 맴버를  name 과 비교해서 list를 반환하기 때문에
        // memberRepository 에서 애초에 select count(m) from Member로 최적화 하는것이 좋음
    private void validateDuplicateMember(CreateMember member) {
        if(memberRepository.findDuplicateName(member.getName()) >0) throw new DuplicateMember();
    }

}
