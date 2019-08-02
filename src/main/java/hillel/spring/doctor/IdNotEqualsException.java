package hillel.spring.doctor;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST, reason = "id should not be changed")
public class IdNotEqualsException extends RuntimeException{
}
