package study.datajpa.response;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class MemberResponse {

    private String name;
    private int age;

    @Builder
    private MemberResponse(String name, int age) {
        this.name = name;
        this.age = age;
    }
}
