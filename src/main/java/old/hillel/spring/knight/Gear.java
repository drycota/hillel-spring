package old.hillel.spring.knight;

import lombok.Data;
import org.springframework.stereotype.Component;

@Component
@Data
public class Gear {
    private final Sword sword;
    private final Armor armor;
}
