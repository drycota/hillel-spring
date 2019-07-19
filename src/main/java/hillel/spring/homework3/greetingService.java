package hillel.spring.homework3;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.Random;

@AllArgsConstructor
@Service
public class greetingService {
    private final greetingRepository repository;

    private String random(){
        Random r = new Random();
        return repository.greeting().get(Language.values()[(r.nextInt(repository.greeting().size()))]);
    }

    public String greetingEn(){
        return repository.greeting().get(Language.ENGLISH);
    }

    public String greetingIt(){
        return repository.greeting().get(Language.ITALIAN);
    }

    public String greetingFr(){
        return repository.greeting().get(Language.FRENCH);
    }

    public String greetingRandom(){
        return random();
    }
}
