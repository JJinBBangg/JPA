package jpabook.jpashop.request;

import lombok.Builder;
import lombok.Getter;

@Getter
public class OrderItem {
    private Long itemId;
    private int count;

    public OrderItem() {
    }

    @Builder
    private OrderItem(Long itemId, int count) {
        this.itemId = itemId;
        this.count = count;
    }
}
