package jpabook.jpashop.response;

import jpabook.jpashop.entity.Delivery;
import jpabook.jpashop.entity.Member;
import jpabook.jpashop.entity.OrderItem;
import jpabook.jpashop.entity.OrderStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@NoArgsConstructor
public class OrderResponse {
    private Long id;
    private MemberResponse memberResponse;
    private LocalDateTime orderDate;
    private OrderStatus status;
    private List<OrderItemResponse> orderItemResponse;
    private DeliveryResponse deliveryResponse;

    @Builder
    private OrderResponse(Long id, Member member, LocalDateTime orderDate, OrderStatus status/*, List<OrderItem> orderItems*/, Delivery delivery) {
        this.id = id;
        this.memberResponse = member == null ? null : MemberResponse.builder()
                .id(member.getId())
                .name(member.getName())
                .address(member.getAddress())
                .build();
        this.orderDate = orderDate;
        this.status = status;
//        this.orderItemResponse = orderItems == null ? null : orderItems.stream()
//                .map(orderItem -> OrderItemResponse.builder()
//                        .id(orderItem.getId())
//                        .count(orderItem.getCount())
//                        .orderPrice(orderItem.getOrderPrice())
//                        .item(orderItem.getItem()) // 사용하면 가져오고 사용하지 않으면 가져오지 않음(쿼리)
//                        .build())
//                .collect(Collectors.toList());;
        this.deliveryResponse = delivery == null ? null : DeliveryResponse.builder()
                .status(delivery.getStatus())
                .addressResponse(delivery.getAddress())
                .build();
    }

    public void addOrderItems(List<OrderItemResponse> orderItemResponse){
        this.orderItemResponse = orderItemResponse;
    }
}
