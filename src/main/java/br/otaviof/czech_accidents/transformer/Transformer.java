package br.otaviof.czech_accidents.transformer;

import br.otaviof.czech_accidents.adt.list.CustomList;
import br.otaviof.czech_accidents.adt.queue.CustomQueue;
import br.otaviof.czech_accidents.adt.queue.DynamicQueue;
import br.otaviof.czech_accidents.adt.stack.DynamicStack;
import br.otaviof.czech_accidents.io.*;
import br.otaviof.czech_accidents.sort.Sorter;

import java.io.File;
import java.io.IOException;
import java.util.logging.Logger;

public class Transformer {
    private static final Logger logger = Logger.getLogger("Transformer");

    public enum SortOrder {
        ASCENDING, DESCENDING
    }

    /**
     * Aplica um filtro sobre uma coluna de um arquivo separado por vírgulas
     * @param input O arquivo de entrada
     * @param output O arquivo de saída
     * @param column A coluna a ser filtrada
     * @param filter O filtro a ser aplicado sobre a coluna
     * @throws IOException Se ocorrer algum erro na leitura ou escrita do arquivo
     */
    public static void filterByColumn(File input, File output, String column, CellFilter filter) throws IOException {
        logger.info(String.format("Reading file \"%s\"", input));
        final TableTransformer tt = new TableTransformer(input);
        tt.getTracker().setAction((p) -> {logger.fine(String.format("Writing progress: %.2f %%", 100*p));});

        final int writtenLines = tt.filter(
            output,
            column,
            filter
        );

        logger.info(String.format("Done. Written %d lines", writtenLines));
    }

