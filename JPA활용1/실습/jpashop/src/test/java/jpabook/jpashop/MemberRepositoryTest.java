package jpabook.jpashop;

import jpabook.jpashop.domain.Member;
import jpabook.jpashop.repository.MemberRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;



@SpringBootTest
class MemberRepositoryTest
{
    @Autowired
    MemberRepository memberRepository;

    @Test
    @DisplayName("JPA H2 연결 test")
    @Transactional
    @Rollback(value = false)
    void test1 () throws Exception{
        //given
        Member newMember = Member.builder()
                .name("newMember")
                .build();
        Long saveMemberId = memberRepository.save(newMember);
        //when
        Member member = memberRepository.find(saveMemberId);
        //then
        Assertions.assertThat(member.getId()).isEqualTo(newMember.getId());
        Assertions.assertThat(member.getName()).isEqualTo(newMember.getName());
    }
}