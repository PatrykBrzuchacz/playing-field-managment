package engineersthesis.playingfieldmanagment.web.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.CONFLICT)
public class UserIsWorkerException extends RuntimeException {
    public UserIsWorkerException() {
        super("User is actually working on playing field or his requests is waiting for result");
    }
}
