package engineersthesis.playingfieldmanagment.web.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.CONFLICT)
public class WrongReceivedTimesException extends RuntimeException {
    public WrongReceivedTimesException() {
        super("Opening cannot be after closing");
    }
}
