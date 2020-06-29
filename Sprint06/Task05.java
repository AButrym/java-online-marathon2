import java.util.Set;
import java.util.function.Predicate;

class MyUtils {
    static Predicate<Integer> getPredicateFromSet(
        Set<Predicate<Integer>> predicateSet
    ) {
        return predicateSet.stream()
                .reduce(Predicate<Integer>::and)
                .orElseThrow(IllegalArgumentException::new);
    }
}
