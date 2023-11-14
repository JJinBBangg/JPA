package jpabook.jpashop.request;

import jpabook.jpashop.entity.CategoryItem;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
public class CreateAlbum extends CreateItem{
    private String artist;
    private String etc;

    @Builder
    private CreateAlbum(String name, int price, int stockQuantity, String artist, String etc, String... categories) {
        super(name, price, stockQuantity, categories);
        this.artist = artist;
        this.etc = etc;
    }
}
