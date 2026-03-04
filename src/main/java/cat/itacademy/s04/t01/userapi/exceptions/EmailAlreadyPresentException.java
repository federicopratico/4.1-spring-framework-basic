package cat.itacademy.s04.t01.userapi.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.CONFLICT)
public class EmailAlreadyPresentException extends RuntimeException {
    static private final String MESSAGE = "the email already exists";
    public EmailAlreadyPresentException() {
        super(MESSAGE);
    }
    public EmailAlreadyPresentException(String message) {
        super(message);
    }
}
