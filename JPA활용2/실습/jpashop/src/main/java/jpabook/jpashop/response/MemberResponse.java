package jpabook.jpashop.response;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import static lombok.AccessLevel.*;

@Getter
@NoArgsConstructor(access = PROTECTED)
public class MemberResponse {
    private Long id;
    private String name;

    @Builder
    private MemberResponse(Long id, String name){
        this.id = id;
        this.name =name;
    }

}
