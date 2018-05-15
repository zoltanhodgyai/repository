package ro.msg.learning.shop.exception;

public class NoStocksFoundException extends RuntimeException {
    public NoStocksFoundException(String text) {
        super(text);
    }
}
