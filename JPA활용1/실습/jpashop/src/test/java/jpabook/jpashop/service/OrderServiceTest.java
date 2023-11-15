package jpabook.jpashop.service;

import jakarta.persistence.EntityManager;
import jpabook.jpashop.entity.Address;
import jpabook.jpashop.entity.Member;
import jpabook.jpashop.entity.Order;
import jpabook.jpashop.entity.OrderItem;
import jpabook.jpashop.entity.item.Book;
import jpabook.jpashop.entity.item.Movie;
import jpabook.jpashop.repository.ItemRepository;
import jpabook.jpashop.repository.MemberRepository;
import jpabook.jpashop.repository.OrderRepository;
import jpabook.jpashop.request.CreateOrder;
import jpabook.jpashop.request.Item;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class OrderServiceTest {

    @Autowired
    OrderService orderService;
    @Autowired
    MemberRepository memberRepository;
    @Autowired
    ItemRepository itemRepository;
    @Autowired
    OrderRepository orderRepository;
    @Autowired
    EntityManager em;

    @BeforeEach
    void beforeEach() {

    }

    @Test
    @DisplayName("주문저장-다른 주소값 주문")
    @Rollback(value = false)
    void test1() throws Exception {
        //given
        Address address = Address.builder()
                .city("진주")
                .street("사들로")
                .zipCode("157")
                .build();

        Member member = Member.builder()
                .name("member1")
                .address(address)
                .orders(new ArrayList<>())
                .build();
        memberRepository.save(member);

        Book book = Book.builder()
                .name("iris")
                .isbn("I3034E2")
                .author("jjinbbang")
                .stockQuantity(550)
                .price(10000)
                .build();
        itemRepository.save(book);

        Movie movie = Movie.builder()
                .name("iris")
                .director("kookjin")
                .actor("kookjin")
                .stockQuantity(300)
                .price(12000)
                .build();
        itemRepository.save(movie);
        em.flush();
        em.clear();
        //when
        List<Item> itemList = List.of(Item.builder()
                        .itemId(1L)
                        .count(50)
                        .build(),
                Item.builder()
                        .itemId(2L)
                        .count(30)
                        .build());
        CreateOrder order = CreateOrder.builder()
                .memberId(member.getId())
                .items(itemList)
                .city("서울")
                .street("마포구 고산18길")
                .zipCode("10-4")
                .build();
        orderService.join(order);
        //then
        Member findMember = memberRepository.findByNameWithOrder("member1");
        assertThat(findMember.getName()).isEqualTo("member1");
        assertThat(findMember.getOrders().size()).isEqualTo(1);
        assertThat(findMember.getOrders().get(0).getOrderItems().size()).isEqualTo(2);
        assertThat(findMember.getOrders().get(0).getDelivery().getAddress().getCity()).isEqualTo("서울");
    }

    @Test
    @DisplayName("주문저장-회원과 같은 주소값 또는 주소값 없이 주문")
    @Rollback(value = false)
    void test2() throws Exception {
        //given
        Address address = Address.builder()
                .city("진주")
                .street("사들로")
                .zipCode("157")
                .build();

        Member member = Member.builder()
                .name("member1")
                .address(address)
                .orders(new ArrayList<>())
                .build();
        memberRepository.save(member);

        Book book = Book.builder()
                .name("iris")
                .isbn("I3034E2")
                .author("jjinbbang")
                .stockQuantity(550)
                .price(10000)
                .build();
        itemRepository.save(book);

        Movie movie = Movie.builder()
                .name("iris")
                .director("kookjin")
                .actor("kookjin")
                .stockQuantity(300)
                .price(12000)
                .build();
        itemRepository.save(movie);
        em.flush();
        em.clear();
        //when
        List<Item> itemList = List.of(Item.builder()
                        .itemId(1L)
                        .count(50)
                        .build(),
                Item.builder()
                        .itemId(2L)
                        .count(30)
                        .build());
        CreateOrder order = CreateOrder.builder()
                .memberId(member.getId())
                .items(itemList)
                .city("진주")
                .street("사들로")
                .zipCode("157")
                .build();
        CreateOrder order1 = CreateOrder.builder()
                .memberId(member.getId())
                .items(itemList)
                .build();
        orderService.join(order);// 같은 주소값 입력
        orderService.join(order1); // 주소갑 입력없이
        //then
        Member findMember = memberRepository.findByNameWithOrder("member1");
        assertThat(findMember.getName()).isEqualTo("member1");
        assertThat(findMember.getOrders().get(0).getOrderItems().size()).isEqualTo(2);
        assertThat(findMember.getOrders().get(0).getDelivery().getAddress().getCity()).isEqualTo("진주");
        assertThat(findMember.getOrders().get(1).getDelivery().getAddress().getCity()).isEqualTo("진주");
    }

    @Test
    @DisplayName("주문취소")
    @Rollback(value = false)
    void test3() throws Exception {
        //given
        Address address = Address.builder()
                .city("진주")
                .street("사들로")
                .zipCode("157")
                .build();

        Member member = Member.builder()
                .name("member1")
                .address(address)
                .orders(new ArrayList<>())
                .build();
        memberRepository.save(member);

        Book book = Book.builder()
                .name("iris")
                .isbn("I3034E2")
                .author("jjinbbang")
                .stockQuantity(550)
                .price(10000)
                .build();
        itemRepository.save(book);

        Movie movie = Movie.builder()
                .name("iris")
                .director("kookjin")
                .actor("kookjin")
                .stockQuantity(300)
                .price(12000)
                .build();
        itemRepository.save(movie);
        em.flush();
        em.clear();

        List<Item> itemList = List.of(Item.builder()
                        .itemId(1L)
                        .count(50)
                        .build(),
                Item.builder()
                        .itemId(2L)
                        .count(30)
                        .build());
        CreateOrder order = CreateOrder.builder()
                .memberId(member.getId())
                .items(itemList)
                .city("서울")
                .street("마포구 고산18길")
                .zipCode("10-4")
                .build();
        orderService.join(order);
        em.flush();
        em.clear();
        //when

        Member findMember = memberRepository.findByNameWithOrder("member1");
        Order order1 = findMember.getOrders().get(0);
        Assertions.assertThat(order1.getOrderItems().get(0).getItem().getStockQuantity())
                .isEqualTo(500);
        Assertions.assertThat(order1.getOrderItems().get(1).getItem().getStockQuantity())
                .isEqualTo(270);

        orderService.cancel(order1.getId());
        Assertions.assertThat(order1.getOrderItems().get(0).getItem().getStockQuantity())
                .isEqualTo(550);
        Assertions.assertThat(order1.getOrderItems().get(1).getItem().getStockQuantity())
                .isEqualTo(300);

    }
    @Test
    @DisplayName("주문검색")
    @Rollback(value = false)
    void test4() throws Exception {
        //given
        Address address = Address.builder()
                .city("진주")
                .street("사들로")
                .zipCode("157")
                .build();

        Member member = Member.builder()
                .name("member1")
                .address(address)
                .orders(new ArrayList<>())
                .build();
        memberRepository.save(member);

        Book book = Book.builder()
                .name("iris")
                .isbn("I3034E2")
                .author("jjinbbang")
                .stockQuantity(550)
                .price(10000)
                .build();
        itemRepository.save(book);

        Movie movie = Movie.builder()
                .name("iris")
                .director("kookjin")
                .actor("kookjin")
                .stockQuantity(300)
                .price(12000)
                .build();
        itemRepository.save(movie);
        em.flush();
        em.clear();

        List<Item> itemList = List.of(Item.builder()
                        .itemId(1L)
                        .count(50)
                        .build(),
                Item.builder()
                        .itemId(2L)
                        .count(30)
                        .build());
        CreateOrder order = CreateOrder.builder()
                .memberId(member.getId())
                .items(itemList)
                .city("서울")
                .street("마포구 고산18길")
                .zipCode("10-4")
                .build();
        CreateOrder order1 = CreateOrder.builder()
                .memberId(member.getId())
                .items(itemList)
                .city("서울")
                .street("마포구 고산18길")
                .zipCode("10-4")
                .build();
        orderService.join(order);
        orderService.join(order1);
        em.flush();
        em.clear();
        //when
        List<Order> orders = orderService.findOrder("member1");
        Assertions.assertThat(orders.size()).isEqualTo(2);
    }
}