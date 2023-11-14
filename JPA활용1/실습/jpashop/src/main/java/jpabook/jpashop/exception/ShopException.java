package jpabook.jpashop.exception;

import java.util.HashMap;
import java.util.Map;

public abstract class ShopException extends RuntimeException{
    public ShopException(String message) { super(message); }

    public ShopException(String message, Throwable cause) {
        super(message, cause);
    }
    public abstract int getStatusCode();

}
