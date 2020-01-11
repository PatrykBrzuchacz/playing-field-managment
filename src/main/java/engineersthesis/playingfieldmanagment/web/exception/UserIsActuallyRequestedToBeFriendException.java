package engineersthesis.playingfieldmanagment.web.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.CONFLICT)
public class UserIsActuallyRequestedToBeFriendException extends RuntimeException {
    public UserIsActuallyRequestedToBeFriendException() {
        super("User Is Actually Requested To Be Friend Exception");
    }
}
