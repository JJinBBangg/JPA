package jpabook.jpashop;

import jakarta.annotation.PostConstruct;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import jpabook.jpashop.entity.Address;
import jpabook.jpashop.entity.Member;
import jpabook.jpashop.entity.Order;
import jpabook.jpashop.entity.OrderItem;
import jpabook.jpashop.entity.item.Book;
import jpabook.jpashop.entity.item.Movie;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
public class InitDb {

    private final InitService initService;
    @PostConstruct
    public void init (){
        initService.dbInit();
    }
    @Component
    @RequiredArgsConstructor
    @Transactional
    static class InitService {
        private final EntityManager em;
        public void dbInit() {
            Address address = Address.builder()
                    .city("진주")
                    .street("사들로")
                    .zipcode("157")
                    .build();

            Member member1 = Member.builder()
                    .name("userA")
                    .address(address)
                    .build();
            em.persist(member1);

            Address address1 = Address.builder()
                    .city("서울")
                    .street("마포구 고산18길")
                    .zipcode("10-4")
                    .build();

            Member member2 = Member.builder()
                    .name("userB")
                    .address(address1)
                    .build();
            em.persist(member2);
            //2명의 member가 주문을 하는 경우

            Book book = Book.builder()
                    .stockQuantity(100)
                    .price(1000)
                    .isbn("123")
                    .author("123")
                    .build();
            em.persist(book);

            Movie movie = Movie.builder()
                    .director("kookjin")
                    .actor("kookjin")
                    .name("iris")
                    .stockQuantity(300)
                    .price(1000)
                    .build();
            em.persist(movie);

            OrderItem orderItem = OrderItem.builder()
                    .orderPrice(book.getPrice())
                    .count(15)
                    .item(book)
                    .build();
            OrderItem orderItem2 = OrderItem.builder()
                    .orderPrice(movie.getPrice())
                    .count(15)
                    .item(movie)
                    .build();

            List<OrderItem> orderItemList = List.of(orderItem, orderItem2);

            Order order1 = Order.builder()
                    .member(member1)
                    .orderItems(orderItemList)
                    .address(member1.getAddress())
                    .build();
            em.persist(order1);

            Order order2 = Order.builder()
                    .member(member2)
                    .orderItems(orderItemList)
                    .address(member2.getAddress())
                    .build();
            em.persist(order2);
        }
    }

}
