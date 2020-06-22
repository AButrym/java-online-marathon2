import java.util.*;

public class MyUtils {

    public boolean listMapCompare(List<String> list, Map<String, String> map) {
        return new HashSet<>(list).equals(new HashSet<>(map.values()));
    }
}
