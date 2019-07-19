package hillel.spring.homework3;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;
import java.util.Map;

@AllArgsConstructor
@Repository
public class greetingRepository {
    private final Map<Language, String> greeting = Map.of(
            Language.ENGLISH, "Hello",
            Language.ITALIAN, "Сiao",
            Language.FRENCH, "Salut");

    public Map<Language, String> greeting() {
        return greeting;
    }
}
