package jpabook.jpashop.entity.item;

import jakarta.persistence.Entity;
import jpabook.jpashop.entity.CategoryItem;
import lombok.Builder;

import java.util.List;

@Entity
public class Album extends Item{
    private String artist;
    private String etc;

    protected Album(){

    }
    @Builder
    private Album(String name, int price, int stockQuantity, List<CategoryItem> categories, String artist, String etc) {
        super(name, price, stockQuantity, categories);
        this.artist = artist;
        this.etc = etc;
    }
}
