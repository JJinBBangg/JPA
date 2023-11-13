package jpabook.jpashop.domain.item;

import jakarta.persistence.Entity;
import jpabook.jpashop.domain.CategoryItem;
import lombok.Builder;

import java.util.List;

@Entity
public class Movie extends Item{
    private String director;
    private String actor;

    protected Movie(){}
    @Builder
    private Movie(String name, int price, int stockQuantity, List<CategoryItem> categories, String director, String actor) {
        super(name, price, stockQuantity, categories);
        this.director = director;
        this.actor = actor;
    }
}
