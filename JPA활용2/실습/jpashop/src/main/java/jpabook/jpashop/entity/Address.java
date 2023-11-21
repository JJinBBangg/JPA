package jpabook.jpashop.entity;

import jakarta.persistence.Embeddable;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Objects;

import static lombok.AccessLevel.*;

@Embeddable
@Getter
@NoArgsConstructor(access = PROTECTED)
public class Address {
    private String city;
    private String street;
    private String zipcode;

    @Builder
    private Address(String city, String street, String zipcode) {
        this.city = city;
        this.street = street;
        this.zipcode = zipcode;
    }

}
