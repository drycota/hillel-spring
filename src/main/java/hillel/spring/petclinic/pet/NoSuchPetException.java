package hillel.spring.petclinic.pet;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "no such pet")
public class NoSuchPetException extends RuntimeException {
}
