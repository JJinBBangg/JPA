package jpabook.jpashop.exception;

public class AlreadyDeliveryException extends ShopException {

    private static final String MESSAGE = "이미 배송이 출발한 상품입니다.";

    public AlreadyDeliveryException(){
        super(MESSAGE);
    }

    public AlreadyDeliveryException(String message) {
        super(message);
    }

    public AlreadyDeliveryException(String message, Throwable cause) {
        super(message, cause);
    }

    @Override
    public int getStatusCode() {
        return 0;
    }
}
