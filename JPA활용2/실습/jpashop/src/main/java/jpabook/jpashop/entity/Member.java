package jpabook.jpashop.entity;

import jakarta.persistence.*;
import jpabook.jpashop.request.UpdateMember;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

import static lombok.AccessLevel.*;

@Entity
@Getter
@NoArgsConstructor(access = PROTECTED)
public class Member {
    @Id
    @GeneratedValue
    @Column(name = "MEMBER_ID")
    private Long id;

    private String name;

    @Embedded
    private Address address;

    @OneToMany(mappedBy = "member")
    private List<Order> orders = new ArrayList<>();

    @Builder
    private Member(String name, Address address, List<Order> orders) {
        this.name = name;
        this.address = address;
        this.orders = (orders == null ? new ArrayList<>() : orders);
    }

    public void updateMember(UpdateMember updateMember){
        this.name = (updateMember.getName() == null ?
                this.name : updateMember.getName());

        this.address = (updateMember.getZipcode() == null ?
                this.address : Address.builder()
                                .city(updateMember.getCity())
                                .street(updateMember.getStreet())
                                .zipcode(updateMember.getZipcode())
                                .build());
    }

}
