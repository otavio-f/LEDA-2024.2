package br.otaviof.czech_accidents.transformer;

import br.otaviof.czech_accidents.IO.Streamer;
import br.otaviof.czech_accidents.sorters.*;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.logging.Logger;

public class Transformer {
    private static final Logger logger = Logger.getLogger("Transformer");

    public enum SortOrder {
        ASCENDING, DESCENDING
    }

    private interface SorterCreator<T extends Comparable<? super T>> {
        public Sorter<T> create(T[] arr);
    }

    public static Thread filterAsync(File input, File output, String column, Streamer.Filter filter) throws IOException {
        Thread thread = new Thread(()->{
            try {
                Transformer.filterByColumn(input, output, column, filter);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
        thread.start();
        return thread;
    }

    public static void filterByColumn(File input, File output, String column, Streamer.Filter filter) throws IOException {
        logger.info(String.format("Reading file \"%s\"", input));
        final Streamer st = new Streamer(input);
        logger.info(String.format("Found %d lines and %d columns", st.lineCount, st.columnCount));
        final int writtenLines = st.filterToFile(
            output,
            column,
            filter,
            (p) -> {logger.fine(String.format("Writing progress: %.2f %%", 100*p));}
        );

        logger.info(String.format("Done. Written %d lines", writtenLines));
    }

    private static void reverseOrder(int[] order) {
        int temp;
        for(int i=0; i<order.length/2; i++) {
            temp = order[i];
            order[i] = order[order.length - i - 1];
            order[order.length - i - 1] = temp;
        }
    }

    private static <T extends Comparable<? super T>> void applySort(SorterCreator<T> creator, Streamer st, T[] originalData, File output, SortOrder order) throws IOException {
        System.gc();
        T[] data = Arrays.copyOf(originalData, originalData.length);
        Sorter<T> sorter = creator.create(data);
        sorter.setProgressTracker((p) -> {
            logger.fine(String.format("Sorting progress: %.2f %%", 100*p));
        });
        long time = System.currentTimeMillis();
        int[] newOrder = sorter.sort();
        if(order == SortOrder.DESCENDING)
            reverseOrder(newOrder);
        time = System.currentTimeMillis()-time;
        logger.info(String.format("Sorted in %d ms", time));
        st.writeReordered(output, newOrder, (p) -> {
            logger.fine(String.format("Writing progress: %.2f %%", 100*p));
        });
    }

    public static <T extends Comparable<? super T>> void sortByColumn(File input, File outputDir, String outputExp, String column, Streamer.Converter<T> converter, Transformer.SortOrder order) throws IOException {
        logger.info(String.format("Reading file \"%s\"", input));

        Streamer st = new Streamer(input);
        T[] data = st.getColumn(column, converter, (p) -> {
            logger.fine(String.format("Reading progress: %.2f %%", 100*p));
        });

        /*
        if(data[0] instanceof Integer) { // BUG: (data instanceof Integer[]) is always false
            logger.info("Counting-Sort");
            applySort(
                (arr)-> new CountingSort(arr), st, data, String.format(outputExp, "countingSort"),
                // BUG: Fails to cast Sorter<Integer> to Sorter<T>, even if T is Integer
                order);
        }
        */

        /*
        Heap-Sort
         * Medium case: random order
         * Worst case: same as medium case
         * Best case: array of 1 element (won't do!)
         */
        logger.info("Heap-Sort");
        applySort(
            (arr)->(new HeapSort<T>(arr)),
            st,
            data,
            new File(outputDir, String.format(outputExp, "heapSort")),
            order);

        /*
        Insertion-Sort
         * Medium-case: random order
         * Worst case: descending order
         * Best case: ascending order
         */
        logger.info("Insertion-Sort");
        applySort(
            (arr)->(new InsertionSort<T>(arr)),
            st,
            data,
            new File(outputDir, String.format(outputExp, "insertionSort")),
            order);

        /*
        Merge-Sort
         * Medium case: random order
         * Worst case: alternate elements (won't do!)
         * Best case: any order
         */
        logger.info("Merge-Sort");
        applySort(
            (arr)->(new MergeSort<T>(arr)),
            st,
            data,
            new File(outputDir, String.format(outputExp, "mergeSort")),
            order);

        /*
        Quick-Sort:
         * Medium case: random order
         * Worst case: pivot is the min (ascending/descending order)
         * Best case: pivot is always mean (how?)
         */
        try {
            logger.info("Quick-Sort");
            applySort(
                (arr)->(new QuickSort<T>(arr)),
                st,
                data,
                new File(outputDir, String.format(outputExp, "quickSort")),
                order);
        } catch (StackOverflowError e) {
            logger.warning("Quick-Sort died!");
        }

        /*
        Quick-Sort Median of 3:
         * Medium case: random order
         * Worst case: ?
         * Best case: pivot is always mean (how?)
         */
        try {
            logger.info("Quick-Sort, median of 3");
            applySort(
                (arr)->(new QuickSortMedianThree<>(arr)),
                st,
                data,
                new File(outputDir, String.format(outputExp, "quickSortMedianOf3")),
                order);
        } catch (StackOverflowError e) {
            logger.warning("Quick-Sort median of 3 died!");
        }

        /*
        Selection-Sort
         * Medium case: random order
         * Worst case: descending order
         * Best case: ascending order
         */
        logger.info("Selection-Sort");
        applySort(
            (arr)->(new SelectionSort<T>(arr)),
            st,
            data,
            new File(outputDir, String.format(outputExp, "selectionSort")),
            order);

    }
}
