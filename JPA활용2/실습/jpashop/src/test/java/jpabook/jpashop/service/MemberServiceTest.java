package jpabook.jpashop.service;

import jakarta.persistence.EntityManager;
import jpabook.jpashop.entity.Member;
import jpabook.jpashop.exception.DuplicateMember;
import jpabook.jpashop.repository.MemberRepository;
import jpabook.jpashop.request.MemberForm;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;


@SpringBootTest
@Transactional
class MemberServiceTest {

    @Autowired
    MemberService memberService;
    @Autowired
    MemberRepository memberRepository;
    @Autowired
    EntityManager em;
    @BeforeEach
    void method2() {
        memberRepository.deleteAll();
    }

    @Test
    @DisplayName("회원 가입")
    @Rollback(value = false)
    void test() {
        //given
        MemberForm memberForm = MemberForm.builder()
                .name("member1")
                .city("진주")
                .street("사들로")
                .zipcode("157")
                .build();

        //when
        Long savedMemberId = memberService.join(memberForm);
        Member findMember = memberService.findOne(savedMemberId);

        //then
        assertThat(findMember.getId()).isEqualTo(savedMemberId);
        assertThat(findMember.getAddress().getCity()).isEqualTo("진주");


    }

    @Test
    @DisplayName("기존회원이 있을 때 중복되지않는 name 값으로 가입 가능")
    @Rollback(value = false)
    void test2() throws Exception {
        //given
        MemberForm member1 = MemberForm.builder()
                .name("member1")
                .city("진주")
                .street("사들로")
                .zipcode("157")
                .build();
        Long saveMemberId = memberService.join(member1);

        //when
        //중복되지 않는 member name으로 가입 시 정상적으로 저장되는지 검증
        MemberForm member2 = MemberForm.builder()
                .name("member12")
                .city("서울")
                .street("사들로")
                .zipcode("157")
                .build();
        Long createdMemberId = memberService.join(member2);

        //then
        Long findMemberCount = memberRepository.findDuplicateName("member12");
        Member findMember = memberRepository.findOne(createdMemberId);
        assertThat(findMemberCount).isEqualTo(1L);
        assertThat(findMember.getName()).isEqualTo("member12");
        assertThat(findMember.getAddress().getCity()).isEqualTo("서울");
    }

    @Test
    @DisplayName("중복 MEBER 검증에 잘못된 값을 입력하면 오류발생")
    void test3() throws Exception {
        //given
        MemberForm member1 = MemberForm.builder()
                .name("member1")
                .city("진주")
                .street("사들로")
                .zipcode("157")
                .build();
        memberService.join(member1);

        //when
        //중복되는 name 값으로 가입 시 오류발생(IllegalStateException)
        MemberForm member2 = MemberForm.builder()
                .name("member1")
                .city("서울")
                .street("사들로")
                .zipcode("157")
                .build();
        //then
        // 예외 발생 시 유의할 점 @Rollback(false) 설정 시 해당 exception 발생 시 오류발생할 수 있음
        DuplicateMember throwException = assertThrows(DuplicateMember.class, () -> {
            memberService.join(member2);
        });
        assertEquals(409, throwException.getStatusCode());
        assertEquals("중복된 이름입니다.", throwException.getMessage());

        // 위를 통과하여 진행되어 사용하면안됨
//        fail("예외가 발생해야합니다.");
    }
}