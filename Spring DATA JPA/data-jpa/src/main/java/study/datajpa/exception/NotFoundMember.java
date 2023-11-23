package study.datajpa.exception;

public class NotFoundMember extends jpaException{

    private static final String MESSAGE = "존재하지 않는 회원입니다.";

    public NotFoundMember() {
        super(MESSAGE);
    }

    public NotFoundMember(String message) {
        super(message);
    }

    public NotFoundMember(String message, Throwable cause) {
        super(message, cause);
    }
}
