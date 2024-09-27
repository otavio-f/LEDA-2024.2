package br.otaviof.czech_accidents;

import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.LogRecord;
import java.util.logging.Logger;

import br.otaviof.czech_accidents.transformer.Transformer;

public class Main {
    private static final Logger logger = Logger.getLogger("Main");
    private static File output;
    private static File input;

    private static boolean parseCmdLine(String[] args) {
        if (args.length == 4) {
            // {"-i"/"--input", <input>, "-o"/"--output", <output>}
            if((args[0].equals("-i") || args[0].equals("--input")) && (args[2].equals("-o") || args[2].equals("--output"))) {
                input = new File(args[1]);
                output = new File(args[3]);
                return true;
            }

            // {"-o"/"--output", <output>, "-i"/"--input", <input>}
            if((args[0].equals("-o") || args[0].equals("--output")) && (args[2].equals("-i") || args[2].equals("--input"))) {
                output = new File(args[1]);
                input = new File(args[3]);
                return true;
            }
        }

        if(args.length == 2) {
            // {<input>, <output>}
            input = new File(args[0]);
            output = new File(args[1]);
            return true;
        }

        if(args.length == 0) {
            // use default relative paths
            input = new File("input");
            output = new File("output");
            return true;
        }

        return false;
    }

    private static boolean verifyPaths() {
        final File accidents = new File(input, "road_accidents_czechia_2016_2022.csv");
        if(!accidents.canRead()) {
            logger.severe("Cannot read road accidents file!");
            return false;
        }

        final File pedestrians = new File(input, "pedestrian.csv");
        if(!pedestrians.canRead()) {
            logger.severe("Cannot read road pedestrians file!");
            return false;
        }

        if(output.exists() && !output.isDirectory()) {
            logger.severe("Cannot write to output path!");
            return false;
        }

        if(!output.exists()) {
            logger.warning("Output path doesn't exist. Will try to create.");
            if(!output.mkdir()) {
                logger.severe("Cannot create output path!");
                return false;
            }
        }
        return true;
    }

    private static void configLoggerToOutput(String name, long delay) {
        Logger log = Logger.getLogger(name);
        log.setLevel(Level.FINE);
        log.addHandler(new Handler() {
            long prevTime = 0;
            String prevMessage = "";
            @Override
            public void publish(LogRecord logRecord) {
                Level level = logRecord.getLevel();
                if(level.intValue() > Level.FINE.intValue()) { // print priority messages immediately
                    System.out.printf("<%s> - %s\n", logRecord.getLoggerName(), logRecord.getMessage());
                }

                if(prevMessage.equals(logRecord.getMessage())) // avoid repeating messages
                    return;

                long time = System.currentTimeMillis();
                if(time-prevTime < delay) // avoid spamming debug messages
                    return;
                System.out.printf("<%s> - %s\n", logRecord.getLoggerName(), logRecord.getMessage());
                prevTime = time;
                prevMessage = logRecord.getMessage();
            }

            @Override
            public void flush() {
                System.out.flush();
            }

            @Override
            public void close() throws SecurityException {
            }
        });
    }

    public static void main(String[] args) throws IOException {
        if(!parseCmdLine(args)) {
            logger.severe("Invalid command line arguments.");
            logger.info(String.format("Usage: %s [-i/--input] <input dir> [-o/--output] <output dir>", args[0]));
            return;
        };

        if(!verifyPaths()) {
            logger.severe("Failed to initialize input/output paths.");
            return;
        }

        configLoggerToOutput("Transformer", 1500L);
        configLoggerToOutput("Streamer", 1500L);


        Transformer.filterByColumn(
            new File(input, "road_accidents_czechia_2016_2022.csv"),
            new File(output, "alcohol_accidents.csv"),
            "alcohol",
            (item) -> (item.contains("yes"))
        );

        Transformer.filterByColumn(
            new File(input, "road_accidents_czechia_2016_2022.csv"),
            new File(output, "accidents_NCBMV.csv"),
            "crash_kind",
            (item) -> (item.equals("not an option It is not a collision between moving vehicles"))
        );

        Transformer.filterByColumn(
            new File(output, "accidents_NCBMV.csv"),
            new File(output, "accidents_CWFA.csv"),
            "accident_kind",
            (item) -> (item.equals("collision with forest animals"))
        );

        Transformer.filterByColumn(
            new File(input, "pedestrian.csv"),
            new File(output, "drunk_pedestrians.csv"),
            "pedestrian_condition",
            (item) -> (item.contains("alcohol"))
        );

        final File sorterInput = new File(output, "accidents_NCBMV.csv");
        final DateTimeFormatter format = DateTimeFormatter.ofPattern("uuuu-MM-dd");
        Transformer.sortByColumn(
            sorterInput,
            output,
            "accidents_NCBMV_date_%s_medioCaso.csv",
            "date",
            (date) -> (LocalDate.parse(date, format)),
            Transformer.SortOrder.DESCENDING
        );

        Transformer.sortByColumn(
            sorterInput,
            output,
            "accidents_NCBMV_time_%s_medioCaso.csv",
            "time",
            (time) -> Double.valueOf(time),
            Transformer.SortOrder.ASCENDING
        );

        Transformer.sortByColumn(
            sorterInput,
            output,
            "accidents_NCBMV_communication_kind_%s_medioCaso.csv",
            "communication_kind",
            (kind) -> (kind),
            Transformer.SortOrder.ASCENDING
        );

    }
}
