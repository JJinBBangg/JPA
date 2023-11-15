package jpabook.jpashop.request;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
public class CreateOrder {

    private Long memberId;
    private List<Item> items;

    private String city;
    private String street;
    private String zipCode;

    private CreateOrder() {
    }

    @Builder
    private CreateOrder(Long memberId, List<Item> items, String city, String street, String zipCode) {
        this.memberId = memberId;
        this.items = items;
        this.city = city;
        this.street = street;
        this.zipCode = zipCode;
    }
}
