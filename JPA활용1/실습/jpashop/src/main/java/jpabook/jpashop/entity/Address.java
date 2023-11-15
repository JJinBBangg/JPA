package jpabook.jpashop.entity;

import jakarta.persistence.Embeddable;
import lombok.Builder;
import lombok.Getter;

import java.util.Objects;

@Embeddable
@Getter
public class Address {
    private String city;
    private String street;
    private String zipCode;

    protected Address() {
    }
    @Builder
    private Address(String city, String street, String zipCode) {
        this.city = city;
        this.street = street;
        this.zipCode = zipCode;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Address address = (Address) o;
        return Objects.equals(getCity(), address.getCity())
                && Objects.equals(getStreet(), address.getStreet())
                && Objects.equals(getZipCode(), address.getZipCode());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getCity(), getStreet(), getZipCode());
    }
}
