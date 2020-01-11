package engineersthesis.playingfieldmanagment.web.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.CONFLICT)
public class DateCannotBeBeforeCurrentDateException extends RuntimeException {
    public DateCannotBeBeforeCurrentDateException() {
        super("Date cannot be before current date");
    }
}
