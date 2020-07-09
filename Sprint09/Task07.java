/*
Create a Stream<Integer> duplicateElements(Stream<Integer> stream) method of the MyUtils class
 to return a sorted stream of duplicated elements of the input stream.
For example, for a given elements
[3, 2, 1, 1, 12, 3, 8, 2, 4, 2]
you should get
[1, 2, 3]
*/

import java.util.Arrays;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Stream;

import static java.util.stream.Collectors.counting;
import static java.util.stream.Collectors.groupingBy;

class MyUtils {
    public Stream<Integer> duplicateElements(Stream<Integer> stream) {
        return stream.filter(Objects::nonNull)
                .collect(groupingBy(i -> i, counting()))
                .entrySet().stream()
                .filter(mapEntry -> mapEntry.getValue() > 1)
                .map(Map.Entry::getKey)
                .sorted();
    }
    // Alternative:
/*  public Stream<Integer> duplicateElements(Stream<Integer> stream) {
        Map<Integer, Integer> seen = new HashMap<>();
        return stream.filter(Objects::nonNull)
                .peek(i -> seen.merge(i, 1, Integer::sum))
                .filter(i -> seen.get(i) == 2)
                .sorted();
    }
*/

    public static void main(String[] args) {
        var in = Stream.of(3, 2, 1, 1, 12, 3, 8, 2, 4, 2);
        var expected = new Integer[] {1, 2, 3};
        var out = new MyUtils().duplicateElements(in);
//        System.out.println(Arrays.toString(out.toArray()));
        System.out.println(Arrays.equals(out.toArray(), expected) ? "OK" : "FAIL");
    }
}
