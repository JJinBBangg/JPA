package study.querydsl.response;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import study.querydsl.entity.QMember;

@Getter
@NoArgsConstructor
@ToString
public class MemberTeamResponse {
    private Long memberId;
    private String username;
    private Integer age;
    private Long teamId;
    private String teamName;

    @Builder
    @QueryProjection
    public MemberTeamResponse(Long memberId, String username, Integer age, Long teamId, String teamName) {
        this.memberId = memberId;
        this.username = username;
        this.age = age;
        this.teamId = teamId;
        this.teamName = teamName;
    }
}
