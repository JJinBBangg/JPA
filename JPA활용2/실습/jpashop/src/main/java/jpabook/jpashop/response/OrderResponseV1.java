package jpabook.jpashop.response;

import jpabook.jpashop.entity.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class OrderResponseV1 {

    private Long id;
    private MemberResponse memberResponse;
    private LocalDateTime orderDate;
    private OrderStatus status;
    private List<OrderItemResponse> orderItemResponse;
    private DeliveryResponse deliveryResponse;

    @Builder
    private OrderResponseV1(Long id, Member member, LocalDateTime orderDate, OrderStatus status,Delivery delivery) {
        this.id = id;
        this.memberResponse = member == null ? null : MemberResponse.builder()
                .id(member.getId())
                .name(member.getName())
                .address(member.getAddress())
                .build();
        this.orderDate = orderDate;
        this.status = status;

        this.deliveryResponse = delivery == null ? null : DeliveryResponse.builder()
                .status(delivery.getStatus())
                .addressResponse(delivery.getAddress())
                .build();
    }
}
