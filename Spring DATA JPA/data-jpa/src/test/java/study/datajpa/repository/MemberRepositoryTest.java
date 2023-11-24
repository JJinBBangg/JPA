package study.datajpa.repository;

import jakarta.persistence.EntityManager;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;
import study.datajpa.entity.Member;
import study.datajpa.entity.Team;
import study.datajpa.exception.NotFoundMember;
import study.datajpa.request.UpdateMember;

import java.util.List;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;


@SpringBootTest
@Transactional
@Rollback(false)
class MemberRepositoryTest {

    @Autowired
    EntityManager em;
    @Autowired
    MemberRepository memberRepository;

    @BeforeEach
    void beForeEachMethod() {
        memberRepository.deleteAll();
    }

    @Test
    @DisplayName("회원가입")
    void test1() {
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

    @Test
    @DisplayName("회원검색")
    void test2() throws Exception {

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
        Member findMember1 = memberRepository.findById(member1.getId()).orElseThrow(NotFoundMember::new);
        Member findMember2 = memberRepository.findById(member2.getId()).orElseThrow(NotFoundMember::new);
        assertThat(findMember1.getName()).isEqualTo(member1.getName());
        assertThat(findMember2.getName()).isEqualTo(member2.getName());

        Pageable page = PageRequest.of(0, 3, Sort.by(Sort.Direction.DESC, "name"));
        //List 조회 검증
        List<Member> members = memberRepository.findAll();
        assertThat(members.size()).isEqualTo(2);

        // count 검증
        long count = memberRepository.count();
        assertThat(count).isEqualTo(2L);

        //삭제 검증
        memberRepository.delete(findMember1);
        long count1 = memberRepository.count();
        assertThat(count1).isEqualTo(1L);

        List<Member> members1 = memberRepository.findAll();
        assertThat(members1.size()).isEqualTo(1);
        assertThat(members1.get(0)).isEqualTo(findMember2);
    }

    @Test
    @DisplayName("페이징 처리")
    void test3() throws Exception {
        //given
        Member member1 = Member.builder().name("member1").age(10).build();
        Member member2 = Member.builder().name("member2").age(10).build();
        Member member3 = Member.builder().name("member3").age(10).build();
        Member member4 = Member.builder().name("member4").age(10).build();
        Member member5 = Member.builder().name("member5").age(10).build();
        em.persist(member1);
        em.persist(member2);
        em.persist(member3);
        em.persist(member4);
        em.persist(member5);
        em.flush();
        em.clear();

        //when
        int pageNumber = 0;
        int pageSize = 3;
        int age = 10;
        PageRequest pageRequest = PageRequest.of(pageNumber, pageSize, Sort.by(Sort.Direction.DESC, "name"));
        Page<Member> page = memberRepository.findByPage1(age, pageRequest);
        PageRequest.of(pageNumber, pageSize, Sort.Direction.DESC, "name", "age");
        List<Member> collect = page.stream().map(member -> member).collect(Collectors.toList()); // 원래의 객체 꺼내줌
        //then
        List<Member> members = page.getContent();
        for (Member member : members) {
            System.out.println("member.getName() = " + member.getName());
        }
        page.getTotalElements();

        page.getTotalPages(); // 총 페이지 수
        page.getNumber(); // 현재 페이지

        page.isFirst(); // 첫페이지인가

        if (page.hasNext()) // 다음페이지가 있는가?
            pageRequest.next(); // 다음페이지로 페이징 정보변경

        if (page.hasPrevious()) // 전 페이지가 있는가?
            pageRequest.previous();// 이전페이지로 페이징 정보변경

        page.isLast();  // 마지막페이지인가


    }

    @Test
    @DisplayName("벌크 수정")
    void test4(){
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

        int resultCount = memberRepository.bulkAgePlus(20);

        assertThat(resultCount).isEqualTo(3);
    }

    @Test
    @DisplayName("1+N 문제해결")
    void test5(){
        //given
        Team teamA = Team.builder()
                .name("TeamA")
                .build();

        Team teamB = Team.builder()
                .name("TeamB")
                .build();

        em.persist(teamA);
        em.persist(teamB);

        Member member1 = Member.builder()
                .name("member1")
                .age(10)
                .team(teamA)
                .build();

        Member member2 = Member.builder()
                .name("member2")
                .age(20)
                .team(teamB)
                .build();

        em.persist(member1);
        em.persist(member2);

        em.flush();
        em.clear();

        //when
        List<Member> members = memberRepository.findAll();
        
        //then
        for (Member member : members) {
            System.out.println("member.getName() = " + member.getName());
            System.out.println("member.getTeam().getName() = " + member.getTeam().getName());
        }
        em.clear();

        List<Member> findMembers = memberRepository.findMemberFetchJoinAll();
        for (Member member : findMembers) {
            System.out.println("member.getName() = " + member.getName());
            System.out.println("member.getTeam().getName() = " + member.getTeam().getName());
        }
        em.clear();

        List<Member> findMember = memberRepository.findAll();
        for (Member member : findMember) {
            System.out.println("member.getName() = " + member.getName());
            System.out.println("member.getTeam().getName() = " + member.getTeam().getName());
        }
    }

    @Test
    @DisplayName("JPA Query Hint")
    void test6(){
        //given
        Member member = Member.builder().name("member1").age(33).build();
        memberRepository.save(member);
        em.flush();
        em.clear();

        //when
        // findMember 를 가지고오는 순간부터 변경이 되었을 시 변경감지 하기위한 기준객체를 미리 만들어놓음
        Member findMember = memberRepository.findById(member.getId()).orElseThrow(NotFoundMember::new);
        UpdateMember updateMember = UpdateMember.builder().name("memberA").build();
        findMember.updateMember(updateMember);
        em.flush();// 변경감지(Dirty Checking)

    }

    @Test
    @DisplayName("RepositoryCustom")
    void test7(){
        List<Member> members = memberRepository.findMemberCustom();
    }
}