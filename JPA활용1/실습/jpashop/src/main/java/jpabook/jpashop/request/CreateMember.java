package jpabook.jpashop.request;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class CreateMember {
    private String name;
    private String city;
    private String street;
    private String zipCode;


    @Builder
    public CreateMember(String name, String city, String street, String zipCode) {
        this.name = name;
        this.city = city;
        this.street = street;
        this.zipCode = zipCode;
    }
}
