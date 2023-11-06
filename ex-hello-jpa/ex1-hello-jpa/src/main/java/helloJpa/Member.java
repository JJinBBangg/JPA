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
    // 실제 FK가 있는 테이블에서 기준이 되는 컬럼을 만들고 onetomany를 사용하는 테이블에서는
    // mappedBy 를 활용하여 검색만 되게하고 값을 입력하는경우는 기준이되는 테이블로 와서 입력
//    @Column(name = "TEAM_ID")
//    private Long teamId;

    public Member() {
    }

    public Team getTeam() {
        return team;
    }

    public void changeTeam(Team team) {
        this.team = team;
        // 기존에 team객체 안에 this객체와 같은 ref 값이 존재할 때 저장하지않는 등 로직이 필요하면 추가
        team.getMembers().add(this);
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

