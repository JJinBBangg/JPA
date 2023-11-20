package jpabook.jpashop.response;

import jpabook.jpashop.entity.Address;
import jpabook.jpashop.entity.DeliveryStatus;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Repository;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class DeliveryResponse {
    private DeliveryStatus status;
    private AddressResponse addressResponse;

    @Builder
    public DeliveryResponse(DeliveryStatus status, Address addressResponse) {
        this.status = status;
        this.addressResponse = AddressResponse.builder()
                .city(addressResponse.getCity())
                .street(addressResponse.getStreet())
                .zipcode(addressResponse.getZipcode())
                .build();
    }
}
