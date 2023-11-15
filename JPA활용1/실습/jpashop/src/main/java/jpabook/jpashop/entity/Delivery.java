package jpabook.jpashop.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import static lombok.AccessLevel.*;

@Entity
@Getter
@NoArgsConstructor(access = PROTECTED)
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
