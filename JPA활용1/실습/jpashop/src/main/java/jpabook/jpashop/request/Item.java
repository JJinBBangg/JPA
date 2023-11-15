package jpabook.jpashop.request;

import lombok.Builder;
import lombok.Getter;

@Getter
public class Item {
    private Long itemId;
    private int count;

    public Item() {
    }

    @Builder
    private Item(Long itemId, int count) {
        this.itemId = itemId;
        this.count = count;
    }
}
