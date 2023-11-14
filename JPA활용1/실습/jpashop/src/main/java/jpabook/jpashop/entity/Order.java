package jpabook.jpashop.entity;

import jakarta.persistence.*;
import jpabook.jpashop.entity.item.Item;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "ORDERS")
@Getter
public class Order {
    @Id @GeneratedValue
    @Column(name = "ORDER_ID")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "MEMBER_ID")
    private Member member;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL)
    private List<OrderItem> orderItems = new ArrayList<>();

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "DELIVERY_ID")
    private Delivery delivery;

    private LocalDateTime orderDate;

    @Enumerated(EnumType.STRING)
    private OrderStatus status;

    protected Order() {
    }
    public void setMember(Member member){
        this.member = member;
        member.getOrders().add(this);
    }

    @Builder
    private Order(Member member, List<OrderItem> orderItems, Delivery delivery,OrderStatus status) {
        this.member = member;
        this.delivery = delivery;
        this.orderDate = LocalDateTime.now();
        this.status = status;

        List<OrderItem> newItems = new ArrayList<>();
        for (OrderItem orderItem : orderItems) {
            OrderItem item = OrderItem.builder()
                    .count(orderItem.getCount())
                    .orderPrice(orderItem.getOrderPrice())
                    .order(this)
                    .build();
            newItems.add(item);
        }

        this.orderItems = newItems;
    }
//    public void addOrder(Long id, Member member, List<OrderItem> orderItems, Delivery delivery){
//        member.getOrders().add(this);
//        if(orderItems.get(0) != null){
//            for (OrderItem orderItem : orderItems) {
//                OrderItem.builder()
//                        .id(id)
//                        .orderPrice(orderItem.getOrderPrice())
//                        .count(orderItem.getCount())
//                        .order(this)
//                        .build();
//            }
//        }
//        if(delivery != null)Delivery.builder().order(this).build();
//    }
}
