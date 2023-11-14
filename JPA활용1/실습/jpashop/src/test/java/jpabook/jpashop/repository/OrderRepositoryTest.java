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
    @Transactional
    @Rollback(value = false)
    void test1(){
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
                .price(1000)
                .build();
        itemRepository.save(movie);


        //when
        Member member = memberRepository.findOne(member1.getId());
        Item findItem1 = itemRepository.findOne(book.getId());
        Item findItem2 = itemRepository.findOne(movie.getId());

        OrderItem orderItem = OrderItem.builder()
                .orderPrice(findItem1.getPrice())
                .count(321)
                .item(findItem1)
                .build();
        OrderItem orderItem2 = OrderItem.builder()
                .orderPrice(findItem1.getPrice())
                .count(321)
                .item(findItem2)
                .build();


        List<OrderItem> orderItemList = List.of(orderItem, orderItem2);

        Delivery delivery = Delivery.builder()
                .status(DeliveryStatus.READY)
                .address(address)
                .build();

        Order order = Order.builder()
                .member(member)
                .orderItems(orderItemList)
                .delivery(delivery)
                .status(OrderStatus.ORDER)
                .build();


        orderRepository.save(order);
        //then


    }

}