package jpabook.jpashop;

import jakarta.persistence.*;
import jpabook.jpashop.domain.*;

import java.util.List;

public class jpaMain {
    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");

        EntityManager em = emf.createEntityManager();

        EntityTransaction tx = em.getTransaction();

        tx.begin();

        try {
//            System.out.println("===================");
//            Team team = new Team();
//            team.setName("teamA");
//            em.persist(team);
//
//            Team teamB = new Team();
//            team.setName("teamB");
//            em.persist(teamB);
//
//            Member member = new Member();
//            member.setName("member1");
//            member.setTeam(team);
//            em.persist(member);
//
//            Member member2 = new Member();
//            member2.setName("member2");
//            member2.setTeam(teamB);
//            em.persist(member2);
//
//            em.flush();
//            em.clear();
//
//            System.out.println("===================");
//            Member findMember = em.find(Member.class, member2.getId());
//            System.out.println("findMember.getTeam().getClass() = " + findMember.getTeam().getClass());
//            // team의 정보조회 없이 team.getClass(); 조회시 Proxy 타입으로 조회됨
////            System.out.println("findMember.getTeam().getName() = " + findMember.getTeam().getName());
//            // 위 team을 불러오게되면 team을 join해서 조회
//
//            //EAGER설정 시 즉시조회
////            Member findMember2 = em.find(Member.class, member2.getId());
////            List<Member> resultMembers = em.createQuery("select m from Member m", Member.class).getResultList();
//            List<Member> resultMembers = em.createQuery("select m from Member m join fetch m.team", Member.class).getResultList();
//            // 위처럼 JPQL 를 사용하는 경우 member만 조회 하는 쿼리를 만들어서 조회했지만 EAGER설정으로
//            // team조회하는 쿼리가 한번더 나가게됨
//            // 위같은 N+1 문제를 해결하기 위해서 LAZY설정을 권장(실무에서 반드시)
//            // 경우에 따라 member정보와 team정보를 같이 가져오고 싶은경우 fetch join 이라는 기능 사용

            Child child1 = new Child();
            Child child2 = new Child();

            Parent parent = new Parent();
            parent.addChild(child1);
            parent.addChild(child2);

            em.persist(parent); //cascade 의 영향받음(ALL, PERSIST)
//            em.persist(child1);
//            em.persist(child2);

            em.flush();
            em.clear();

            Parent findParent = em.find(Parent.class, parent.getId());
            findParent.getChildList().remove(0);
            // 컬렉션에서 삭제했을 뿐인데 영속컨텍스트에 삭제함(orphanRemoval =true) 설정

            tx.commit();
        } catch (Exception e) {
            e.printStackTrace();
            tx.rollback();
        } finally {
            em.close();
        }
        emf.close();

    }
}
