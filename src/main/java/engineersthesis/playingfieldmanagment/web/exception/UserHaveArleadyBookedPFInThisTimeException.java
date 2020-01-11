package engineersthesis.playingfieldmanagment.web.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.CONFLICT)
public class UserHaveArleadyBookedPFInThisTimeException extends RuntimeException {
    public UserHaveArleadyBookedPFInThisTimeException() {
        super("User have arleady booked playing field in this time");
    }
}
