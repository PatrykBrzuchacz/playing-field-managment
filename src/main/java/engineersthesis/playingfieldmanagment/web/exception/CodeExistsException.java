package engineersthesis.playingfieldmanagment.web.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.CONFLICT)
public class CodeExistsException  extends RuntimeException {
    public CodeExistsException() {
        super("Code actually is filled");
    }
}
