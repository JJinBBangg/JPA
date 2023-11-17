package jpabook.jpashop.response;

import jpabook.jpashop.entity.Address;
import jpabook.jpashop.entity.DeliveryStatus;
import org.springframework.stereotype.Repository;

@Repository
public class DeliveryResponse {
    private DeliveryStatus status;
    private AddressResponse addressResponse;
}
