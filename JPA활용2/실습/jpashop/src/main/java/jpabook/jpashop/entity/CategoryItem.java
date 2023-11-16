package jpabook.jpashop.entity;

import jakarta.persistence.*;
import jpabook.jpashop.entity.item.Item;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import static lombok.AccessLevel.*;

@Entity
@Getter
@NoArgsConstructor(access = PROTECTED)
public class CategoryItem {

    @Id @GeneratedValue
    @Column(name = "CATEGORY_ITEM_ID")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ITEM_ID")
    private Item item;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CATEGORY_ID")
    private Category category;

    @Builder
    private CategoryItem(Long id, Item item, Category category) {
        this.id = id;
        this.item = item;
        this.category = category;
    }
}
