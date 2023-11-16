package jpabook.jpashop.api;

import jakarta.validation.Valid;
import jpabook.jpashop.entity.Member;
import jpabook.jpashop.request.CreateMember;
import jpabook.jpashop.request.UpdateMember;
import jpabook.jpashop.response.MemberResponse;
import jpabook.jpashop.service.MemberService;
import lombok.*;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class MemberApiController {


    private final MemberService memberService;

    @GetMapping("/members/{}")
    public MemberResponse get(@PathVariable Long memberId) {
        Member findMember = memberService.findOne(memberId);
        return MemberResponse.builder()
                .id(findMember.getId())
                .name(findMember.getName())
                .build();
    }

    @GetMapping("/members")
    public Result getMembers() {
        return new Result(memberService.findMembers());
    }

    @PostMapping("/members")
    public MemberResponse set(@Valid @RequestBody CreateMember createMember){
        Long createdMemberId = memberService.join(createMember);
        return MemberResponse.builder()
                .id(createdMemberId)
                .build();
    }

    @PutMapping("/members/{id}")
    public void update(@PathVariable Long id, @RequestBody @Valid UpdateMember updateMember){
        memberService.updateMember(id, updateMember);
    }


    @AllArgsConstructor
    @Getter
    private static class Result<T>{
        private T data;
    }
}

//    @Getter
//    private static class Result<T>{
//        private T data;
//        private int count;
//
//        @Builder
//        public Result(count, T data) {
//            this.count = count;
//            this.data = data;
//        }
//    }
//    단건을 응답하는 api 인 경우 객체의 이름이 바로 api 스펙으로 노출 되어도 되지만
//    api 응답할 때 List인 경우 위처럼 감싸고난 후 내보내면 data 라는 객체 내부에 List내용이 배열로 나가기때문에
//    api 스팩 상 추가적인 내용을 json 객체로 전달 하는데 유연하게 대처할 수 있음
// List 를 바로 응답할 경우 Array 로 json 을 반환하게됨
//    {[
//        {
//              name : "member1"
//        },
//        {
//              name : "member2"
//        },
//    ]}
// vuejs 에서는 자동으로 data 내부에 List 객체 이름을 확인하여 넣어주기는
// 다른 서버나 framework 에서 받을 때는 아래와같이 유연하게 대처가능하도록 data 로 감싼 후 응답 시 훨씬 유연함
// Data로 감싼 후 응답할 시 List<Member> 가 Array 아니라 객체 내부의 배열로 인식되어
// count 처럼 여러 값을 추가로 넣는다고 해도 api 스펙상 확장이지 변경된 것이 아니게 됨
//    {
//          "count" : 2,  //count 를 추가하는 등 기존 api 스팩에 관여하지 않고 추가로 객체전달 가능
//          "data" :[
//              {
//                   name : "member1"
//              },
//                   name : "member2"
//              {
//          ]
//    }
//
