package jpabook.jpashop.entity;

import jakarta.persistence.*;
import jpabook.jpashop.entity.item.Item;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import static jakarta.persistence.FetchType.*;
import static lombok.AccessLevel.*;

@Entity
@Getter
@NoArgsConstructor(access = PROTECTED)
public class OrderItem {

    @Id @GeneratedValue
    @Column(name = "ORDER_ITEM_ID")
    private Long id;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "ORDER_ID")
    private Order order;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "ITEM_ID")
    private Item item;

    private int orderPrice;

    private int count;

    // === 생성 로직 ===
    @Builder
    private OrderItem(Order order, Item item, int orderPrice, int count) {
        this.order = order;
        this.item = item;
        this.orderPrice = orderPrice;
        this.count = count;
    }
    // === 비지니스 로직 ===
    public int getTotalPrice(){
        return orderPrice * count;
    }
}
