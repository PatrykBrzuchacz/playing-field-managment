package engineersthesis.playingfieldmanagment.web.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.CONFLICT)

public class UserIsActuallyInThisTeamException extends RuntimeException {
    public UserIsActuallyInThisTeamException() {
        super("userDto is actually in this team");
    }
}
