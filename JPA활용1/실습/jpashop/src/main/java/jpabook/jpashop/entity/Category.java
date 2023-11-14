package jpabook.jpashop.entity;

import jakarta.persistence.*;
import lombok.Builder;

import java.util.ArrayList;
import java.util.List;

import static jakarta.persistence.FetchType.*;

@Entity
public class Category {

    @Id @GeneratedValue
    @Column(name = "CATEGORY_ID")
    private Long id;

    private String name;

    @OneToMany(mappedBy = "category")
    private List<CategoryItem> items = new ArrayList<>();


    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "PARENT_ID")
    private Category parent;

    @OneToMany(mappedBy = "parent")
    private List<Category> child = new ArrayList<>();

    protected Category() {
    }
    @Builder
    private Category(Long id, String name, List<CategoryItem> items, List<Category> child, Category parent) {
        this.id = id;
        this.name = name;
        this.items = items;
        this.child = child;
        this.parent = parent;
    }
}
