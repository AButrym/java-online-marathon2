class ArrayUtil {
    public static <T> T setAndReturn(T[] arr, T val, int ix) {
        return arr[ix] = val;
    }
}
