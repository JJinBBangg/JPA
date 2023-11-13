package jpabook.jpashop.repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jpabook.jpashop.domain.Member;
import jpabook.jpashop.domain.item.Book;
import org.springframework.stereotype.Repository;


@Repository
public class MemberRepository{

    @PersistenceContext
    private EntityManager em;

    public Long save(Member member){
        em.persist(member);
        return member.getId();
    }

    public Member find(Long memberId){
        return em.find(Member.class, memberId);
    }

}
