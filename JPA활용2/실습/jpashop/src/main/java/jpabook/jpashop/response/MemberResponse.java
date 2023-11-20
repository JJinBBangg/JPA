package jpabook.jpashop.response;

import jpabook.jpashop.entity.Address;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import static lombok.AccessLevel.*;

@Getter
@NoArgsConstructor(access = PROTECTED)
public class MemberResponse {
    private Long id;
    private String name;
    private AddressResponse addressResponse;

    @Builder
    private MemberResponse(Long id, String name, Address address){
        this.id = id;
        this.name =name;
        this.addressResponse = address == null ? null : AddressResponse.builder()
                                                            .city(address.getCity())
                                                            .street(address.getStreet())
                                                            .zipcode(address.getZipcode())
                                                            .build();
    }

}
