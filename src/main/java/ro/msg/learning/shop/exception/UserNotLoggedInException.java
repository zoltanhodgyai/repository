package ro.msg.learning.shop.exception;

public class UserNotLoggedInException extends RuntimeException {

    public UserNotLoggedInException(String text) {

        super(text);
    }
}
