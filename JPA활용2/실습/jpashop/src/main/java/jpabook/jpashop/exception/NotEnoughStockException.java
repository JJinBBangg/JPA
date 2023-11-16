package jpabook.jpashop.exception;

public class NotEnoughStockException extends ShopException{
    private static final String MESSAGE = "재고수량이 부족합니다.";

    public NotEnoughStockException() {
        super(MESSAGE);
    }
    public NotEnoughStockException(String message) {
        super(message);
    }

    public NotEnoughStockException(String message, Throwable cause) {
        super(message, cause);
    }

    @Override
    public int getStatusCode() {
        return 400;
    }
}
