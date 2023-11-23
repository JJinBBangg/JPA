package study.datajpa.exception;

public abstract class jpaException extends RuntimeException {

    public jpaException() {
        super();
    }

    public jpaException(String message) {
        super(message);
    }

    public jpaException(String message, Throwable cause) {
        super(message, cause);
    }

    public jpaException(Throwable cause) {
        super(cause);
    }

    protected jpaException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

    public abstract int getStatusCode();
}
