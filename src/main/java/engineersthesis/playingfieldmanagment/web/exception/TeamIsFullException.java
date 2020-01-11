package engineersthesis.playingfieldmanagment.web.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.CONFLICT)

public class TeamIsFullException extends RuntimeException {
    public TeamIsFullException() {
        super("You cant join to team because is full");
    }
}
