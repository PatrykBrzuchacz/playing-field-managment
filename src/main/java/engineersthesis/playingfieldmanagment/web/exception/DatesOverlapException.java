package engineersthesis.playingfieldmanagment.web.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class DatesOverlapException extends RuntimeException {
    public DatesOverlapException() {
        super("Date cant overlap on date in base");
    }
}

