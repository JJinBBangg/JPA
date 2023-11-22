package jpabook.jpashop.api;

import jpabook.jpashop.entity.Order;
import jpabook.jpashop.request.CreateOrder;
import jpabook.jpashop.response.OrderResponse;
import jpabook.jpashop.response.OrderResponseV1;
import jpabook.jpashop.service.OrderService;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
@Slf4j
public class OrderApiController {

    private final OrderService orderService;

    // XXXToOne 문제해결
    // Order -> Member;
    // Order -> Delivery
    @GetMapping("/orders")
    public List<OrderResponse> getOrders(){
        log.info(">>>>>>>>>GET : /orders");
        //XXXToOne 문제 해결
//       return orderService.findAll(); //v2 DTO 로 불러왔지만 각각의 연결 테이블을 1 + N + N 쿼리 발생
//       return orderService.findAllWithDeliveryAndMember(); // v3 fetch join 을 사용하여 쿼리 1번으로 조회
        // v3 vs v4
        // v3는 가져온 값을 다른곳에서도 유연하게 필요한 데이터만 뽑아서 사용가능
        // v4는 필요한 값을 가져오는 곳에만 사용하기때문에 유연성 은 떨어지나 기능은 향상됨
        // 테이블의 컬럼수가 많고 필요한 데이터가 아주 일부일경우 v4로 성능향상을 기대할 수 있고
        // 테이블의 컬럼수가 적고 대부분의 컬럼을 동시에 사용할 일이 많은경우 v3가 유연하게 대처가능함
//        return orderService.findAllWithDeliveryAndMemberV2(); // v4 DTO 를 쿼리에 바로 반환받아 필요한 컬럼만 조회
        // XXXToMany 문제해결
//        return orderService.findAllWithDeliveryAndMember(); // v3 fetch join 을 사용하여 쿼리 1번으로 조회
        return orderService.find();
    }

    @GetMapping("/orders/{id}")
    public List<OrderResponse> getOrder(@PathVariable Long id, String name){
        return orderService.findOrderByName(name);
    }

    @PostMapping("/orders")
    public void add(CreateOrder createOrder){
        orderService.join(createOrder);
    }

    @AllArgsConstructor
    @Data
    private class Result<T> {
        private T data;
    }
}
