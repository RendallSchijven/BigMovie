package nl.nhl.groep16.parser;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;

import java.io.FileWriter;
import java.util.Calendar;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;


public class Main {

    static boolean running = true;

    static Scanner scanner;

    static Parser parser;

    public static void main(String[] args) throws Exception {
        long startTime = System.nanoTime();
        ParserReader pr = new ParserReader(args[0]);
        Appendable out = System.out;
        if (args.length > 1) {
            out = new FileWriter(args[1]);
        }
        CSVPrinter csvPrinter = new CSVPrinter(out, CSVFormat.MYSQL);
        if (args.length > 2)
            pr.writeToCsvPrinter(csvPrinter, Integer.parseInt(args[2]));
        else
            pr.writeToCsvPrinter(csvPrinter);
        long endTime = System.nanoTime();
        long time = TimeUnit.SECONDS.convert((endTime - startTime), TimeUnit.NANOSECONDS);
        System.out.println("Done\nTime: " + time);
    }
}
