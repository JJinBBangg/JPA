package jpabook.jpashop.response;

import jpabook.jpashop.entity.item.Album;
import jpabook.jpashop.entity.item.Book;
import jpabook.jpashop.entity.item.Item;
import jpabook.jpashop.entity.item.Movie;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.Hibernate;

@Getter
@Slf4j
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ItemResponse {
    private Long id;
    private String name;
    private int price;
    private int stockQuantity;
    private String actor;
    private String artist;
    private String author;
    private String director;
    private String etc;
    private String isbn;

    @Builder
    private ItemResponse(Item item) {
        this.id = item.getId();
        this.price = item.getPrice();
        this.name = item.getName();
        this.stockQuantity = item.getStockQuantity();
        // 아래처럼 사용하게 된 이유 : fetch join 을 사용하여 가져올때는 처음부터 가져오기때문에
        // 실제 객체로 반환 되어 문제가 없었으나 Lazy 로딩으로 객체를 찾아온 경우 proxy 타입의 객체로
        // 반환되게 되는데 이때 실제 상속받은 entity 와 instanceOf로 비교 할 시 타입캐스팅 에러발생

        //item 이 초기화 되어있는지 확인
        if (Hibernate.isInitialized(item)) {
            // item 객체가 HibernateProxy 객체이기 때문에
            // 실제 객체와 비교하기 위해서는
            // unProxy 한 후 사용하여 실제 객체와 비교
            Object unProxiedItem = Hibernate.unproxy(item);
            if (unProxiedItem instanceof Book) {
                this.isbn = ((Book) unProxiedItem).getIsbn();
                this.author = ((Book) unProxiedItem).getAuthor();

            } else if (unProxiedItem instanceof Movie) {
                this.actor = ((Movie) unProxiedItem).getActor();
                this.director = ((Movie) unProxiedItem).getDirector();

            } else if (unProxiedItem instanceof Album) {
                this.artist = ((Album) unProxiedItem).getArtist();
                this.etc = ((Album) unProxiedItem).getEtc();
            }
        }
    }
}
//        if (item instanceof Book) {
//            this.isbn = ((Book) item).getIsbn();
//            this.author = ((Book) item).getAuthor();
//        }
//        if (item instanceof Movie) {
//            this.actor = ((Movie) item).getActor();
//            this.director = ((Movie) item).getDirector();
//        }
//        if (item instanceof Album) {
//            this.artist = ((Album) item).getArtist();
//            this.etc = ((Album) item).getEtc();
//        }
//
//        if (item.getDtype().equals("Book")) {
//            this.isbn = ((Book) item).getIsbn();
//            this.author = ((Book) item).getAuthor();
//        }
//        if(item.getDtype().equals("Movie")){
//            this.actor = ((Movie)item).getActor();
//            this.director = ((Movie)item).getDirector();
//        }
//        if(item.getDtype().equals("Album")) {
//            this.artist = ((Album) item).getArtist();
//            this.etc = ((Album) item).getEtc();
//        }

//    @Builder
//    public ItemResponse(Long id, String name, int price, int stockQuantity, String actor, String artist, String author, String director, String etc, String isbn) {
//        this.id = id;
//        this.name = name;
//        this.price = price;
//        this.stockQuantity = stockQuantity;
//        if (item instanceof Book) {
//            this.isbn = ((Book) item).getIsbn();
//            this.author = ((Book) item).getAuthor();
//        }
//        if(item instanceof Movie){
//            this.actor = ((Movie)item).getActor();
//            this.director = ((Movie)item).getDirector();
//        }
//        if(item instanceof Album) {
//            this.artist = ((Album) item).getArtist();
//            this.etc = ((Album) item).getEtc();
//        }
//    }
