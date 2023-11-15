package jpabook.jpashop.repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jpabook.jpashop.entity.Order;
import jpabook.jpashop.entity.item.Item;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

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



    public void deleteAll(){
        em.createQuery("delete from Order").executeUpdate();
    }

}