    /**
     * Ordena um arquivo por uma coluna
     * @param clazz A classe da coluna a ser ordenada
     * @param input O arquivo de entrada
     * @param outputDir O diretório de saída
     * @param outputExp A expressão que determina o nome do arquivo de saída
     * @param columnName O nome da coluna
     * @param converter Conversor de tipo da coluna
     * @param order Ordem, crescente ou decrescente
     * @param <T> Tipo genérico da coluna
     * @throws IOException Se ocorrer algum erro de leitura
     */
    public static <T extends Comparable<? super T>> void sortColumn(Class<T> clazz,
                                                                      File input,
                                                                      File outputDir,
                                                                      String outputExp,
                                                                      String columnName,
                                                                      CellConverter<T> converter,
                                                                      SortOrder order) throws IOException {

        logger.info(String.format("Reading file \"%s\"", input));

        // Read table
        final TableReader tr = new TableReader(input);
        tr.getTracker().setAction((p) -> {
            logger.fine(String.format("Reading: %.2f %%", 100*p));
        });

        final CustomList<T> data = tr.getColumnAs(columnName, converter);
        logger.info(String.format("OK. Read %d lines.", data.getSize()));

        // Prepare table writer
        final TableTransformer tt = new TableTransformer(input);
        tt.getTracker().setAction((p) -> {
            logger.fine(String.format("Writing progress: %.2f %%", 100*p));
        });
        CustomQueue<Integer> newOrder;

        // Prepare timer
        long time;


        // COUNTING-SORT
        if(clazz == Integer.class) {
            logger.info("***Counting-Sort***");
            time = System.currentTimeMillis();
            newOrder = Sorter.countingSort((CustomList<Integer>) data, (p) -> {
                logger.fine(String.format("Sorting progress: %.2f %%", 100*p));
            });
            time = System.currentTimeMillis()-time;
            logger.info(String.format("Counting-Sort took %d ms.", time));

            if(order == SortOrder.DESCENDING)
                newOrder = new DynamicQueue<>(new DynamicStack<>(newOrder)); // queue->stack->queue inverte ordem
            tt.reorder(new File(outputDir, String.format(outputExp, "countingSort")), newOrder);
        }


        // HEAP-SORT
        logger.info("***Heap-Sort***");
        time = System.currentTimeMillis();
        newOrder = Sorter.heapSort(data, (p) -> {
            logger.fine(String.format("Sorting progress: %.2f %%", 100*p));
        });
        time = System.currentTimeMillis()-time;
        logger.info(String.format("Heap-Sort took %d ms.", time));

        if(order == SortOrder.DESCENDING)
            newOrder = new DynamicQueue<>(new DynamicStack<>(newOrder)); // queue->stack->queue inverte ordem
        tt.reorder(new File(outputDir, String.format(outputExp, "heapSort")), newOrder);


        // INSERTION-SORT
        logger.info("***Insertion-Sort***");
        time = System.currentTimeMillis();
        newOrder = Sorter.insertionSort(data, (p) -> {
            logger.fine(String.format("Sorting progress: %.2f %%", 100*p));
        });
        time = System.currentTimeMillis()-time;
        logger.info(String.format("Insertion-Sort took %d ms.", time));

        if(order == SortOrder.DESCENDING)
            newOrder = new DynamicQueue<>(new DynamicStack<>(newOrder)); // queue->stack->queue inverte ordem
        tt.reorder(new File(outputDir, String.format(outputExp, "insertionSort")), newOrder);


        // MERGE-SORT
        logger.info("***Merge-Sort***");
        time = System.currentTimeMillis();
        newOrder = Sorter.mergeSort(data, (p) -> {
            logger.fine(String.format("Sorting progress: %.2f %%", 100*p));
        });
        time = System.currentTimeMillis()-time;
        logger.info(String.format("Merge-Sort took %d ms.", time));

        if(order == SortOrder.DESCENDING)
            newOrder = new DynamicQueue<>(new DynamicStack<>(newOrder)); // queue->stack->queue inverte ordem
        tt.reorder(new File(outputDir, String.format(outputExp, "mergeSort")), newOrder);


        // QUICK-SORT
        logger.info("***Quick-Sort***");
        time = System.currentTimeMillis();
        newOrder = Sorter.quickSort(data, (p) -> {
            logger.fine(String.format("Sorting progress: %.2f %%", 100*p));
        });
        time = System.currentTimeMillis()-time;
        logger.info(String.format("Quick-Sort took %d ms.", time));

        if(order == SortOrder.DESCENDING)
            newOrder = new DynamicQueue<>(new DynamicStack<>(newOrder)); // queue->stack->queue inverte ordem
        tt.reorder(new File(outputDir, String.format(outputExp, "quickSort")), newOrder);


        // QUICK-SORT (MEDIAN OF 3)
        logger.info("***Quick-Sort (Median of 3)***");
        time = System.currentTimeMillis();
        newOrder = Sorter.quick3MedianSort(data, (p) -> {
            logger.fine(String.format("Sorting progress: %.2f %%", 100*p));
        });
        time = System.currentTimeMillis()-time;
        logger.info(String.format("Quick-Sort (Median of 3) took %d ms.", time));

        if(order == SortOrder.DESCENDING)
            newOrder = new DynamicQueue<>(new DynamicStack<>(newOrder)); // queue->stack->queue inverte ordem
        tt.reorder(new File(outputDir, String.format(outputExp, "quickSortMedianOf3")), newOrder);


        // SELECTION-SORT
        logger.info("***Selection-Sort***");
        time = System.currentTimeMillis();
        newOrder = Sorter.selectionSort(data, (p) -> {
            logger.fine(String.format("Sorting progress: %.2f %%", 100*p));
        });
        time = System.currentTimeMillis()-time;
        logger.info(String.format("Selection-Sort took %d ms.", time));

        if(order == SortOrder.DESCENDING)
            newOrder = new DynamicQueue<>(new DynamicStack<>(newOrder)); // queue->stack->queue inverte ordem
        tt.reorder(new File(outputDir, String.format(outputExp, "selectionSort")), newOrder);
    }
}
