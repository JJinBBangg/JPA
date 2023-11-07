package jpabook.jpashop.domain;

import jakarta.persistence.*;

import static jakarta.persistence.FetchType.LAZY;

@Entity
public class Delivery extends BaseEntity{
    @Id
    @GeneratedValue
    private Long id;

    @OneToOne(mappedBy = "delivery",fetch = LAZY)
    private Order order;

    private String city;
    private String street;
    private String zipCode;
    @Enumerated(EnumType.STRING)
    private DeliveryStatus status;
}
