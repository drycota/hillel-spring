package homework2;

import org.junit.Test;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;

public class RestarauntProcessorTest {
    Restaraunt restaraunt = new Restaraunt(List.of(new Dish("Beef Stake", 271, true, DishType.BEEF),
            new Dish("Chicken kebab", 142, true, DishType.CHICKEN),
            new Dish("Mexican beans", 185, false, DishType.VEGETABLES),
            new Dish("Beef with oranges", 260, true, DishType.BEEF),
            new Dish("Cherry Duck", 220, true, DishType.CHICKEN),
            new Dish("Rooster in wine", 202, false, DishType.CHICKEN),
            new Dish("Italian beef", 269, true, DishType.BEEF),
            new Dish("Stuffed Eggplant", 137, true, DishType.VEGETABLES),
            new Dish("Chicken fricassee", 138, true, DishType.CHICKEN),
            new Dish("Baked apples", 96, true, DishType.VEGETABLES),
            new Dish("Dandelion salad", 81, true, DishType.VEGETABLES),
            new Dish("Beef fricassee", 292, false, DishType.BEEF)));

    @Test
    public void namesDishes() {
        List<String> names = RestarauntProcessor.namesDishes(restaraunt);
        System.out.println(names);
        assertThat(names).hasSize(12);
    }
    @Test
    public void lowСalorieDishes() {
        Optional<Restaraunt> restarauntLowCalories = RestarauntProcessor.filterСalories(restaraunt, 100);
        if (restarauntLowCalories.isPresent()) {
            System.out.println(restarauntLowCalories.get().getMenu());
            assertThat(restarauntLowCalories.get().getMenu()).hasSize(2);
        } else {
            fail("Restaraunt should be present");
        }
    }
    @Test
    public void topNutritious(){
        List<Dish> topNutritious = RestarauntProcessor.topNutritious(restaraunt);
        System.out.println(topNutritious);
        assertThat(topNutritious.get(0).getCalories()).isEqualTo(292);
    }
    @Test
    public void sortByTypeAndByNameTest(){
        RestarauntProcessor.sortByTypeAndByName(restaraunt);
        assertThat(restaraunt.getMenu()).hasSize(12);
    }
    @Test
    public void getAverageCalories() {
        Map<DishType, Double> map = RestarauntProcessor.getAverageCalories(restaraunt);
        assertThat(map).hasSize(3);
    }
    @Test
    public void groupByDishTypeTest() {
        Map<DishType, List<Dish>> map = RestarauntProcessor.groupByDishType(restaraunt);
        assertThat(map).hasSize(3);
    }
    @Test
    public void groupByBioToNameTest() {
        Map<DishType, List<String>> map = RestarauntProcessor.groupByBioName(restaraunt);
        assertThat(map).hasSize(3);
    }
}
