package jpabook.jpashop.response;

import lombok.Builder;
import lombok.Getter;

@Getter
public class ItemResponse {
    private Long id;
    private String name;
    private int price;
    private int count;
    private int stockQuantity;
    private String dType;
    private String actor;
    private String artist;
    private String author;
    private String director;
    private String etc;
    private String isbn;

    @Builder
    public ItemResponse(Long id, String name, int price,int count, int stockQuantity, String dType, String actor, String artist, String author, String director, String etc, String isbn) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.count = count;
        this.stockQuantity = stockQuantity;
        this.dType = dType;
        this.actor = actor;
        this.artist = artist;
        this.author = author;
        this.director = director;
        this.etc = etc;
        this.isbn = isbn;
    }
}
