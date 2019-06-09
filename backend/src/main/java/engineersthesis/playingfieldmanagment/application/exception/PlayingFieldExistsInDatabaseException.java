package engineersthesis.playingfieldmanagment.application.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.CONFLICT)
public class PlayingFieldExistsInDatabaseException extends RuntimeException {
    public PlayingFieldExistsInDatabaseException(){
        super("Playing field exists in database");
    }
}
