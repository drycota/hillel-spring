package homework2;

import lombok.Data;

import java.util.List;

@Data
public class Restaraunt {
    private final List<Dish> menu;
}

@Data
class Dish {
    private final String name;
    private final Integer calories;
    private final Boolean isBio;
    private final DishType type;
}

enum DishType {
    BEEF,
    CHICKEN,
    VEGETABLES
}
