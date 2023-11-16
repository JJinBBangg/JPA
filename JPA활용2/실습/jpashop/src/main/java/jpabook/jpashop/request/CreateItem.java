package jpabook.jpashop.request;


import jpabook.jpashop.entity.CategoryItem;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

import static lombok.AccessLevel.*;

@Getter
@NoArgsConstructor(access = PROTECTED)
@AllArgsConstructor(access = PROTECTED)
public abstract class CreateItem {
    private String name;
    private int price;
    private int stockQuantity;
    private String[] category;
    // 상속받은 객체에서만 생성자 사용가능하도록 access level 을 PROTECTED
    // 상속받은 객체에서도 Setter 없이 Builder 만 사용하도록 통제하면 안전하게 사용가능
}
