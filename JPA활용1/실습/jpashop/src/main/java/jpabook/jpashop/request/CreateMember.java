package jpabook.jpashop.request;

import jakarta.validation.constraints.NotEmpty;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class CreateMember {
    @NotEmpty(message = "이름 값은 필수입니다.")
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
