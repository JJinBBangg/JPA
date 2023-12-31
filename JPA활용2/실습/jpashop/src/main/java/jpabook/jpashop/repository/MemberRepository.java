package jpabook.jpashop.repository;

import jakarta.persistence.EntityManager;
import jpabook.jpashop.entity.Member;
import jpabook.jpashop.entity.Order;
import lombok.RequiredArgsConstructor;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
@RequiredArgsConstructor
public class MemberRepository{



    private final EntityManager em;

    public Long save(Member member){
        em.persist(member);
        return member.getId();
    }

    public Member findOne(Long memberId){
        return em.find(Member.class, memberId);

    }
    public Long findDuplicateName(String name){
        Long result = em
                .createQuery("select count(m) from Member m where m.name = :name", Long.class)
                .setParameter("name", name)
                .getSingleResult();
        return result;
    }

    public List<Member> findAll(){
        return em.createQuery("select m from Member m",Member.class).getResultList();
    }

    public Member findByName(String name){
        return em.createQuery("select m from Member m where m.name = name", Member.class)
                .setParameter("name", name)
                .getSingleResult();
    }
    public Member findByNameWithOrder(String name){
        return em.createQuery("select m from Member m join fetch m.orders where m.name = :name", Member.class)
                .setParameter("name", name)
                .getSingleResult();
    }


    public void deleteAll(){
        em.createQuery("delete from Member").executeUpdate();
    }
}
