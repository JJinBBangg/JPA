package jpabook.jpashop.repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jpabook.jpashop.entity.Member;
import jpabook.jpashop.entity.Order;
import jpabook.jpashop.entity.OrderStatus;
import jpabook.jpashop.entity.item.Item;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.swing.*;
import java.util.List;
import java.util.stream.Collectors;

@Repository
@RequiredArgsConstructor
public class OrderRepository {

    private final EntityManager em;

    public Long save(Order order){
        em.persist(order);
        return order.getId();
    }

    public Order findOne(Long orderId){
        return em.find(Order.class, orderId);
    }

    public List<Order> findAll() {
        return em.createQuery("select o from Order o", Order.class).getResultList();
    }

//    public List<Order> findByStatus(OrderStatus status){
////        List<Order> findMember = em.createQuery("select m from Member m join fetch m.orders", Member.class)
////                .getResultList()
////                .stream()
////                .map((member)->member.getOrders())
////                .collect(Collectors.toList());
//
////        findMember.stream()
////                .map((member)->{
////
////                    member.getOrders()
////                        .stream()
////                        .filter((order -> order.getStatus() == status))
////                        .forEach();
////
////                    return
////                })
////                .collect(Collectors.toList());
//        return null;
//    }
//    public List<Order> findByName(String name){
//        Member findMember = em.createQuery("select m from Member m join fetch m.orders where m.id = :id", Member.class)
//                .setParameter("id", memberId)
//                .getSingleResult();
//
//    }

    public void deleteAll(){
        em.createQuery("delete from Delivery").executeUpdate();
        em.createQuery("delete from OrderItem ");
        em.createQuery("delete from Order").executeUpdate();
    }

}
