package hillel.spring.simpleApp;

import hillel.spring.Apple;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class AppleService {
    private final AppleRepository repo;

    public List<Apple> findHeavyApples() {
        return repo.finfAll().stream()
                .filter(apple -> apple.getWeight() > 100)
                .collect(Collectors.toList());
    }
}
