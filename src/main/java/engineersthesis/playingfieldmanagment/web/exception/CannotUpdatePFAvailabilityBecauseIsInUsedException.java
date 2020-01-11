package engineersthesis.playingfieldmanagment.web.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.CONFLICT)
public class CannotUpdatePFAvailabilityBecauseIsInUsedException extends RuntimeException {
    public CannotUpdatePFAvailabilityBecauseIsInUsedException() {
        super("Cannot update playing field availability because is actually in used");
    }
}
