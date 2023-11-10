import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Persistence;
import jpql.Member;
import jpql.Team;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class jpaMain {
    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        tx.begin();
        try{

            Team team = new Team();
            team.setName("teamA");
            em.persist(team);


            Team team1 = em.find(Team.class, 1L);
            System.out.println("team1.getName() = " + team1.getName());
            List<Member> memberList = IntStream.range(1, 31)
                    .mapToObj((i) -> new Member("member" + i, 100 + i))
                    .collect(Collectors.toList());
            for (Member member1 : memberList) {
                member1.setTeam(team);
                em.persist(member1);
            }

            em.flush();
            em.clear();

            String jpql1 = "select m from Member m join m.team t";
            String jpql2 = "select m from Member m left join m.team t on m.id = t.id and t.name = :teamName order by m.id desc ";
            String jpql3 = "select m from Member m join Team t on m.name = t.name";
            List<Member> resultList = em.createQuery(jpql3, Member.class)
//                    .setParameter("teamName", "teamA")
                    .setFirstResult(0)
                    .setMaxResults(10)
                    .getResultList();
            for (Member member : resultList) {
                System.out.println("member.getName() = " + member.getName());
                
            }
            System.out.println("=============================");
            List<Member> members = em.createQuery("select m from Member m order by m.id desc ", Member.class)
                    .setFirstResult(0)
                    .setMaxResults(10)
                    .getResultList();
            for (Member m : members) {
                System.out.println("m.getName() = " + m.getName());
            }


            System.out.println("commit");
            tx.commit();
        }catch (Exception e){
            e.printStackTrace();
            tx.rollback();
        } finally {
            em.close();
        }
        emf.close();

    }
}
