package jpabook.jpashop.entity;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;

@Entity
@Getter
public class Delivery {

    @Id @GeneratedValue
    @Column(name = "DELIVERY_ID")
    private Long id;

    @OneToOne(fetch = FetchType.LAZY,mappedBy = "delivery")
    private Order order;

    @Embedded
    private Address address;

    @Enumerated(EnumType.STRING)
    private DeliveryStatus status;

    protected Delivery() {
    }
    @Builder
    private Delivery( Order order, Address address, DeliveryStatus status) {
        this.order = order;
        this.address = address;
        this.status = (status == null ? DeliveryStatus.READY : status);
    }

    public void setStatus(DeliveryStatus status) {
        this.status = status;
    }
}
