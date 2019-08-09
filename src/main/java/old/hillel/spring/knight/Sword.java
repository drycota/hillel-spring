package old.hillel.spring.knight;

import lombok.Data;
import org.springframework.stereotype.Component;

@Component
@Data
public class Sword {
    private final String description = "Sharp sword";
}
