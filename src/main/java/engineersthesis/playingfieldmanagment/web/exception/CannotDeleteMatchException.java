package engineersthesis.playingfieldmanagment.web.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.CONFLICT)
public class CannotDeleteMatchException extends RuntimeException {
    public CannotDeleteMatchException() {
        super("Cannot delete match because is to late");
    }
}
