package utils;

import java.util.Random;

public final class TestUtils {

    private static final Random RNG = new Random();

    /**
     * Generates an array of integers
     *
     * @param size length of the array
     * @param low  lower limit of integer range
     * @param high upper limit of integer range
     * @return an array of integers from low to high
     */
    public static Integer[] genIntArray(int size, int low, int high) {
        long time = System.currentTimeMillis();
        Integer result[] = new Integer[size];
        while (--size >= 0)
            result[size] = low + RNG.nextInt(high - low);
        time = System.currentTimeMillis() - time;
        System.out.printf("Generated array in %d ms!\n", time);
        return result;
    }

    public static Integer genInt() {
        return RNG.nextInt();
    }

    /**
     * Verifies if the array is correctly ordered
     *
     * @param arr
     * @return true if the array is in crescent order, otherwise false
     */
    public static boolean isOrdered(Integer arr[]) {
        for (int i = 0; i < arr.length - 1; i++)
            if (arr[i] > arr[i + 1])
                return false;
        return true;
    }

}
