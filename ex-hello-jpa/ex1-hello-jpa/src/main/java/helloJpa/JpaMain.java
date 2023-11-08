package helloJpa;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Persistence;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import org.hibernate.Hibernate;

import java.util.ArrayList;
import java.util.List;

public class JpaMain {
    public static void main(String[] args) {
        EntityManagerFactory enf = Persistence.createEntityManagerFactory("hello");
        EntityManager em = enf.createEntityManager();
        //code
        //connection 얻기
        EntityTransaction tx = em.getTransaction();
        tx.begin();
        try {
//            for(Long i = 1l; i <= 60; i++){
//                Member member = new Member();
//                member.setUsername("A"+i);
//                em.persist(member);
//            }
//            System.out.println("========================");
//            Member member1 = em.find(Member.class, 1);
//            Member member3 = em.find(Member.class, 2);
//            Member member3 = em.find(Member.class, 3);
//            Member member4 = em.find(Member.class, 4);
//            Member member5 = em.find(Member.class, 5);
//            System.out.println("member1 = " + member1.getId());
//            System.out.println("member3 = " + member3.getId());
//            System.out.println("member3 = " + member3.getId());
//            System.out.println("member4 = " + member4.getId());
//            System.out.println("member5 = " + member5.getId());
//            System.out.println("========================");

            // 로직
            // 비영속
//            Member member1 = new Member(101L, "jin1");
//            Member member3 = new Member(102L, "jin2");

            // 영속
//            em.persist(member1); //단일 컨텍스트 저장
//            em.persist(member3);

//            System.out.println("=========");
//
//            Member findMember = em.find(Member.class, 1L);
//            System.out.println("findMember = " + findMember.getName());
//            Member member = em.find(Member.class, 1L);
//            List<Member> memberList = em.createQuery("select m from Member as m", Member.class)
//                    .setFirstResult(0) // 1번부터
//                    .setMaxResults(10) // 10번까지 가져와
//                    .getResultList();
//            for (Member member : memberList) {
//                System.out.println("member.getName() = " + member.getName());
//            }
//            member.setName("jjinbbang"); // 자동으로 저장됨
//            em.persist(member); // 쿼리문 작성
//            Team team = new Team();
//            team.setName("TeamA");
//            em.persist(team);



//            team.getMembers().add(member);
//            member.set 하지 않고 실행 시 member 테이블의 TEAM_ID 값 null
//            읽기전용이라 member.setTeam()을 한 값만 들어가지 여기서는 입력되지않음
//            flush를 하지 않는 상태에서 로직 상 값을 이용하려면 1차 캐시에 저장된 값을 이용해야하는데
//            이런경우 team.getMembers().add(member); 양쪽에서 입력한 후에 사용해야함
//            이유는 1차캐시에 올라가는 모습은 순수한 java객체로 team의 List<Member> 값은 없다고 보면됨
//            em.flush();
//            em.clear();

//            Team findTeam = em.find(Team.class, team.getId());
//            List<Member> members = findTeam.getMembers();
//            for (Member m : members) {
//                System.out.println("m.getUsername() = " + m.getUsername());
//            }


//            Member findedMember = em.find(Member.class, member.getId());
//            Team findTeam = findedMember.getTeam();
//            System.out.println("findTeam.getName() = " + findTeam.getName());
//
//            List<Member> members = findedMember.getTeam().getMembers();
//            for (Member m : members) {
//                System.out.println("m = " + m.getUsername());
//            }


//            Team findTeam = em.find(Team.class, 100L); // 팀100 번이 있다고 가정
//            member.setTeam(findTeam);
// 팀을 객체로 바로 바인딩 commit 에서 FK값이 변경 반영됨

//            //프록시 실습
//            Member member = new Member();
//            member.setUsername("memberA");
//            em.persist(member);
//            em.flush();
//            em.clear();
//
//            Member findMember = em.find(Member.class, member.getId());
//            System.out.println("findMember.getClass() = " + findMember.getClass());
//            // 1차 캐시에 데이터 불러오고나면
//            Member refMember = em.getReference(Member.class, member.getId());
//            System.out.println("refMember.getClass() = " + refMember.getClass());
//            System.out.println("refMember.getId() = " + refMember.getId());
//            // 여기까지는 DB 조회 없이도 출력가능하여 쿼리 날리지 않음
//            System.out.println("refMember.getUsername() = " + refMember.getUsername());
//            // 1차 캐시에서 데이터가 있으면 proxy 의 target이 1차캐시에서 조회
//            Hibernate.initialize(refMember); // username 조회 하지 않고 사용
//            //사용할 때 쿼리를 날리게 됨

            // 위의 em.find() 와 em.reference() 순서가 바뀌더라도 1차캐시에 데이터가 있으면
            // find() 에서도 쿼리를 날리지 않고 1차캐시를 사용하게됨
            // 특이점은 ref 먼저 조회시 class타입이 proxy로 나오고 이후에 find조회하더라도
            // 두클래스의 타입 모두 proxy로 조회되고 반대의 경우에는 두클레스 모두 Member 타입임
            
//            System.out.println("findMember.getClass() = " + findMember.getClass());//
//            System.out.println("findMember.getUsername() = " + findMember.getUsername());//

            Team teamA = new Team();
            teamA.setName("teamA");

            Team teamB = new Team();
            teamB.setName("teamB");


            Member member1 = new Member();
            member1.setUsername("java hello");
            member1.setTeam(teamA);

            Member member2 = new Member();
            member2.setUsername("spring hello");
            member2.setTeam(teamB);

            Member member3 = new Member();
            member3.setUsername("javascript hello");
            member3.setTeam(teamA);

            em.persist(teamA);
            em.persist(teamB);
            em.persist(member1);
            em.persist(member2);
            em.persist(member3);

            em.flush();
            em.clear();

            System.out.println("==========================");

            String jpql1 = "select m from Member m where m.team.name like '%A%'";
            String jpql2 = "select m from Member m where m.username like '%hello%'";
            List<Member> members = em.createQuery(jpql2
                    , Member.class).getResultList();//대소문자구분함
            for (Member member : members) {
                System.out.println("member.getUsername() = " + member.getUsername());
                System.out.println("member.getTeam().getName(); = " + member.getTeam().getName());
            }
            // Criteria 사용
            CriteriaBuilder cb = em.getCriteriaBuilder();

            CriteriaQuery<Member> query = cb.createQuery(Member.class);

            Root<Member> m = query.from(Member.class);

            CriteriaQuery<Member> cq = query.select(m).where(cb.like(m.get("username"), "%hello%"));

            List<Member> memberList = em.createQuery(cq).getResultList();

            for (Member member : memberList) {
                System.out.println("member.getUsername() = " + member.getUsername());
                System.out.println("member.getTeam().getName(); = " + member.getTeam().getName());
            }

            // 커밋
            System.out.println("commit");
            tx.commit();

        } catch (Exception e) {
            e.printStackTrace();
            tx.rollback();
        } finally {
            // connection 닫기
            em.close();
        }
        enf.close();
    }
}
