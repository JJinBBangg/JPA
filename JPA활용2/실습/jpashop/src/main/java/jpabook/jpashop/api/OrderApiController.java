package jpabook.jpashop.api;

import jpabook.jpashop.entity.Order;
import jpabook.jpashop.request.CreateOrder;
import jpabook.jpashop.response.OrderResponse;
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
       return orderService.findAll();
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
