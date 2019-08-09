package old.hillel.spring;

import org.junit.Test;

import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.fail;

public class AppleProcessorTest {
    List<Apple> apples = List.of(
            new Apple(100, "Green"),
            new Apple(120, "Red"),
            new Apple(150, "Yellow"),
            new Apple(130, "Green")
    );

    @Test
    public void pickHeaviestTest() throws Exception {

        Optional<Apple> heaviestApple = AppleProcessor.pickHeaviest(apples);

        //  assertThat(heaviestApple.isPresent()).isTrue();

        if (heaviestApple.isPresent()) {
            assertThat(heaviestApple.get().getColor()).isEqualTo("Yellow");
        } else {
            fail("Apple should be present");
        }
    }

    @Test
    public void pickHeavietsFromEmpty() throws Exception {
        Optional<Apple> apple = AppleProcessor.pickHeaviest(Collections.emptyList());

        assertThat(apple.isPresent()).isFalse();
    }

    @Test
    public void filterApplesHeavierThan120() throws Exception {

//        List<Apple> heavy = AppleProcessor.filterHeavy(apples, 120);
//        assertThat(heavy).hasSize(2);

//        ApplePredicate predicate = new ApplePredicate() {
//            @Override
//            public boolean test(Apple apple) {
//                return apple.getWeight() > 120;
//            }
//        };
//        List<Apple> heavy = AppleProcessor.filter(apples, predicate);
//        assertThat(heavy).hasSize(2);

//        ApplePredicate predicate = apple -> apple.getWeight() > 120;
//        List<Apple> heavy = AppleProcessor.filter(apples, predicate);
//        assertThat(heavy).hasSize(2);
//
//        MyPredicate<Apple> predicate = apple -> apple.getWeight() > 120;
//
//        List<Apple> heavy = AppleProcessor.filter(apples, predicate);
//
//        assertThat(heavy).hasSize(2);
//    }

    Predicate<Apple> predicate = apple -> apple.getWeight() > 120;
    List<Apple> heavy = AppleProcessor.filter(apples, predicate);
    assertThat(heavy).hasSize(2);

//    Predicate<Apple> predicate = apple -> apple.getWeight() > 120;
//
//    List<Apple> heavy = AppleProcessor.filter(apples, predicate);
//
//    assertThat(heavy).hasSize(2);
}

    @Test
    public void filterRed() throws Exception {

//        List<Apple> redApples = AppleProcessor.filterByColor(apples, "Red");
//        assertThat(redApples).hasSize(1);

//        ApplePredicate predicate = new ApplePredicate() {
//            @Override
//            public boolean test(Apple apple) {
//                return apple.getColor().equals("Red");
//            }
//        };
//        List<Apple> heavy = AppleProcessor.filter(apples, predicate);
//        assertThat(heavy).hasSize(1);

//        MyPredicate predicate = new MyPredicate<Apple>() {
//            @Override
//            public boolean test(Apple apple) {
//                return apple.getColor().equals("Red");
//            }
//        };
//        List<Apple> redApples = AppleProcessor.filter(apples, predicate);
//        assertThat(redApples).hasSize(1);

        Predicate predicate = new Predicate<Apple>() {
            @Override
            public boolean test(Apple apple) {
                return apple.getColor().equals("Red");
            }
        };
        List<Apple> redApples = AppleProcessor.filter(apples, predicate);
        assertThat(redApples).hasSize(1);
    }


    @Test
    public void filterHeavyAndRed() throws Exception {

        Predicate<Apple> isHeavy = apple -> apple.getWeight() >= 120;
        Predicate<Apple> isRed = apple -> apple.getColor().equals("Red");

        Predicate<Apple> isHeavyAndRed = isHeavy.and(isRed);

        List<Apple> heavyAndRed = AppleProcessor.filter(apples, isHeavyAndRed);

        assertThat(heavyAndRed).hasSize(1);
    }

    @Test
    public void filterUsingStreams() throws Exception {

        Predicate<Apple> isRed = apple -> apple.getColor().equals("Red");

        List<Apple> redApples = apples.stream()
                                      .filter(isRed)
                                      .collect(Collectors.toList());

        assertThat(redApples).hasSize(1);
    }

//    @Test
//    public void mapColors() throws Exception {
//        val colors = apples.stream()
////                           .map(apple -> apple.getColor())
//                           .map(Apple::getColor)
//                           .collect(Collectors.toSet());
//
//        assertThat(colors).contains("Red", "Green", "Yellow");
//    }

    @Test
    public void print() throws Exception {
//        apples.forEach(a -> System.out.println(a));
//        apples.forEach(System.out::println);
//        apples.stream().forEach(System.out::println);
//        apples.stream().peek(System.out::println).collect(Collectors.toList());
        Optional<String> mayBeColor = apples.stream()
                                      .peek(System.out::println)
                                      .map(Apple::getColor)
                                      .peek(System.out::println)
                                      .findFirst();

        String color = mayBeColor.orElse("Green");

    }

//    @Test
//    public void combine() throws Exception {
//        val colorsMoreThan3Letters = apples.stream()
//                                           .map(Apple::getColor)
//                                           .filter(color -> color.length() > 3)
//                                           .peek(System.out::println)
//                                           .collect(Collectors.toList());
//
//        assertThat(colorsMoreThan3Letters).hasSize(3);
//    }

    @Test
    public void groupByColor() throws Exception {
        Map<String, List<Apple>> colorToApples = apples.stream()
                                                       .collect(Collectors.groupingBy(Apple::getColor));

        assertThat(colorToApples).isEqualTo(Map.of(
                "Green", List.of(new Apple(100, "Green"), new Apple(130, "Green")),
                "Red", List.of(new Apple(120, "Red")),
                "Yellow", List.of(new Apple(150, "Yellow"))
        ));
    }

    @Test
    public void statistics() throws Exception {
        IntSummaryStatistics intSummaryStatistics = apples.stream()
                                                          .mapToInt(Apple::getWeight)
                                                          .summaryStatistics();

        System.out.println(intSummaryStatistics);
    }

    @Test
    public void randomNumbers() throws Exception {
        Stream.generate(Math::random)
              .map(Object::toString)
              .limit(100)
              .forEach(System.out::println);
    }
}

class MyClass {
    public static String getColor(Apple apple) {
        return apple.getColor();
    }
}
