package helloJpa;

import jakarta.persistence.*;

import java.util.Date;

@Entity
@SequenceGenerator(
        name = "MEMBER_SEQ_GENERATOR", // spring 에서 매칭시킬 시퀀스 네임
        sequenceName = "MEMBER_SEQ", // 매핑할 DB상의 시퀀스 네임
        initialValue = 1, allocationSize = 50)
// allocationSize 는 여러 서버가 작동하더라도 각 서버가 할당받은 시퀀스를 확보하기때문에
// 동시성 문제도 없어짐
// 50개 단위로 시퀀스값을 가져온다
// 예)현재5352 = 5353~5402까지
// TABLE 도 같은 전략으로 가져오기때문에 문제없음
// hibernate5 버전 이후부터는 default 값이 TABLE
public class Member {
    @Id @GeneratedValue
    @Column(name = "MEMBER_NAME")
    private Long id;

    @Column(name = "USERNAME")
    private String username;

    @ManyToOne
    @JoinColumn(name ="TEAM_ID")
    private Team team;
//    @Column(name = "TEAM_ID")
//    private Long teamId;

    public Member() {
    }

    public Team getTeam() {
        return team;
    }

    public void setTeam(Team team) {
        this.team = team;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}

