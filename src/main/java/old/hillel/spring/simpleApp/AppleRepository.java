package old.hillel.spring.simpleApp;

import old.hillel.spring.Apple;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class AppleRepository {
    private final List<Apple> apples = List.of(
            new Apple(100, "Green"),
            new Apple(120, "Red"));

    public List<Apple> finfAll(){
        return apples;
    }
}
