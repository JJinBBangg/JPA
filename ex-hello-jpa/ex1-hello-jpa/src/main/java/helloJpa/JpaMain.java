package helloJpa;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Persistence;

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
//            Member member2 = em.find(Member.class, 2);
//            Member member3 = em.find(Member.class, 3);
//            Member member4 = em.find(Member.class, 4);
//            Member member5 = em.find(Member.class, 5);
//            System.out.println("member1 = " + member1.getId());
//            System.out.println("member2 = " + member2.getId());
//            System.out.println("member3 = " + member3.getId());
//            System.out.println("member4 = " + member4.getId());
//            System.out.println("member5 = " + member5.getId());
//            System.out.println("========================");

            // 로직
            // 비영속
//            Member member1 = new Member(101L, "jin1");
//            Member member2 = new Member(102L, "jin2");

            // 영속
//            em.persist(member1); //단일 컨텍스트 저장
//            em.persist(member2);

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
            Team team = new Team();
            team.setName("TeamA");
            em.persist(team);

            Member member = new Member();
            member.changeTeam(team);
            member.setUsername("memberA");
            em.persist(member);


//            team.getMembers().add(member);
//            member.set 하지 않고 실행 시 member 테이블의 TEAM_ID 값 null
//            읽기전용이라 member.setTeam()을 한 값만 들어가지 여기서는 입력되지않음
//            flush를 하지 않는 상태에서 로직 상 값을 이용하려면 1차 캐시에 저장된 값을 이용해야하는데
//            이런경우 team.getMembers().add(member); 양쪽에서 입력한 후에 사용해야함
//            이유는 1차캐시에 올라가는 모습은 순수한 java객체로 team의 List<Member> 값은 없다고 보면됨
//            em.flush();
//            em.clear();

            Team findTeam = em.find(Team.class, team.getId());
            List<Member> members = findTeam.getMembers();
            for (Member m : members) {
                System.out.println("m.getUsername() = " + m.getUsername());
            }


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

            // 커밋
            System.out.println("commit");
            tx.commit();

        } catch (Exception e) {
            tx.rollback();
        } finally {
            // connection 닫기
            em.close();
        }
        enf.close();
    }
}
