package old.hillel.spring.knight;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Component
@Data
public class Horse {
    private final String name;

    public Horse(@Qualifier("horseName") String name) {
        this.name = name;
    }
}
