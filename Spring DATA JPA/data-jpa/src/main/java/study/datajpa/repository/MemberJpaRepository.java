package study.datajpa.repository;

import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import study.datajpa.entity.Member;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class MemberJpaRepository {

    private final EntityManager em;

    public Member save(Member member){
        em.persist(member);
        return member;
    }
    public List<Member> findAll(){
        return em.createQuery("select m from Member m", Member.class)
                .getResultList();
    }
    public Optional<Member> findById(Long id){
        return Optional.ofNullable(em.find(Member.class, id));
    }
    public long count(){
        return em.createQuery("select count(m) from Member m", Long.class).getSingleResult();
    }

    public void delete(Member member){
        em.remove(member);
    }

    public List<Member> findByName(String name){
        return em.createNamedQuery("Member.findByName", Member.class)
                .setParameter("name", name)
                .getResultList();
    }

    public List<Member> findByPage(int age, int offset, int limit){
        return em.createQuery("select m from Member m where m.age = :age order by m.name desc ", Member.class)
                .setParameter("age", age)
                .setFirstResult(offset)
                .setMaxResults(limit)
                .getResultList();
    }

    public long totalCount(int age){
        return em.createQuery("select count(m) from Member m where m.age = :age", Long.class)
                .setParameter("age", age)
                .getSingleResult();
    }

    public int bulkAgePlus(int age){
        return em.createQuery("update Member m set m.age= m.age+1 where m.age >= :age ")
                .setParameter("age", age)
                .executeUpdate();

    }
}
