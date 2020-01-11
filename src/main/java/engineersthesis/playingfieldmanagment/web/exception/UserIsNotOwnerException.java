package engineersthesis.playingfieldmanagment.web.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.CONFLICT)
public class UserIsNotOwnerException extends RuntimeException {
    public UserIsNotOwnerException() {
        super("User is not owner");
    }
}
