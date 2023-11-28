package study.querydsl.response;

import com.querydsl.core.annotations.QueryProjection;
import lombok.*;

@Getter
@NoArgsConstructor
@ToString
public class MemberResponse {

    private String username;
    private Integer age;

    @QueryProjection
    @Builder
    public MemberResponse(String username, Integer age) {
        this.username = username;
        this.age = age;
    }
}
