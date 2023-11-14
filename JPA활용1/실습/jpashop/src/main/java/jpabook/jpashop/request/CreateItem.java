package jpabook.jpashop.request;


import jpabook.jpashop.entity.CategoryItem;
import lombok.Getter;

import java.util.List;

@Getter
public abstract class CreateItem {
    private String name;
    private int price;
    private int stockQuantity;
    private String[] category;

    protected CreateItem() {
    }

    protected CreateItem(String name, int price, int stockQuantity, String... category) {
        this.name = name;
        this.price = price;
        this.stockQuantity = stockQuantity;
        this.category = category;
    }
}
