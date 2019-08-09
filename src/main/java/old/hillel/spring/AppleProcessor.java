package old.hillel.spring;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;

import lombok.val;

public class AppleProcessor {

//    public static Apple pickHeaviest(List<Apple> apples) {
//        Apple heaviest = null;
//        for (var apple : apples) {
//            if (heaviest == null || heaviest.getWeight() < apple.getWeight()) {
//                heaviest = apple;
//            }
//        }
//        return heaviest;
//    }

    public static Optional<Apple> pickHeaviest(List<Apple> apples) {
        Apple heaviest = null;
        for (var apple : apples) {
            if (heaviest == null || heaviest.getWeight() < apple.getWeight()) {
                heaviest = apple;
            }
        }
        return Optional.ofNullable(heaviest);
    }

//    public static List<Apple> filterHeavy(List<Apple> apples, Integer weight) {
//        val heavyApples = new ArrayList<Apple>();
//        for(Apple apple: apples){
//            if(apple.getWeight() > weight)
//                heavyApples.add(apple);
//        }
//        return heavyApples;
//    }
//
//    public static List<Apple> filterByColor(List<Apple> apples, String color) {
//        val appropriateApples = new ArrayList<Apple>();
//        for(Apple apple: apples){
//            if(apple.getColor().equals(color))
//                appropriateApples.add(apple);
//        }
//        return appropriateApples;
//    }

//    public static List<Apple> filter(List<Apple> apples, ApplePredicate predicate) {
//        val appropriateApples = new ArrayList<Apple>();
//        for(Apple apple: apples){
//            if(predicate.test(apple))
//                appropriateApples.add(apple);
//        }
//        return appropriateApples;
//    }

//    public static <T> List<T> filter(List<T> objects, MyPredicate<T> predicate) {
//        val appropriateApples = new ArrayList<T>();
//        for (T thing : objects) {
//            if (predicate.test(thing)) {
//                appropriateApples.add(thing);
//            }
//        }
//        return appropriateApples;
//    }

    public static <T> List<T> filter(List<T> objects, Predicate<T> predicate) {
        val appropriateApples = new ArrayList<T>();
        for (T thing : objects) {
            if (predicate.test(thing)) {
                appropriateApples.add(thing);
            }
        }
        return appropriateApples;
    }


    Optional<String> f(){
        if(Math.random() > 0.5){
            return Optional.of("Prize  here");
        }else {
            return Optional.empty();
        }
    }

}

//@FunctionalInterface
//interface ApplePredicate{
//    boolean test(Apple apple);
//}

//@FunctionalInterface
//interface MyPredicate<T>{
//    boolean test(T thing);
//}
