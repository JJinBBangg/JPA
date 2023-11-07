package jpabook.jpashop.domain;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
public class Category extends BaseEntity{
    @Id
    @GeneratedValue
    @Column(name = "CATEGORY_ID")
    private Long id;

    @OneToMany(mappedBy = "category")
    private List<CategoryItem> categoryItems= new ArrayList<>();

    @ManyToOne
    @JoinColumn(name = "PARENT_ID")
    private Category parents;

    @OneToMany(mappedBy = "parents")
    private List<Category> child = new ArrayList<>();


}
