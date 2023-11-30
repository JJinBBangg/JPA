package study.querydsl.response;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.lang.Nullable;

@Data
@NoArgsConstructor
public class MemberSearchCondition {
    private String username;
    private String teamName;
    private Integer ageGoe;
    private Integer ageLoe;
    private String usernameLike;

    @Builder
    public MemberSearchCondition(String username, String teamName, Integer ageGoe, Integer ageLoe, String usernameLike) {
        this.username = username;
        this.teamName = teamName;
        this.ageGoe = ageGoe;
        this.ageLoe = ageLoe;
        this.usernameLike = usernameLike;
    }
}
