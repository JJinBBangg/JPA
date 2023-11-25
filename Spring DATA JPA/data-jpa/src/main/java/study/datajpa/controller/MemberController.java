package study.datajpa.controller;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import study.datajpa.entity.Member;
import study.datajpa.exception.NotFoundMember;
import study.datajpa.repository.MemberRepository;
import study.datajpa.response.MemberResponse;

import java.util.stream.Collectors;
import java.util.stream.IntStream;

@RestController
@RequiredArgsConstructor
public class MemberController {

    private final MemberRepository memberRepository;

    @GetMapping("/member/{id}")
    public String getMember(@PathVariable("id") Long id) {
        Member member = memberRepository.findById(id).orElseThrow(NotFoundMember::new);
        return member.getName();
    }

    @GetMapping("/member1/{id}")
    public MemberResponse getMember1(@PathVariable("id") Member member) {
        return MemberResponse.builder()
                .name(member.getName())
                .build();
    }

    @GetMapping("/members")
    public Page<Member> getMembers(@PageableDefault(page = 0,size =10 ,sort="id",direction = Sort.Direction.DESC) Pageable pageable) {
        return memberRepository.findAll(pageable);
    }


    @PostConstruct
    public void init() {
        IntStream.range(1, 100).forEach(i -> {
                    Member member = Member.builder()
                            .name("member" + i)
                            .age(i)
                            .build();
                    memberRepository.save(member);
                });
    }
}
