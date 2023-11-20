package jpabook.jpashop.entity.item;

import jakarta.persistence.Entity;
import jpabook.jpashop.entity.CategoryItem;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Entity
@Getter
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
