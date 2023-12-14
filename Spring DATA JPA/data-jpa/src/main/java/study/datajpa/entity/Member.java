package study.datajpa.entity;

import jakarta.persistence.*;
import lombok.*;
import study.datajpa.request.UpdateMember;


@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@NamedQuery(
        name = "Member.findByName",
        query = "select m from Member m where m.name = :name"
)
@ToString(of = {"id", "name", "age"})
@TableGenerator(name = "member", allocationSize = 100)
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "member_id")
    private Long id;

    private String name;

    private int age;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "team_id")
    private Team team;

    @Builder
    private Member(Long id, String name, int age, Team team) {
        this.id = id;
        this.name = name;
        this.age = age;
        if (team != null) changeTeam(team);
    }

    public void updateMember(UpdateMember updateMember) {
        name = updateMember.getName() == null ? name : updateMember.getName();
        age = updateMember.getAge() == null ? age : updateMember.getAge();
        if (updateMember.getTeam() != null) changeTeam(team);
    }

    private void changeTeam(Team team) {
        this.team = team;
        team.getMembers().add(this);
    }


}
