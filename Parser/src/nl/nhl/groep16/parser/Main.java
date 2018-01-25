package nl.nhl.groep16.parser;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;

import java.awt.*;
import java.io.File;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;


public class Main {

    static boolean running = true;

    static Scanner scanner;
    public static boolean linesArg = false;
    public static int amountOfLinesToParse;

    public static void main(String[] args) throws Exception {

        // Simple check for a help flag
        if (args[0].toString().equals("--help") || args[0].toString().equals("-h")) {
            Main.printHelp();
            return;
        }

        // Write alll output to an output directory
        File dir = new File("out");
        dir.mkdir();

        ArrayList<ParserReader> parseReadersList = new ArrayList<ParserReader>();

        boolean linesArg;
        int amountOfLinesToParse;
        // String outputLocation = new File(" ").getAbsolutePath();
        // outputLocation = outputLocation.replaceAll("\\s+$", "");

        boolean tl = false;
        // Process the input arguments
        for (int i = 0; i < args.length; i++) {
            if (args[i].equals("-l")) {
                linesArg = true;
                tl = true;
            } else if (tl == true) {
                amountOfLinesToParse = Integer.parseInt(args[i].toString());
                tl = false;
            } else {
                ParserReader pr = new ParserReader(args[i]);
                parseReadersList.add(pr);
            }
        }

        // Start Threads
        for (ParserReader parser : parseReadersList) {
            parser.start();
        }
    }

//=======================================
//         Old non-threaded version
//=======================================
//
//        long startTime = System.nanoTime();
//        ParserReader pr = new ParserReader(args[0]);
//        Appendable out = System.out;
//
//        if (args.length > 1) {
//            out = new FileWriter(args[1]);
//        }
//
//        CSVPrinter csvPrinter = new CSVPrinter(out, CSVFormat.MYSQL);
//
//        if (args.length > 2)
//            pr.writeToCsvPrinter(csvPrinter, Integer.parseInt(args[2]));
//        else
//            pr.writeToCsvPrinter(csvPrinter);
//
//        long endTime = System.nanoTime();
//        long time = TimeUnit.SECONDS.convert((endTime - startTime), TimeUnit.NANOSECONDS);
//        System.out.println("Done\nTime: " + time);
//    }


    /**
     * Print help info
     */
    static private void printHelp() {
        System.out.println("Parser: ./parser movies.list mpaa-rating.list -l 50 -o outputDir/\n\targuments:\n\t-l\tamount of lines to parse");
    }
}
