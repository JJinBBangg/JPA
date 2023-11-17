package jpabook.jpashop.response;

import jpabook.jpashop.entity.OrderStatus;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class OrderResponse {
    private Long id;
    private LocalDateTime orderDate;
    private OrderStatus status;
    private ItemResponse itemResponse;
    private DeliveryResponse deliveryResponse;

    @Builder
    private OrderResponse(Long id, LocalDateTime orderDate, OrderStatus status, ItemResponse itemResponse, DeliveryResponse deliveryResponse) {
        this.id = id;
        this.orderDate = orderDate;
        this.status = status;
        this.itemResponse = itemResponse;
        this.deliveryResponse = deliveryResponse;
    }
}
