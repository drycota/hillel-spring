package hillel.spring.knight;

import lombok.Data;
import org.springframework.stereotype.Component;

@Component
@Data
public class Armor {
    private final String description = "Shiny armor";
}
