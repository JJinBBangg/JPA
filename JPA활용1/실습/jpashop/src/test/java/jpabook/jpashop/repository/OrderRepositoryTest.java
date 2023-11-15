package jpabook.jpashop.repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jpabook.jpashop.entity.*;
import jpabook.jpashop.entity.item.Book;
import jpabook.jpashop.entity.item.Item;
import jpabook.jpashop.entity.item.Movie;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@SpringBootTest
@Transactional
class OrderRepositoryTest {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private ItemRepository itemRepository;

    @PersistenceContext
    private EntityManager em;

    @Test
    @DisplayName("주문 저장")
    @Rollback(value = false)
    void test1() {
        //given
        Address address = Address.builder()
                .city("진주")
                .street("사들로")
                .zipCode("157")
                .build();

        Member member1 = Member.builder()
                .name("member1")
                .address(address)
                .orders(new ArrayList<>())
                .build();
        memberRepository.save(member1);

        Book book = Book.builder()
                .stockQuantity(100)
                .price(1000)
                .isbn("123")
                .author("123")
                .build();
        itemRepository.save(book);

        Movie movie = Movie.builder()
                .director("kookjin")
                .actor("kookjin")
                .name("iris")
                .stockQuantity(300)
                .price(1000)
                .build();
        itemRepository.save(movie);

        // order생성전에 주문하는 회원가입, 등록되어있는 물품 DB에 저장
        em.flush();
        em.clear();
        System.out.println("=========================");
        //when
        Member findMember = memberRepository.findOne(member1.getId());
        Item findItem1 = itemRepository.findOne(book.getId());
        Item findItem2 = itemRepository.findOne(movie.getId());

        OrderItem orderItem = OrderItem.builder()
                .orderPrice(findItem1.getPrice())
                .count(15)
                .item(findItem1)
                .build();
        OrderItem orderItem2 = OrderItem.builder()
                .orderPrice(findItem2.getPrice())
                .count(15)
                .item(findItem2)
                .build();


        List<OrderItem> orderItemList = List.of(orderItem, orderItem2);


        Order order = Order.builder()
                .member(findMember)
                .orderItems(orderItemList)
                .address(findMember.getAddress())
//                .status(OrderStatus.ORDER) // null로 들어가면 기본값을 order로 설정
                .build();


        orderRepository.save(order);
//        order.cancelOrder(); // 취소 시 테스트
        //then
    }

    @Test
    @DisplayName("주문자명을 통한 주문검색")
    @Rollback(value = false)
    void test2() throws Exception {
        //given
        Address address = Address.builder()
                .city("진주")
                .street("사들로")
                .zipCode("157")
                .build();

        Member member1 = Member.builder()
                .name("member1")
                .address(address)
                .orders(new ArrayList<>())
                .build();
        memberRepository.save(member1);
        Address address1 = Address.builder()
                .city("서울")
                .street("마포구 고산18길")
                .zipCode("10-4")
                .build();

        Member member2 = Member.builder()
                .name("member2")
                .address(address1)
                .orders(new ArrayList<>())
                .build();
        memberRepository.save(member2);
        //2명의 member가 주문을 하는 경우

        Book book = Book.builder()
                .stockQuantity(100)
                .price(1000)
                .isbn("123")
                .author("123")
                .build();
        itemRepository.save(book);

        Movie movie = Movie.builder()
                .director("kookjin")
                .actor("kookjin")
                .name("iris")
                .stockQuantity(300)
                .price(1000)
                .build();
        itemRepository.save(movie);

        // order생성전에 주문하는 회원가입, 등록되어있는 물품 DB에 저장
        em.flush();
        em.clear();
        System.out.println("=========================");
        //when
        Member findMember = memberRepository.findOne(member1.getId());
        Member findMember1 = memberRepository.findOne(member2.getId());
        Item findItem1 = itemRepository.findOne(book.getId());
        Item findItem2 = itemRepository.findOne(movie.getId());

        OrderItem orderItem = OrderItem.builder()
                .orderPrice(findItem1.getPrice())
                .count(15)
                .item(findItem1)
                .build();
        OrderItem orderItem2 = OrderItem.builder()
                .orderPrice(findItem2.getPrice())
                .count(15)
                .item(findItem2)
                .build();


        List<OrderItem> orderItemList = List.of(orderItem, orderItem2);


        Order order = Order.builder()
                .member(findMember)
                .orderItems(orderItemList)
                .address(findMember.getAddress())
//                .status(OrderStatus.ORDER) // null로 들어가면 기본값을 order로 설정
                .build();

        Order order2 = Order.builder()
                .member(findMember1)
                .orderItems(orderItemList)
                .address(findMember1.getAddress())
                .build();

        Order order3 = Order.builder()
                .member(findMember1)
                .orderItems(orderItemList)
                .address(findMember1.getAddress())
                .build();
        //when
        orderRepository.save(order);
        orderRepository.save(order2);
        orderRepository.save(order3);
//        orderRepository.save(order2);아래처럼 여러 주문을 하더라도 같은객체로 관리되어 수정값을 입력하게됨
        System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
        em.flush();
        em.clear();
        //then
        // 쿼리가 어떻게 나가는지 확인하고 튜닝하기
        Member findByName = memberRepository.findByNameWithOrder("member2");
        List<Order> orders = findByName.getOrders();
        for (Order order1 : orders) {
            System.out.println("order1.getId() = " + order1.getId());
        }
    }
}