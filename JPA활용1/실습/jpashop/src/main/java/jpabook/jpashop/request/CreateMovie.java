package jpabook.jpashop.request;

import lombok.Builder;
import lombok.Getter;

@Getter
public class CreateMovie extends CreateItem{
    private String director;
    private String actor;
    @Builder
    private CreateMovie(String name, int price, int stockQuantity, String director, String actor, String... categories) {
        super(name, price, stockQuantity, categories);
        this.director = director;
        this.actor = actor;
    }
}
