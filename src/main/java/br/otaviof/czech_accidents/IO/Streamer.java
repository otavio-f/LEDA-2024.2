package br.otaviof.czech_accidents.IO;

import br.otaviof.czech_accidents.tracker.ProgressTracker;

import java.io.*;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Streamer {
    /**
     * Functional interface that accepts or rejects an item
     */
    public interface Filter {
        /**
         * Filters items according to its condition
         *
         * @param item Item to be filtered
         * @return true if the item is accepted by the filter, otherwise false
         */
        public boolean accepts(String item);
    }

    /**
     * Functional interface to convert cells to types
     * @param <T> A comparable type
     */
    public interface Converter<T extends Comparable<? super T>> {
        /**
         * Converts a string cell to a suitable type
         * @param item the cell to be converted
         * @return the cell converted to the appropriate type
         */
        public T convert(String item);
    }

    private static Logger logger = Logger.getLogger("CSVStreamer");

    final private static Pattern PATTERN = Pattern.compile(
        "(?:\\s*(?:\\\"([^\\\"]*)\\\"|([^,]*))\\s*,?)+?", // Matches any cell, found on
        // https://regex101.com/library/eH1zP0
        Pattern.CASE_INSENSITIVE);

    private final File input;
    /** Column amount */
    public final int columnCount;
    /** Line amount, including headers */
    public final int lineCount;

    public Streamer(File file) throws IOException {
        this.input = file;

        final BufferedReader br = new BufferedReader(new FileReader(this.input));

        final String header = br.readLine();
        assert(header != null);
        int cc = 0;
        final Matcher match = PATTERN.matcher(header);
        while(match.find())
            cc++;
        this.columnCount = cc-1;

        int lc = 1;
        while(br.readLine() != null)
            lc++;
        this.lineCount = lc;

        br.close();
    }

    /**
     * Retrieves the matching column from the regular expression matcher
     *
     * @param matcher The object containing the regular expression matching data
     * @return The matching column or null if there's no match
     */
    private String findMatch(Matcher matcher) {
        if (!matcher.find())
            return null; // WARN: It seems some columns are missing
        // Already matches, so either group 1 or 2 has the value
        String result = matcher.group(1);
        if (result == null)
            result = matcher.group(2);
        return result;
    }

    /**
     * Splits a line of text
     *
     * @param line A comma-separated line
     * @return A line separated by columns or null if failed to parse the amount of
     *         columns expected
     */
    private String[] getCells(String line, int cellCount) {
        Matcher matcher = PATTERN.matcher(line);
        String[] result = new String[cellCount];
        for (int i = 0; i < cellCount; i++) {
            String match = findMatch(matcher);
            // TODO: maybe replace missing cells with empty spaces?
            result[i] = (match==null) ? "" : match;
//            if (match == null) {
//                return null;
//            }
//            result[i] = match;
        }
        return result;
    }

    private int findColumnIndex(String[] header, String column) {
        for(int i=0; i<header.length; i++)
            if(header[i].equals(column))
                return i;
        throw new IllegalArgumentException(String.format("Column %s", column));
    }

    public <T extends Comparable<? super T>> T[] getColumn(String name, Converter<T> convertFunc, ProgressTracker.Tracker trackerFunc) throws IOException {
        FileReader fr = new FileReader(this.input);
        BufferedReader br = new BufferedReader(fr);

        ProgressTracker pt = new ProgressTracker(this.lineCount);
        pt.setTracker(trackerFunc);

        // Get column index
        String[] cells = getCells(br.readLine(), this.columnCount);
        final int index = findColumnIndex(cells, name);
        pt.update();

        // Fill result array
        final T[] result = (T[]) new Comparable[this.lineCount-1];
        for(int i=1; i<this.lineCount; i++) {
            cells = getCells(br.readLine(), this.columnCount);
            result[i-1] = convertFunc.convert(cells[index]);
            // output[i-1] = convertFunc.convert(cells[index]);
            pt.update();
        }

        br.close();
        return result;
    }

    public int filterToFile(File output, String column, Filter filterFunc, ProgressTracker.Tracker trackerFunc) throws IOException {
        FileReader fr = new FileReader(this.input);
        BufferedReader br = new BufferedReader(fr);

        // Delete file if exists
        output.delete();

        // initialize write process
        FileWriter fw = new FileWriter(output);
        BufferedWriter bw = new BufferedWriter(fw);
        ProgressTracker pt = new ProgressTracker(this.lineCount);
        pt.setTracker(trackerFunc);

        // write headers
        String line = br.readLine();
        bw.write(line);
        bw.newLine();
        bw.flush();
        pt.update();

        // find column index
        String[] cells = getCells(line, this.columnCount);
        assert(cells != null); // useful if truncating the entire line upon encountering a missing cell
        final int index = findColumnIndex(cells, column);

        // filter and write out rest of file
        int writtenCount = 0;
        line = br.readLine();
        while(line != null) {
            cells = getCells(line, this.columnCount);
            if(cells != null && filterFunc.accepts(cells[index])) {
                bw.append(line);
                bw.newLine();
                bw.flush();
                writtenCount++;
            }
            pt.update();
            line = br.readLine();
        }

        bw.close();
        fw.close();
        return writtenCount;
    }

    public void writeReordered(File output, int[] newOrder, ProgressTracker.Tracker trackerFunc) throws IOException {
        FileReader fr = new FileReader(this.input);
        BufferedReader br = new BufferedReader(fr);

        final String[] lines = new String[this.lineCount-1];
        ProgressTracker pt = new ProgressTracker(this.lineCount);
        pt.setTracker(trackerFunc);

        // fetch header
        final String headers = br.readLine();

        // fetch lines
        for(int i=0; i<lines.length; i++) {
            lines[i] = br.readLine();
            if(i%2 == 0)
                pt.update();
        }

        // close read inputs, we stopped reading
        br.close();
        fr.close();

        // write lines to file
        output.delete();
        FileWriter fw = new FileWriter(output);
        BufferedWriter bw = new BufferedWriter(fw);

        // write header
        bw.append(headers);
        bw.newLine();
        bw.flush();

        // write rest of lines
        for(int i=0; i<lines.length; i++) {
            bw.append(lines[newOrder[i]]);
            bw.newLine();
            bw.flush();
            if(i%2==1)
                pt.update();
        }

        bw.close();
        fw.close();
    }
}
