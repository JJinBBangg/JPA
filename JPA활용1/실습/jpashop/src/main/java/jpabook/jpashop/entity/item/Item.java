package jpabook.jpashop.entity.item;

import jakarta.persistence.*;
import jpabook.jpashop.entity.CategoryItem;
import jpabook.jpashop.exception.NotEnoughStockException;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

import static jakarta.persistence.InheritanceType.*;

@Entity
@Inheritance(strategy = SINGLE_TABLE)
@Getter
public abstract class Item {
    @Id @GeneratedValue
    private Long id;
    private String name;
    private int price;
    private int stockQuantity;

    @OneToMany(mappedBy = "item")
    private List<CategoryItem> categories = new ArrayList<>();

    protected Item() {
    }
    protected Item(String name, int price, int stockQuantity, List<CategoryItem> categories) {
        this.name = name;
        this.price = price;
        this.stockQuantity = stockQuantity;
        this.categories = categories;
    }

    public void addStock(int quantity){
        this.stockQuantity += quantity;
    }
    public void removeStock(int quantity){
        if(this.stockQuantity - quantity < 0) throw new NotEnoughStockException();
        this.stockQuantity -= quantity;
    }
}
