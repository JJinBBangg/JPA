package jpabook.jpashop.service;

import jpabook.jpashop.entity.*;
import jpabook.jpashop.entity.item.Item;
import jpabook.jpashop.repository.ItemRepository;
import jpabook.jpashop.repository.MemberRepository;
import jpabook.jpashop.repository.OrderRepository;
import jpabook.jpashop.request.CreateOrder;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final MemberRepository memberRepository;
    private final ItemRepository itemRepository;

    public void join(CreateOrder createOrder) {
        //주문 회원정보 조회
        Member orderMember = memberRepository.findOne(createOrder.getMemberId());
        //주문 물품 정보 조회
        List<OrderItem> itemList = createOrder.getItems()
                .stream()
                .map((item) -> {
                    Item one = itemRepository.findOne(item.getItemId());
                    return OrderItem.builder()
                            .orderPrice(one.getPrice())
                            .count(item.getCount())
                            .item(one)
                            .build();
                }).collect(Collectors.toList());
        // 새로운 주소 선택 후 주문 시
        // 회원의 기존 주소와 별도의 배송지 입력 시 회원의 주소를 추가로 저장하는로직
        Address address = Address.builder()
                .city(createOrder.getCity())
                .street(createOrder.getStreet())
                .zipCode(createOrder.getZipCode())
                .build();
        if(address == orderMember.getAddress()){
            address = null;
        } else {
            // 회원의 주소값을 추가로 저장하는 로직
        }

        // 저장
        orderRepository.save(Order.builder()
                .member(orderMember)
                .orderItems(itemList)
                .address(address)
                .build());

    }
}
