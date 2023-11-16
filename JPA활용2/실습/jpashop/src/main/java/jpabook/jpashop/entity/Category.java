package jpabook.jpashop.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

import static jakarta.persistence.FetchType.*;
import static lombok.AccessLevel.*;

@Entity
@NoArgsConstructor(access = PROTECTED)
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

    @Builder
    private Category(Long id, String name, List<CategoryItem> items, List<Category> child, Category parent) {
        this.id = id;
        this.name = name;
        this.items = items;
        this.child = child;
        this.parent = parent;
    }
}
