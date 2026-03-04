package cat.itacademy.s04.t01.userapi.exceptions;

public class EmailAlreadyPresentException extends RuntimeException {
    static private final String MESSAGE = "the email already exists";
    public EmailAlreadyPresentException() {
        super(MESSAGE);
    }
    public EmailAlreadyPresentException(String message) {
        super(message);
    }
}
