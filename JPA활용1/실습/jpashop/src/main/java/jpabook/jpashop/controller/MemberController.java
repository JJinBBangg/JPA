package jpabook.jpashop.controller;

import jpabook.jpashop.entity.Member;
import jpabook.jpashop.request.CreateMember;
import jpabook.jpashop.service.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@Slf4j
public class MemberController {

    private final MemberService memberService;

    @GetMapping("/members/{}")
    public Member get(@PathVariable Long memberId){
        return memberService.findOne(memberId);
    }

    @GetMapping("/members")
    public List<Member> getMembers(){
        return memberService.findMembers();
    }
    @PostMapping("/members")
    public Long setMembers(@RequestBody CreateMember createMember){
        log.info(">>>>>>>>>>>>>>>>>>>{}",createMember);
        return memberService.join(createMember);
    }
}
