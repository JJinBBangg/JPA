package helloJpa;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
public class Team {
    @Id @GeneratedValue
    @Column(name = "TEAM_ID")
    private Long id;
    private String name;

    @OneToMany(mappedBy = "team")// mappedBy의 경우에는 getter 만 지정
    //값을 입력하는 경우는 실제 FK가 있는 Entity 에서 입력해야함
    private List<Member> members = new ArrayList<>();

    public Team() {
    }

    public List<Member> getMembers() {
        return members;
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
