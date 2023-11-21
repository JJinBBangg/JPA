package jpabook.jpashop.repository;

import jakarta.persistence.EntityManager;
import jpabook.jpashop.entity.Order;
import jpabook.jpashop.response.OrderItemResponse;
import jpabook.jpashop.response.OrderResponse;
import jpabook.jpashop.response.OrderResponseV1;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.stream.Collectors;

@Repository
@RequiredArgsConstructor
public class OrderRepository {

    private final EntityManager em;

    public Long save(Order order) {
        em.persist(order);
        return order.getId();
    }


    public Order findOne(Long orderId) {
        return em.find(Order.class, orderId);
    }

    public List<Order> findAll() {
        return em.createQuery("select o from Order o", Order.class)
                .getResultList();
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

    public void deleteAll() {
        em.createQuery("delete from Delivery").executeUpdate();
        em.createQuery("delete from OrderItem ");
        em.createQuery("delete from Order").executeUpdate();
    }

    public List<Order> findAllWithDeliveryAndMember() {
        return em.createQuery(
                        // distinct 를 사용하면 from 절의 order 를 기준으로 식별자 값이 같은 것은
                        // 어플리케이션 레벨에서 중복을 제거하고 객체에 넣어준다.
                        // 하지만 distinct 를 사용하더라도 DB의 실제 쿼리결과는 1:N 관계로 인해서
                        // 1 * N개의 로우(row)가 조회되기 때문에
                        // OneToMany 관계에서는 페이징 처리를 사용하면안됨
                        // 추가로 1 : N fetch join 은 1개만 사용해야한다
                        "select distinct o from Order o " +
                                "join fetch o.member m " +
                                "join fetch o.delivery d "
                        , Order.class)
//                .setFirstResult(1)
//                .setMaxResults(100)
                .getResultList();
    }

    public List<OrderResponseV1> findAllWithDeliveryAndMemberV2() {
        getOrder().stream().forEach(order -> {
            List<OrderResponseV1> orderItems = findOrderItems(order.getId());
        });
        return null;

    }

    private List<OrderResponseV1> findOrderItems(Long id) {
        this.orderItemResponse = orderItems == null ? null : orderItems.stream()
                .map(orderItem -> OrderItemResponse.builder()
                        .id(orderItem.getId())
                        .count(orderItem.getCount())
                        .orderPrice(orderItem.getOrderPrice())
                        .item(orderItem.getItem()) // 사용하면 가져오고 사용하지 않으면 가져오지 않음(쿼리)
                        .build())
                .collect(Collectors.toList());;
        return null;
    }

    private List<OrderResponseV1> getOrder() {
        return em.createQuery(
                // 쿼리문에 바로 DTO 를 사용하는경우
                // builder 사용하도 상관없음
                // setter 없어도 상관없음
                // 기본생성자 scope, 없어도 상관없음
                // AllArgsConstructor 의 파라미터 타입을 기준 필드 타입기준아님
                // 가져온 table 을 객체 타입에 맞춰서 내부 내용도 사용가능
                // 필요한 필드값만 컬럼의 값으로 바로 뽑아올 수 있음 type 만 맞으면 됨
                "select new jpabook.jpashop.response.OrderResponseV1(" +
                        "    o.id, " +
                        "    o.member , " +
                        "    o.orderDate, " +
                        "    o.status, " +
                        "    o.delivery ) " +
                        "from Order o " +
                        "join o.member m " +
                        "join o.delivery d " +
                        "join o.orderItems oi",
                OrderResponseV1.class
        ).getResultList();
    }
}
