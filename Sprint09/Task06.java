import java.util.*;
import java.util.stream.Stream;

import static java.util.function.Predicate.not;
import static java.util.stream.Collectors.*;

public class MyUtils {
    public Map<String, Stream<String>> phoneNumbers(List<Stream<String>> list) {
        return list.stream().filter(Objects::nonNull)
                .flatMap(x -> x).filter(Objects::nonNull)
                .map(s -> s.replaceAll("\\D", ""))
                .filter(not(String::isEmpty))
                .collect(groupingBy(
                        s -> s.length() == 10 ? s.substring(0, 3)
                                : s.length() == 7 ? "loc"
                                : "err",
                        mapping(s -> s.length() == 10 ? s.substring(3) : s,
                                collectingAndThen(
                                        toCollection(TreeSet::new), // sorting
                                        Collection::stream)
                        )
                ));
    }

    public static void main(String[] args) {
        // smoke test
        var in = List.of(
                Stream.of("093 987 65 43", "(050)1234567", "12-345"),
                Stream.of("067-21-436-57", "050-2345678", "0939182736", "224-19-28"),
                Stream.of("(093)-11-22-334", "044 435-62-18", "721-73-45"));
        var expected = Map.of(
                "050", Stream.of("1234567", "2345678"),
                "067", Stream.of("2143657"),
                "093", Stream.of("1122334", "9182736", "9876543"),
                "044", Stream.of("4356218"),
                "loc", Stream.of("2241928", "7217345"),
                "err", Stream.of("12345")
        );
        var out = new MyUtils().phoneNumbers(in);
//        out.forEach((k,v) -> System.out.printf("%s : %s%n", k, v.collect(Collectors.joining(", ", "[", "]"))));
//        System.out.println("<<GOT **************** EXPECTED>>");
//        expected.forEach((k,v) -> System.out.printf("%s : %s%n", k, v.collect(Collectors.joining(", ", "[", "]"))));
        /* Streams are for single use, so either print or test */
        boolean res = out.entrySet().stream()
                .allMatch(mapEntry ->
                        expected.containsKey(mapEntry.getKey()) &&
                                Arrays.equals(
                                        expected.get(mapEntry.getKey()).toArray(),
                                        mapEntry.getValue().toArray()
                                )
                );
        System.out.println(res ? "OK" : "FAIL");
    }
}
