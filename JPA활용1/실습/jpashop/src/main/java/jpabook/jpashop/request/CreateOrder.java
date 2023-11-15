package jpabook.jpashop.request;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
public class CreateOrder {

    private Long memberId;
    private List<OrderItem> orderItems;

    private String city;
    private String street;
    private String zipCode;

    private CreateOrder() {
    }

    @Builder
    private CreateOrder(Long memberId, List<OrderItem> orderItems, String city, String street, String zipCode) {
        this.memberId = memberId;
        this.orderItems = orderItems;
        this.city = city;
        this.street = street;
        this.zipCode = zipCode;
    }
}
