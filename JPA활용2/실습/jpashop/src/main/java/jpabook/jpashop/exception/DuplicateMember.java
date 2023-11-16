package jpabook.jpashop.exception;

import lombok.Getter;

@Getter
public class DuplicateMember extends ShopException {

    private static final String MESSAGE = "중복된 이름입니다.";

    public DuplicateMember() {
        super(MESSAGE);
    }
    public DuplicateMember(String message){
        super(message);
    }
    @Override
    public int getStatusCode() {
        return 409;
    }

}
