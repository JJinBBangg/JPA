package jpabook.jpashop.service;

import jpabook.jpashop.entity.*;
import jpabook.jpashop.entity.item.Item;
import jpabook.jpashop.repository.ItemRepository;
import jpabook.jpashop.repository.MemberRepository;
import jpabook.jpashop.repository.OrderRepository;
import jpabook.jpashop.request.CreateOrder;
import jpabook.jpashop.response.OrderResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class OrderService {

    private final OrderRepository orderRepository;
    private final MemberRepository memberRepository;
    private final ItemRepository itemRepository;


    // 주문 로직
    public Long join(CreateOrder createOrder) {
        // 주문회원 조회
        Member orderMember = memberRepository.findOne(createOrder.getMemberId());

        // 주문물품 조회
        List<OrderItem> orderItems = getOrderItems(createOrder); //(itemId, count 사용하여 OrderItems 생성)

        // 배송정보 생성
        Address address = getAddress(createOrder); //입력된 주소로 Address 생성
        if((address.equals(orderMember.getAddress()) || (address.getCity() == null))) {
            //회원정보 주소지와 같은 경우
            address = null; // null로 입력하면 회원의 기존 배송지 사용
        } else {
            //회원정보 주소지와 다른 경우
            //새로운 주소지 추가 로직...
        }

        // 주문 생성
        Order order = Order.builder()
                .member(orderMember)
                .orderItems(orderItems)
                .address(address)
                .build();
        // 주문 저장
        orderRepository.save(order);

        return order.getId();
    }
    public List<Order> findAll(){
        return orderRepository.findAll();
    }


    // 취소 로직
    public void cancel(Long orderId){
        Order findOrder = orderRepository.findOne(orderId);
        findOrder.cancelOrder();
    }
    // 검색 로직
    @Transactional(readOnly = true)
    public OrderResponse findOrder(String username){
        Member byNameWithOrder = memberRepository.findByNameWithOrder(username);
        return OrderResponse.builder().build();
    }

    ///////////////////////////class 내부 메소드///////////////////////////
    private List<OrderItem> getOrderItems(CreateOrder createOrder) {
        log.info(">>>>>>>>>>>>>>>>{}",createOrder.getOrderItems().size());
        return createOrder.getOrderItems()
                .stream()
                .map((orderItem) -> {
                    Item findItem = itemRepository.findOne(orderItem.getItemId());
                    return OrderItem.builder()
                            .orderPrice(findItem.getPrice())
                            .count(orderItem.getCount())
                            .item(findItem)
                            .build();
                }).collect(Collectors.toList());
    }

    private static Address getAddress(CreateOrder createOrder) {
        return Address.builder()
                .city(createOrder.getCity())
                .street(createOrder.getStreet())
                .zipcode(createOrder.getZipCode())
                .build();
    }
}
