package jpabook.jpashop.service;

import jpabook.jpashop.entity.*;
import jpabook.jpashop.entity.item.Item;
import jpabook.jpashop.repository.ItemRepository;
import jpabook.jpashop.repository.MemberRepository;
import jpabook.jpashop.repository.OrderRepository;
import jpabook.jpashop.repository.order.query_repository.OrderQueryRepository;
import jpabook.jpashop.request.CreateOrder;
import jpabook.jpashop.response.OrderItemResponse;
import jpabook.jpashop.response.OrderResponse;
import jpabook.jpashop.response.OrderResponseV1;
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
    private final OrderQueryRepository orderQueryRepository;


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

    @Transactional(readOnly = true)
    public List<OrderResponse> findAllWithDeliveryAndMember(){
        List<Order> findOrder = orderRepository.findAllWithDeliveryAndMember();
        List<OrderResponse> orderResponses = findOrder
                .stream()
                .map(order -> OrderResponse.builder()
                        .id(order.getId())
                        .member(order.getMember())// 사용하면 쿼리 날라가고 안하면 안날라감 LAZY
                        .orderDate(order.getOrderDate())
                        .orderItems(order.getOrderItems()) // 사용하면 쿼리 날라가고 안하면 안날라감 LAZY
                        .delivery(order.getDelivery()) // 사용하면 쿼리 날라가고 안하면 안날라감 LAZY // 왜 2번 쿼리가 실행되는지 모르겠음.
                        .status(order.getStatus())
                        .build())
                .collect(Collectors.toList());
        return orderResponses;
    }


    @Transactional(readOnly = true)
    public List<OrderResponse> findAllByQeruy(){
        orderQueryRepository.findAll();
        }
    }

    @Transactional(readOnly = true)
    public List<OrderResponse> findAll(){
        return orderRepository.findAll()
                .stream()
                .map(order -> OrderResponse.builder()
                        .id(order.getId())
                        .member(order.getMember())// 사용하면 쿼리 날라가고 안하면 안날라감 LAZY
                        .orderDate(order.getOrderDate())
                        .orderItems(order.getOrderItems()) // 사용하면 쿼리 날라가고 안하면 안날라감 LAZY
                        .delivery(order.getDelivery()) // 사용하면 쿼리 날라가고 안하면 안날라감 LAZY // 왜 2번 쿼리가 실행되는지 모르겠음.
                        .status(order.getStatus())
                        .build())
                .collect(Collectors.toList());
    }


    // 취소 로직
    public void cancel(Long orderId){
        Order findOrder = orderRepository.findOne(orderId);
        findOrder.cancelOrder();
    }
    // 검색 로직
    @Transactional(readOnly = true)
    public List<OrderResponse> findOrderByName(String username){
        Member findMember = memberRepository.findByNameWithOrder(username);
        return findMember.getOrders()
                .stream()
                .map(order -> OrderResponse.builder()
                        .id(order.getId())
                        .member(order.getMember()) // 사용하면 쿼리 날라가고 안하면 안날라감 LAZY
                        .status(order.getStatus())
                        .orderDate(order.getOrderDate())
                        .orderItems(order.getOrderItems()) // 사용하면 쿼리 날라가고 안하면 안날라감 LAZY
                        .delivery(order.getDelivery()) // 사용하면 쿼리 날라가고 안하면 안날라감 LAZY
                        .build())
                .collect(Collectors.toList());
    }





    ///////////////////////////class 내부 메소드///////////////////////////

    private List<OrderItem> getOrderItems(CreateOrder createOrder) {
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

    private Address getAddress(CreateOrder createOrder) {
        return Address.builder()
                .city(createOrder.getCity())
                .street(createOrder.getStreet())
                .zipcode(createOrder.getZipCode())
                .build();
    }


    public List<OrderResponseV1> findAllWithDeliveryAndMemberV2() {
        return orderRepository.findAllWithDeliveryAndMemberV2();
    }
}
// 적절하지 않은 코드
// 아래처럼 작성 시 build 할 때 접근한 각 객체의 모든 데이터를 로딩하는 쿼리문이 발생함
//        List<OrderResponse> orderResponses = orderRepository.findAll()
//                .stream()
//                .map(order ->
//                        OrderResponse.builder()
//                        .id(order.getId())
//                        .status(order.getStatus())
//                        .orderDate(order.getOrderDate())
//                        .itemResponse(getItemResponse(order.getOrderItems()))
//                        .deliveryResponse(getDelivery(order))
//                        .build())
//                .collect(Collectors.toList());
// 수정된 코드
// 추가된 로직이 필요할 시 필요한 객체를 찾고 사용하는 방식으로 작성해야 쿼리가 덜나감
// 위 하고의 차이는 order.getOrderItems.stream() 형태로 사용하게 될 때
// orderItem 객체와 join 되어있는 item 을 각각의 join 쿼리를 만들게됨
// 예 select * from order_item where orderId = ?;
//    select * from order_item oi join item i on oi.id = i.id;

// 아래처럼 Order 응답 객체는 Order 조회만 하는 쿼리 1개로 해결하고
// 추가할 정보가 있을 시 해당되는 각각의 정보를 취합하는 방식으로 사용하는 것이 쿼리가 많이 나가지않음
// 로직 중 item 이미 가져온 경우 해당되는 item id 로 가져오면 영속성 컨텍스트에 등록 되어있을 시
// 추가 쿼리 없이 가져올 수 있음

// orderItem 인 경우 Order 와 1:N이기 때문에 필요하면 추가하거나(fetch join 을 사용하여 가져오고)
// Item, Member, Delivery 같은 경우 N:1 조회인 경우 Order 또는 OrderItem 의 참조 값을
// 기준으로 검색쿼리를 각각의 Repository 에서 가져와 사용하는것이 올바름
// (영속성 컨텍스트를 우선 검색하고 없으면 조회하되 불필요한 join 쿼리 만들지 않게 하는 것)
// 복잡한 join 필요할 시 queryDsl 사용해서 단건의 쿼리를 작성하도록 만드는 것이 올바름

