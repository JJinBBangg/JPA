package jpabook.jpashop;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
public class Member {
    @Id @GeneratedValue
    private Long id;

    private String name;

    public Member() {
    }
    @Builder
    public Member(Long id, String name) {
        this.id = id;
        this.name = name;
    }
}
