package study.datajpa.repository;

import jakarta.persistence.QueryHint;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import study.datajpa.entity.Member;
import study.datajpa.response.MemberResponse;

import java.util.List;

public interface MemberRepository extends JpaRepository<Member, Long> {

    // 사용 시 각 Entity 의 필드명과 동일한 변수값을 지정해야함 
    // username 이라고 지정할 시 member entity 의 필드값이 없다고 오류발생
    // 사용 중 필드명이 수정될 시 어플리케이션 실행 시점에 오류발생ㄷ
    List<Member> findByNameAndAgeGreaterThan(String name, int age);

    // 각 쿼리 메소드의 from 값은 JpaRepository<Member, Long> : Member Entity 객체 참조
    // 조회 : find...By, query...By, get...By, read...By
    // Count : count...By // return long
    // Exists : exists...By // return boolean
    // 삭제 : delete...By // return long
    // DISTINCT : find...DistinctBy
    // LIMIT : findFirst(#), findTop(#)
    
    @Query(name = "Member.findByName") 
    // 주석 처리하여도 메서드 명을 기준으로 namedQuery 를 우선으로 찾고
    // 찾는 기준 jpaRepository<T, ID> T 자리의 Member entity 내부의
    // Member.findByName 이름의 namedQuery 검색
    // 이후에 쿼리 메소드로 작동한다.
    List<Member> findByName(@Param("name") String name);

    @Query("select m from Member m where m.name = :name and m.age > :age")
    List<Member> findAllBy(@Param("name")String name, @Param("age")int age);

    @Query("select m.name from Member m ")
    String findUsernameList();

    @Query("select new study.datajpa.response.MemberResponse( " +
            " m.name, " +
            " m.age ) " +
            " from Member m ")
    List<MemberResponse> findMemberDto();

    @Query("select m from Member m where m.name in :names")
    List<Member> findByNames(@Param("names") List<String> names);

    @Query("select m from Member m where m.age = :age")
    Page<Member> findByPage1(@Param("age") int age, Pageable pageable);

    //검색해야하는 쿼리의 숫자보다 1개 더 가져와서 다음이있는지 확인하고 슬라이스, 더보기 스타일로 컨텐츠 제공
    @Query("select m from Member m where m.age = :age")
    Slice<Member> findByPage2(@Param("age") int age, Pageable pageable);

    // 쿼리가 복잡해지면 카운트 쿼리도 복잡해짐
    @Query(value = "select m from Member m left join m.team t where m.age = :age",
    //카운트 쿼리 분리(성능 향상)
            countQuery = "select count(m) from Member m where m.age =:age")
    Page<Member> findByPage3(@Param("age") int age, Pageable pageable);

    //수정쿼리에서는 Modifying 붙여줘야함
    // em.executeQuery()
    // 벌크 연산을 하게되면 db 에만 반영되고 영속성 컨텍스트에는 반영되지않음
    // 그래서 clear 를 자동으로 하게만들어서 이후 조회하는 정보는 모두 db 에서 가지고 오게 만듦
    @Modifying(clearAutomatically = true)
    @Query("update Member m set m.age = m.age+1 where m.age >= :age")
    int bulkAgePlus(@Param("age")int age);

    @Query("select m from Member m "/*" +
            "join fetch m.team t"*/)
    @EntityGraph(attributePaths = "team")
    List<Member> findMemberFetchJoinAll();

    @Override
    @EntityGraph(attributePaths = {"team"})
    List<Member> findAll();

    @EntityGraph(attributePaths = "team")
    List<Member> findFetchJoinByName(@Param("name") String name);

    @QueryHints(@QueryHint(name ="org.hibernate.readOnly", value ="true"))
    Member findQueryHintById(@Param("id") Long id);
}
