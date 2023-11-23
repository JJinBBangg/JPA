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

import java.util.List;

import static org.assertj.core.api.Assertions.*;


@SpringBootTest
@Transactional
@Rollback(false)
class MemberJpaRepositoryTest {

    @Autowired EntityManager em;
    @Autowired MemberJpaRepository memberJpaRepository;
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
        Member savedMember = memberJpaRepository.save(member);

        //when
        Member findMember = em.find(Member.class, savedMember.getId());

        //then
        assertThat(findMember.getName()).isEqualTo(member.getName());
        org.junit.jupiter.api.Assertions.assertEquals(findMember.getName(), member.getName());

    }

    @Test
    @DisplayName("회원검색")
    void test2() throws Exception{

        Member member1 = Member.builder()
                .name("member1")
                .age(10)
                .build();

        Member member2 = Member.builder()
                .name("member2")
                .age(10)
                .build();
        em.persist(member1);
        em.persist(member2);

        em.flush();
        em.clear();

        //단건조회 검증
        Member findMember1 = memberJpaRepository.findById(member1.getId()).orElseThrow(NotFoundMember::new);
        Member findMember2 = memberJpaRepository.findById(member2.getId()).orElseThrow(NotFoundMember::new);
        assertThat(findMember1.getName()).isEqualTo(member1.getName());
        assertThat(findMember2.getName()).isEqualTo(member2.getName());

        //List 조회 검증
        List<Member> members = memberJpaRepository.findAll();
        assertThat(members.size()).isEqualTo(2);

        // count 검증
        long count = memberJpaRepository.count();
        assertThat(count).isEqualTo(2L);

        //삭제 검증
        memberJpaRepository.delete(findMember1);
        long count1 = memberJpaRepository.count();
        assertThat(count1).isEqualTo(1L);

        List<Member> members1 = memberJpaRepository.findAll();
        assertThat(members1.size()).isEqualTo(1);
        assertThat(members1.get(0)).isEqualTo(findMember2);
    }

    @Test
    @DisplayName("벌크 수정")
    void test3(){
        Member member1 = Member.builder()
                .name("member1")
                .age(10)
                .build();

        Member member2 = Member.builder()
                .name("member2")
                .age(20)
                .build();
        Member member3 = Member.builder()
                .name("member3")
                .age(30)
                .build();
        Member member4 = Member.builder()
                .name("member4")
                .age(40)
                .build();
        em.persist(member1);
        em.persist(member2);
        em.persist(member3);
        em.persist(member4);
        em.flush();
        em.clear();

        int resultCount = memberJpaRepository.bulkAgePlus(20);

        assertThat(resultCount).isEqualTo(3);
    }
}