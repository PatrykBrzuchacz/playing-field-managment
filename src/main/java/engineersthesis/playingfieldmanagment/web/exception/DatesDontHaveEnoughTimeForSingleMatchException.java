package engineersthesis.playingfieldmanagment.web.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.CONFLICT)
public class DatesDontHaveEnoughTimeForSingleMatchException extends RuntimeException {
    public DatesDontHaveEnoughTimeForSingleMatchException() {
        super("Dates dont have enough time for single match");
    }
}