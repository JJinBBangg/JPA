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
    @Id
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "MEMBER_SEQ_GENERATOR")
    private Long id;

    @Column(name = "name"/*, updatable = false*//*jpa update조작 불가 *//*, insertable = false*//*jpa insert조작 불가 */)
    private String username;

    private Integer age;

    @Enumerated(EnumType.STRING)
    private RoleType roleType;

    @Temporal(TemporalType.TIMESTAMP)
    private Date createdDate;

    @Temporal(TemporalType.TIMESTAMP)
    private Date lastModifiedDate;
    @Lob
    private String description;
    public Member(){

    }
    public Member(Long id, String username, Integer age, RoleType roleType, Date createdDate, Date lastModifiedDate, String description) {
        this.id = id;
        this.username = username;
        this.age = age;
        this.roleType = roleType;
        this.createdDate = createdDate;
        this.lastModifiedDate = lastModifiedDate;
        this.description = description;
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

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public RoleType getRoleType() {
        return roleType;
    }

    public void setRoleType(RoleType roleType) {
        this.roleType = roleType;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    public Date getLastModifiedDate() {
        return lastModifiedDate;
    }

    public void setLastModifiedDate(Date lastModifiedDate) {
        this.lastModifiedDate = lastModifiedDate;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}

