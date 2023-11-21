package jpabook.jpashop.entity;

import jakarta.persistence.*;
import jpabook.jpashop.exception.AlreadyDeliveryException;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.BatchSize;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static jakarta.persistence.CascadeType.ALL;
import static jakarta.persistence.FetchType.LAZY;
import static lombok.AccessLevel.PROTECTED;

@Entity
@Table(name = "ORDERS")
@Getter
@NoArgsConstructor(access = PROTECTED)
public class Order {
    @Id
    @GeneratedValue
    @Column(name = "ORDER_ID")
    private Long id;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "MEMBER_ID")
    private Member member;

    @OneToMany(mappedBy = "order", cascade = ALL)
    private List<OrderItem> orderItems = new ArrayList<>();

    @OneToOne(fetch = LAZY, cascade = ALL)
    @JoinColumn(name = "DELIVERY_ID")
    private Delivery delivery;

    private LocalDateTime orderDate;

    @Enumerated(EnumType.STRING)
    private OrderStatus status = OrderStatus.ORDER;


    public void setMember(Member member) {
        this.member = member;
        member.getOrders().add(this);
    }

    // === 기본생성 로직 ===
    // 기본 주문 생성은  builder 로 생성하고 이외 수정 및 취소는 별도의 메서드를 만들어서 처리
    // CascadeType.All 에 유의해서 사용해야함(delivery 와 orderItems 는 최초 입력되는 값이 바로 persist됨
    @Builder
    private Order(Member member, List<OrderItem> orderItems, Address address, OrderStatus status) {
        this.member = member;
        // 주소값이 입력없으면 member 기존주소값을 사용하고 입력되는 주소값이 있으면 새로운 주소사용
        this.delivery = (address == null ? Delivery.builder()
                .address(member.getAddress())
                .build()
                :
                Delivery.builder()
                        .address(address)
                        .build());
        this.orderDate = LocalDateTime.now();
        // status 가 입력되지않으면서 최초 builder 사용 시 기본값을 ORDER로 설정
        this.status = (status == null ? OrderStatus.ORDER : status);
        // 주문으로 들어온 제품의 갯수와 가격 정보에 주문을 연결시키는 연관관계 메서드
        List<OrderItem> newItems = new ArrayList<>();
        for (OrderItem orderItem : orderItems) {
            OrderItem newItem = OrderItem.builder()
                    .count(orderItem.getCount())
                    .orderPrice(orderItem.getOrderPrice())
                    .order(this)
                    .item(orderItem.getItem())
                    .build();
            newItems.add(newItem);
            //주문한 상품의 갯수를 차감
            newItem.getItem().removeStock(orderItem.getCount());
            // 차감 및 추가 작업을 OrderItem 객체에서 하는것이 맞는가? 여기서 실행하는것이 맞은가?
            // CascadeType.ALL인데 넘어가야하는가? 결과적으로 생성 메서드를 사용하는 쪽에서 사용하는것이 맞음
            // OrderItem 객체가 생성되는 시점에서 item갯수를 차감하도록 지정
        }
        this.orderItems = newItems;
    }

    // === 비지니스 로직 ===
    public void cancelOrder() {
        if (this.delivery.getStatus() == DeliveryStatus.CAMP) {
            throw new AlreadyDeliveryException();
        }
        changeStatus(OrderStatus.CANCEL);
        List<OrderItem> orderItemList = this.orderItems;
        for (OrderItem orderItem : orderItemList) {
            //취소된 상품 갯수를 다시 더 해준다
            orderItem.getItem().addStock(orderItem.getCount());
        }
    }

    // 내부에서 사용하는 method 를 통해서만 수정가능하도록 private changeStatus 메서드 생성
    private void changeStatus(OrderStatus status) {
        this.status = status;
    }

    // === 조회 로직 ===
    // 전체 주문 가격 조회
    public int getTotalPrice() {
        List<OrderItem> orderItems = this.orderItems;
        return orderItems.stream()
                .mapToInt(OrderItem::getTotalPrice)
                .sum();
    }
    //
}
