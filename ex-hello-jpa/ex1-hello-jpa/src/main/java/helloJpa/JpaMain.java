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
            for(Long i = 1l; i <= 60; i++){
                Member member = new Member();
                member.setUsername("A"+i);
                em.persist(member);
            }
            System.out.println("========================");
            Member member1 = em.find(Member.class, 1);
            Member member2 = em.find(Member.class, 2);
            Member member3 = em.find(Member.class, 3);
            Member member4 = em.find(Member.class, 4);
            Member member5 = em.find(Member.class, 5);
            System.out.println("member1 = " + member1.getId());
            System.out.println("member2 = " + member2.getId());
            System.out.println("member3 = " + member3.getId());
            System.out.println("member4 = " + member4.getId());
            System.out.println("member5 = " + member5.getId());
            System.out.println("========================");

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
