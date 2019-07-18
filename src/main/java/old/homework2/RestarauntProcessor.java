package homework2;

import lombok.val;

import java.util.*;
import java.util.stream.Collectors;

public class RestarauntProcessor {

    public static List<String> namesDishes(Restaraunt restaraunt){
//        val namesDishes = new ArrayList<String>();
//        for(Dish dish: restaraunt.getMenu()){
//            namesDishes.add((dish.getName()));
//        }
//        return namesDishes;

        val namesDishes = restaraunt.getMenu().stream()
                .map(Dish::getName)
                .collect(Collectors.toList());
        return namesDishes;
    }

    public static Optional<Restaraunt> filterСalories(Restaraunt restaraunt, Integer calorie) {
//        val lowCalories = new ArrayList<Dish>();
//        for(Dish dish: restaraunt.getMenu()){
//            if(dish.getCalories() < calorie)
//                lowCalories.add(dish);
//        }
//        Restaraunt lowCaloriesDish = new Restaraunt(lowCalories);
//        return Optional.of(lowCaloriesDish);

        List<Dish> lowCaloriesDish = restaraunt.getMenu().stream()
                                                         .filter(dish -> dish.getCalories() < 100)
                                                         .collect(Collectors.toList());
        return Optional.of(new Restaraunt(lowCaloriesDish));
    }

    public static List<Dish> topNutritious(Restaraunt restaraunt) {
//        List<Dish> listNutritious =  new ArrayList<Dish>(restaraunt.getMenu());
//        listNutritious.sort((o1, o2) -> o1.getCalories().compareTo(o2.getCalories()));
//        List<Dish> topNutritious = new ArrayList<>();
//        for(int i = listNutritious.size()-1; i >= 0; i--){
//            if(topNutritious.size()<3)
//                topNutritious.add(listNutritious.get(i));
//        }
//        return topNutritious;
        List<Dish> topNutritious = restaraunt.getMenu().stream()
                                                       .sorted(getCompCalories())
                                                       .limit(3)
                                                       .collect(Collectors.toList());
        return topNutritious;
    }

    public static void sortByTypeAndByName(Restaraunt restaraunt) {
//        List<Dish> sortList =  new ArrayList<Dish>(restaraunt.getMenu());
//        sortList.sort((o1, o2) -> o1.getType().compareTo(o2.getType()));
//        for (Dish dish: sortList) {
//            System.out.println(dish);
//        }
//        sortList.sort((o1, o2) -> o1.getName().compareTo(o2.getName()));
//        for (Dish dish: sortList) {
//            System.out.println(dish);
//        }

        restaraunt.getMenu().stream()
                            .sorted(getCompType())
                            .peek(System.out::println)
                            .sorted(getCompName())
                            .forEach(System.out::println);
    }

    public static Map<DishType, Double> getAverageCalories(Restaraunt restaraunt){
//        List<Dish> averageCalories =  new ArrayList<Dish>(restaraunt.getMenu());
//        Map<DishType, Double> mapOfCalories = new HashMap<>();
//
//        List<Integer> beef = new ArrayList<>();
//        List<Integer> chicken = new ArrayList<>();
//        List<Integer> vegetables = new ArrayList<>();
//
//        for (Dish dish: averageCalories) {
//            if(dish.getType() == DishType.BEEF){
//                beef.add(dish.getCalories());
//            } else if (dish.getType() == DishType.CHICKEN) {
//                chicken.add(dish.getCalories());
//            } else {
//                vegetables.add(dish.getCalories());
//            }
//        }
//        mapOfCalories.put(DishType.BEEF, calcAverageCalories(beef));
//        mapOfCalories.put(DishType.CHICKEN, calcAverageCalories(chicken));
//        mapOfCalories.put(DishType.VEGETABLES, calcAverageCalories(vegetables));
//        for (Map.Entry<DishType, Double> entry : mapOfCalories.entrySet()) {
//            System.out.println(entry.getKey()+" : "+entry.getValue());
//        }
//        return mapOfCalories;

        Map<DishType, Double> mapOfCalories = restaraunt.getMenu().stream()
                .collect(Collectors.groupingBy(Dish::getType, Collectors.averagingInt(Dish::getCalories)));

        mapOfCalories.entrySet().forEach(System.out::println);

        return mapOfCalories;
    }

    public static Map<DishType, List<Dish>> groupByDishType(Restaraunt restaraunt){
//        Map<DishType, List<Dish>> groupByDishType = new HashMap<>();
//        List<Dish> beef = new ArrayList<>();
//        List<Dish> chicken = new ArrayList<>();
//        List<Dish> vegetables = new ArrayList<>();
//        for (Dish dish: restaraunt.getMenu()) {
//            if(dish.getType() == DishType.BEEF){
//                beef.add(dish);
//            } else if (dish.getType() == DishType.CHICKEN) {
//                chicken.add(dish);
//            } else {
//                vegetables.add(dish);
//            }
//        }
//        groupByDishType.put(DishType.BEEF, beef);
//        groupByDishType.put(DishType.CHICKEN, chicken);
//        groupByDishType.put(DishType.VEGETABLES, vegetables);
//
//        for (Map.Entry<DishType, List<Dish>> entry : groupByDishType.entrySet()) {
//            System.out.println(entry.getKey()+" : "+entry.getValue().toString());
//        }
//        return groupByDishType;

        Map<DishType, List<Dish>> groupByDishType = restaraunt.getMenu().stream()
                .collect(Collectors.groupingBy(Dish::getType, Collectors.toList()));

        groupByDishType.entrySet().forEach(System.out::println);
        return groupByDishType;
    }

    public static Map<DishType, List<String>> groupByBioName(Restaraunt restaraunt) {
//        Map<DishType, List<String>> groupByBioName = new HashMap<>();
//        List<String> beef = new ArrayList<>();
//        List<String> chicken = new ArrayList<>();
//        List<String> vegetables = new ArrayList<>();
//        for (Dish dish : restaraunt.getMenu()) {
//            if (dish.getType() == DishType.BEEF && dish.getIsBio()) {
//                beef.add(dish.getName());
//            } else if (dish.getType() == DishType.CHICKEN && dish.getIsBio()) {
//                chicken.add(dish.getName());
//            } else if (dish.getIsBio()) {
//                vegetables.add(dish.getName());
//            }
//        }
//        groupByBioName.put(DishType.BEEF, beef);
//        groupByBioName.put(DishType.CHICKEN, chicken);
//        groupByBioName.put(DishType.VEGETABLES, vegetables);
//        for (Map.Entry<DishType, List<String>> entry : groupByBioName.entrySet()) {
//            System.out.println(entry.getKey() + " : " + entry.getValue().toString());
//        }
//        return groupByBioName;

        Map<DishType, List<String>> groupByBioName = restaraunt.getMenu().stream()
                .filter(Dish::getIsBio)
                .collect(Collectors.groupingBy(Dish::getType, Collectors.mapping(Dish::getName, Collectors.toList())));

        groupByBioName.entrySet().forEach(System.out::println);
        return groupByBioName;
    }

    private static Comparator<Dish> getCompCalories()
    {
        return (d1, d2) -> d2.getCalories().compareTo(d1.getCalories());
    }
    private static Comparator<Dish> getCompType()
    {
        return Comparator.comparing(Dish::getType);
    }

    private static Comparator<Dish> getCompName()
    {
        return Comparator.comparing(Dish::getName);
    }

    private static double calcAverageCalories(List<Integer> dish) {
        if (dish == null || dish.isEmpty())
            return 0;
        double sum = 0;
        for (Integer d : dish)
            sum += d;

        return sum / dish.size();
    }
}
