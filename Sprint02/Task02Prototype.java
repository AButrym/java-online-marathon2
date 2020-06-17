import java.util.*;
import java.util.stream.Collectors;

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
    protected final static Map<String, Integer> PROTOTYPE = Map.of(
            "Water", 100,
            "Arabica", 20);

    /* polymorphic static field behaviour via reflection */
    @SuppressWarnings("unchecked")
    protected Map<String, Integer> getPrototype() {
        try { // get redefined (hiding) static PROTOTYPE from subclass
            return (Map<String, Integer>) getClass()
                    .getDeclaredField("PROTOTYPE")
                    .get(null);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            return PROTOTYPE; // static field is not redefined in subclass
        }
    }

    private String name = getClass().getName();
    private int rating;
    private Map<String, Integer> ingredients;

    public Caffee() {
    }

    public Caffee(String name, int rating) {
        this.name = name;
        this.rating = rating;
    }

    public Caffee withRating(int rating) {
        this.rating = rating;
        return this;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public DrinkReceipt addComponent(String componentName, int componentCount) {
        if (ingredients == null) {
            ingredients = new HashMap<>(getPrototype());
        }
        ingredients.merge(componentName, componentCount, Integer::sum);
        return this;
    }

    @Override
    public Map<String, Integer> makeDrink() {
        return ingredients != null
                ? Collections.unmodifiableMap(ingredients)
                : getPrototype();
    }

    @Override
    public int getRating() {
        return rating;
    }
}

class Espresso extends Caffee {
    protected final static Map<String, Integer> PROTOTYPE = Map.of(
            "Water", 50,
            "Arabica", 20);
}

class Cappuccino extends Caffee {
    protected final static Map<String, Integer> PROTOTYPE = Map.of(
            "Water", 100,
            "Arabica", 20,
            "Milk", 50);
}

class MyUtils {
    public <T extends DrinkReceipt & Rating>
    Map<String, Double> averageRating(
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
                new Espresso().withRating(8),
                new Cappuccino().withRating(10),
                new Espresso().withRating(10),
                new Cappuccino().withRating(6),
                new Caffee().withRating(6)
        );

        cups.stream()
                .peek(cup -> cup.addComponent("Sugar", 5))
                .map(DrinkPreparation::makeDrink)
                .forEach(System.out::println);

        System.out.println(new MyUtils().averageRating(cups));
    }
}
