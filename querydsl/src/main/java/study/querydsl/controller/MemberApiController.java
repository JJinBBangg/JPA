package study.querydsl.controller;

import lombok.Data;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import study.querydsl.response.MemberResponse;
import study.querydsl.response.MemberSearchCondition;
import study.querydsl.service.MemberService;

import java.util.List;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
@Slf4j
public class MemberApiController {

    private final MemberService memberService;

    @GetMapping("/members")
    public Result getMembers(MemberSearchCondition condition, @PageableDefault(page = 0, size = 20) Pageable pageable){
        return new Result(memberService.findMemberWithTeam(condition, pageable));
    }

    @Getter
    private static class Result<T>{
        private T data;
        public Result(T data) {
            this.data = data;
        }
    }
}
