package jpabook.jpashop.request;

import lombok.Builder;
import lombok.Getter;

@Getter
public class CreateBook extends CreateItem{
    private String author;
    private String isbn;

    @Builder
    private CreateBook(String name, int price, int stockQuantity, String author, String isbn, String... categories) {
        super(name, price, stockQuantity, categories);
        this.author = author;
        this.isbn = isbn;
    }
}
