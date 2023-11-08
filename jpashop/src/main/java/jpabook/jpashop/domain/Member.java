package jpabook.jpashop.domain;

import jakarta.persistence.*;
import org.hibernate.annotations.CollectionId;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
public class Member{
    @Id
    @GeneratedValue
    @Column(name = "MEMBER_ID")
    private Long id;
    private String name;
    @OneToMany(mappedBy = "member")
    private List<Order> orders = new ArrayList<>();
    @ManyToOne(fetch = FetchType.LAZY) // team을 사용할 때만 조회
    @JoinColumn(name = "TEAM_ID")
    private Team team;
    @Embedded
    private Address address;
//    @Embedded
//    private Period workPeriod;


//    @ElementCollection
//    @CollectionTable(name = "ADDRESS",
//            joinColumns = @JoinColumn(name = "MEMBER_ID")
//            )
//    private List<Address> addressList = new ArrayList<>();
//
//    @ElementCollection
//    @CollectionTable(name = "FAVORITE_FOOD",
//            joinColumns = @JoinColumn(name = "MEMBER_ID"))
//    @Column(name = "FOOD_NAME")
//    private Set<String> favoriteFood = new HashSet<>();


    public Address getHomeAddress() {
        return address;
    }

    public void setHomeAddress(Address homeAddress) {
        this.address = homeAddress;
    }

    public Team getTeam() {
        return team;
    }

    public void setTeam(Team team) {
        this.team = team;
    }

    public List<Order> getOrders() {
        return orders;
    }

    public void setOrders(List<Order> orders) {
        this.orders = orders;
    }

    public Member() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}