package jpabook.jpashop.response;

import jpabook.jpashop.entity.item.Album;
import jpabook.jpashop.entity.item.Book;
import jpabook.jpashop.entity.item.Item;
import jpabook.jpashop.entity.item.Movie;
import lombok.Builder;
import lombok.Getter;

@Getter
public class OrderItemResponse {
    private Long id;
    private String name;
    private int orderPrice;
    private int count;
    private ItemResponse itemResponse;


    @Builder
    public OrderItemResponse(Long id, String name, int orderPrice, int count, Item item) {
        this.id = id;
        this.name = name;
        this.orderPrice = orderPrice;
        this.count = count;
        this.itemResponse = item == null ? null : ItemResponse.builder()
                .item(item)
                .build();


    }
}
