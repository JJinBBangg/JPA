package jpabook.jpashop.domain;

import jakarta.persistence.*;
import lombok.Builder;

@Entity
public class Delivery {

    @Id @GeneratedValue
    @Column(name = "DELIVERY_ID")
    private Long id;

    @OneToOne(mappedBy = "delivery")
    private Order order;

    @Embedded
    private Address address;

    @Enumerated(EnumType.STRING)
    private DeliveryStatus status;

    protected Delivery() {
    }
    @Builder
    private Delivery(Order order, Address address) {
        this.order = order;
        this.address = address;
    }
}
