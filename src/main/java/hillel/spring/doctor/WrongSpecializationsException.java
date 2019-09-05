package hillel.spring.doctor;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST, reason = "specialization not found")
public class WrongSpecializationsException extends RuntimeException{
}