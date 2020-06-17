import java.util.*;

import static java.util.stream.Collectors.*;

interface DrinkReceipt {
    String getName();
    DrinkReceipt addComponent(String componentName, int componentCount);
}

interface DrinkPreparation {
    Map<String, Integer> makeDrink();
}

interface Rating {
    int getRating();
}

class Caffee implements DrinkReceipt, DrinkPreparation, Rating {
    private String name;
    private int rating;
    private Map<String, Integer> ingredients = new HashMap<>();;

    public Caffee(String name, int rating) {
        this.name = name;
        this.rating = rating;
        addComponent("Water", 100);
        addComponent("Arabica", 20);
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public DrinkReceipt addComponent(String componentName, int componentCount) {
        ingredients.merge(componentName, componentCount, Integer::sum);
        return this;
    }

    @Override
    public Map<String, Integer> makeDrink() {
        return Collections.unmodifiableMap(ingredients);
    }

    @Override
    public int getRating() {
        return rating;
    }
}

class Espresso extends Caffee {
    public Espresso(String name, int rating) {
        super(name, rating);
        addComponent("Water", -50);
    }
}

class Cappuccino extends Caffee {
    public Cappuccino(String name, int rating) {
        super(name, rating);
        addComponent("Milk", 50);
    }
}

class MyUtils {
    // this version is OK
    public Map<String, Double> averageRating(
            List<Caffee> coffees    // this non generic version is OK
    public Map<String, Double> averageRating(
            List<Caffee> coffees
    ) {
        return coffees.stream().filter(Objects::nonNull)
                .collect(groupingBy(
                        DrinkReceipt::getName,
                        averagingInt(Rating::getRating)
                ));
    }

    // this generic version compiles but causes Runtime Exception!
    // the body is exactly the same as above
    public <T extends DrinkReceipt & Rating>
    Map<String, Double> averageRatingGenericRTE(
            List<? extends T> coffees
    ) {
        return coffees.stream().filter(Objects::nonNull)
                .collect(groupingBy(
                        DrinkReceipt::getName,
                        Collectors.averagingInt(Rating::getRating)
                ));
    }

    // this generic version is OK:
    // the declaration is exactly as above
    // type hint was added in averagingInt
    public <T extends DrinkReceipt & Rating>
    Map<String, Double> averageRatingGenericOK(
            List<? extends T> coffees
    ) {
        return coffees.stream().filter(Objects::nonNull)
                .collect(groupingBy(
                        DrinkReceipt::getName,
                        Collectors.<T>averagingInt(Rating::getRating)
                ));
    }

    public static void main(String[] args) {
        List<Caffee> cups = List.of(
                new Espresso("Espresso", 8),
                new Cappuccino("Cappuccino", 10),
                new Espresso("Espresso", 10),
                new Cappuccino("Cappuccino", 6),
                new Caffee("Caffee", 6)
        );

        cups.stream()
                .peek(cup -> cup.addComponent("Sugar", 5))
                .map(DrinkPreparation::makeDrink)
                .forEach(System.out::println);

        System.out.println(new MyUtils().averageRating(cups));
        System.out.println(new MyUtils().averageRatingGenericOK(cups));
        System.out.println("***** OK by now *****");
        // this causes Runtime Exception:
        System.out.println(new MyUtils().averageRatingGenericRTE(cups));
    }
}
