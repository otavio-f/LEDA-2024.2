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
        Integer[] result = new Integer[size];
        while (--size >= 0)
            result[size] = genInt(low, high);
        time = System.currentTimeMillis() - time;
        System.out.printf("Generated array in %d ms!\n", time);
        return result;
    }


    /**
     * Generates a random integer
     *
     * @param low  lower limit of integer range
     * @param high upper limit of integer range
     * @return a integer from low to high
     */
    public static Integer genInt(int low, int high) {
        return low + RNG.nextInt(high - low);
    }

    /**
     * Generates a random integer from 0 to 100
     *
     * @return a integer in the range 0-100, inclusive
     */
    public static Integer genInt() {
        return genInt(0,100);
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
