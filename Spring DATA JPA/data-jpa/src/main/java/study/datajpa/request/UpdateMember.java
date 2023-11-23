package study.datajpa.request;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import study.datajpa.entity.Team;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class UpdateMember {

    private Long id;
    private String name;
    private int age;
    private Long teamId;
    private Team team;

    @Builder
    public UpdateMember(Long id, String name, int age, Long teamId, Team team) {
        this.id = id;
        this.name = name;
        this.age = age;
        this.teamId = teamId;
        this.team = team;
    }
}
