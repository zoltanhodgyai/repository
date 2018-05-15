package ro.msg.learning.shop.exception;

public class UsernameAlreadyInUseException extends RuntimeException {

    public UsernameAlreadyInUseException(String text) {
        super(text);
    }
}
