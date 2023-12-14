package jpabook.jpashop.repository.order.query_repository;

import jakarta.persistence.EntityManager;
import jpabook.jpashop.response.OrderItemResponse;
import jpabook.jpashop.response.OrderResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Repository
@RequiredArgsConstructor
public class OrderQueryRepository {

    private final EntityManager em;


    public List<OrderResponse> findAll() {
        // delivery 와 member 를 Dto 로 조회( XXToOne 관계 )
        List<OrderResponse> orders = getOrders();  // 쿼리 1번

        // 조회된 주문들의 ID를 추출
        List<Long> orderIdList = getOrderIdList(orders);

        // 해당 주문과 연결되어있는 orderItem 들과 해당 item 을 in 절을 사용하여 한번에 Dto 로 조회
        List<OrderItemResponse> orderItems = getOrderItems(orderIdList); // 쿼리 2번

        // 조회해온 orderItems 를 해당 order.id 값을 키로하여 Map 으로 변환
        Map<Long, List<OrderItemResponse>> orderItemListMap = getCollect(orderItems);

        // 각 주문의 ID 값을 키로 orderItem 을 주입시켜줌
        orders.stream().forEach(order -> order.addOrderItems(orderItemListMap.get(order.getId())));

        return orders;
    }

    private List<OrderItemResponse> getOrderItems(List<Long> orderIdList) {
        return em.createQuery("select new jpabook.jpashop.response.OrderItemResponse ( " +
                        " oi.id, " +
                        " oi.order.id, " +
                        " oi.orderPrice, " +
                        " oi.count, " +
                        " oi.item ) " +
                        "from OrderItem oi " +
                        "join oi.item i " +
                        "where  oi.order.id in :orderIdList", OrderItemResponse.class)
                .setParameter("orderIdList", orderIdList)
                .getResultList();
    }

    private static Map<Long, List<OrderItemResponse>> getCollect(List<OrderItemResponse> orderItems) {
        return orderItems.stream().collect(Collectors.groupingBy(orderItem -> orderItem.getOrderId()));
    }

    private static List<Long> getOrderIdList(List<OrderResponse> orders) {
        List<Long> orderIdList = orders.stream()
                .map(o -> o.getId())
                .collect(Collectors.toList());
        return orderIdList;
    }

    private List<OrderResponse> getOrders() {
        List<OrderResponse> orders = em.createQuery(
                        "select new jpabook.jpashop.response.OrderResponse( " +
                                " o.id, " +
                                " o.member, " +
                                " o.orderDate, " +
                                " o.status, " +
                                " o.delivery) " +
                                "from Order o " +
                                "join o.delivery d " +
                                "join o.member m", OrderResponse.class)
                .getResultList();
        return orders;
    }
}
