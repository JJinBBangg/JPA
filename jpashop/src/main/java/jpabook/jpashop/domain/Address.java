package jpabook.jpashop.domain;

import jakarta.persistence.Embeddable;
import jakarta.persistence.MappedSuperclass;

import java.util.Objects;

@Embeddable
public class Address {
    private String city;
    private String street;
    private String zipCode;

    public Address() {

    }

    private Address(String city, String street, String zipCode) {
        this.city = city;
        this.street = street;
        this.zipCode = zipCode;
    }

    public String getCity() {
        return city;
    }

    public String getStreet() {
        return street;
    }

    public String getZipCode() {
        return zipCode;
    }

    public static class Builder {
        private String city;
        private String street;
        private String zipCode;

        public Builder city(String city) {
            this.city = city;
            return this;
        }

        public Builder street(String street) {
            this.street = street;
            return this;
        }

        public Builder zipCode(String zipCode) {
            this.zipCode = zipCode;
            return this;
        }

        public Address build() {
            return new Address(this.city, this.street, this.zipCode);
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Address address = (Address) o;
        return Objects.equals(city, address.getCity())
                && Objects.equals(street, address.getStreet())
                && Objects.equals(zipCode, address.getZipCode());
    }

    @Override
    public int hashCode() {
        return Objects.hash(city, street, zipCode);
    }
}
