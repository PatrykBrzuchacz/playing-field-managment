package engineersthesis.playingfieldmanagment.web.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.CONFLICT)
public class PFAvailabilityCannotBeDeletedBecauseIsInUsedException extends RuntimeException {
    public PFAvailabilityCannotBeDeletedBecauseIsInUsedException() {
        super("Playing field cannot be deleted becase is arleady booked");
    }
}
