package jpabook.jpashop.request;

import jakarta.validation.constraints.NotEmpty;
import lombok.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Setter
public class MemberForm {
    @NotEmpty(message = "이름 값은 필수입니다.")
    private String name;
    private String city;
    private String street;
    private String zipcode;

    @Builder
    public MemberForm(String name, String city, String street, String zipcode) {
        this.name = name;
        this.city = city;
        this.street = street;
        this.zipcode = zipcode;
    }
}
