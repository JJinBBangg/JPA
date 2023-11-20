package jpabook.jpashop.request;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class OrderItem {
    private Long itemId;
    private int count;


    @Builder
    private OrderItem(Long itemId, int count) {
        this.itemId = itemId;
        this.count = count;
    }
}
