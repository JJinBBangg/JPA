package study.querydsl.response;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@NoArgsConstructor
@ToString
public class UserResponse {

    private String name;
    private Integer age;

    @Builder
    public UserResponse(String name, Integer age) {
        this.name = name;
        this.age = age;
    }
}
