class Array<T> {
    private T[] array;

    public Array(T[] array) {
        this.array = array;
    }
    public T get(int index) {
        return array[index];
    }
    public int length() {
        return array.length;
    }
}

class ArrayUtil {
    public static double averageValue(Array<? extends Number> array) {
        double res = 0;
        for (int i = 0; i < array.length(); i++) {
            res += array.get(i).doubleValue();
        }
        return res / array.length();
    }
}
