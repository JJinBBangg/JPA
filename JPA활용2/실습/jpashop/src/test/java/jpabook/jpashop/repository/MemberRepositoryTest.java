package jpabook.jpashop.repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jpabook.jpashop.entity.Member;
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
    private MemberRepository memberRepository;

    @PersistenceContext
    private EntityManager em;

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
        Member member = memberRepository.findOne(saveMemberId);
        //then
        Assertions.assertThat(member.getId()).isEqualTo(newMember.getId());
        Assertions.assertThat(member.getName()).isEqualTo(newMember.getName());
    }


}