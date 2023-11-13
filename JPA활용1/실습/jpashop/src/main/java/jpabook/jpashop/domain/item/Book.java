package jpabook.jpashop.domain.item;

import jakarta.persistence.Entity;
import jpabook.jpashop.domain.CategoryItem;
import lombok.Builder;

import java.util.List;

@Entity
public class Book extends Item{
    private String author;
    private String isbn;

    protected Book() {
    }

    @Builder
    private Book(String name, int price, int stockQuantity, List<CategoryItem> categories, String author, String isbn) {
        super(name, price, stockQuantity, categories);
        this.author = author;
        this.isbn = isbn;
    }
}
