package jpabook.jpashop.controller;

import jakarta.validation.Valid;
import jpabook.jpashop.entity.Member;
import jpabook.jpashop.request.MemberForm;
import jpabook.jpashop.service.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/members")
public class MemberController {

    private final MemberService memberService;


    @GetMapping
    public String getMembers(Model model){
        model.addAttribute("members", memberService.findMembers());
        return "members/memberList";
    }

    @GetMapping("/new")
    public String setMembers(Model model){
        model.addAttribute("memberForm", MemberForm.builder().build());
        return "members/createMemberForm";
    }
    @PostMapping(value = "/new")
    public String createMember(@Valid MemberForm memberForm, BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            return "members/createMemberForm";
        }
        memberService.join(memberForm);
        return "redirect:/";
    }
}
