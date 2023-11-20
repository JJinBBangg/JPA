package jpabook.jpashop.response;

import jpabook.jpashop.entity.item.Album;
import jpabook.jpashop.entity.item.Book;
import jpabook.jpashop.entity.item.Item;
import jpabook.jpashop.entity.item.Movie;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
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
        if (item instanceof Book) {
            this.isbn = ((Book) item).getIsbn();
            this.author = ((Book) item).getAuthor();
        }
        if(item instanceof Movie){
            this.actor = ((Movie)item).getActor();
            this.director = ((Movie)item).getDirector();
        }
        if(item instanceof Album) {
            this.artist = ((Album) item).getArtist();
            this.etc = ((Album) item).getEtc();
        }
    }
}

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
