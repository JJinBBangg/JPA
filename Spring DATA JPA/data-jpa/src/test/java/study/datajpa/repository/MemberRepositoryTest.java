package study.datajpa.repository;

import jakarta.persistence.EntityManager;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;
import study.datajpa.entity.Member;
import study.datajpa.exception.NotFoundMember;

import java.util.Optional;


@SpringBootTest
@Transactional
@Rollback(false)
class MemberRepositoryTest {

    @Autowired EntityManager em;
    @Autowired MemberRepository memberRepository;

    @BeforeEach
    void beForeEachMethod(){
        memberRepository.deleteAll();
    }
    @Test
    @DisplayName("회원가입")
    void test1(){
        //given
        Member member = Member.builder()
                .name("memberA")
                .build();
        Member savedMember = memberRepository.save(member);

        //when
        Member findMember = memberRepository.findById(savedMember.getId()).orElseThrow(NotFoundMember::new);

        //then
        Assertions.assertThat(findMember.getName()).isEqualTo(member.getName());
        org.junit.jupiter.api.Assertions.assertEquals(findMember.getName(), member.getName());
        
    }

}