package jpabook.jpashop.api;

import jpabook.jpashop.entity.Order;
import jpabook.jpashop.request.CreateOrder;
import jpabook.jpashop.service.OrderService;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.aspectj.weaver.ast.Or;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class OrderApiController {

    private final OrderService orderService;

    // XXXToOne 문제해결
    // Order -> Member;
    // Order -> Delivery
    @GetMapping("/orders")
    public Result getOrders(){
        return new Result(orderService.findAll());
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
