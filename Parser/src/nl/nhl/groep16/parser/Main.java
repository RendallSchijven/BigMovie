package nl.nhl.groep16.parser;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;

import java.io.FileWriter;
import java.util.Scanner;


public class Main {

    static boolean running = true;

    static Scanner scanner;

    static Parser parser;

    public static void main(String[] args) throws Exception {
        ParserReader pr = new ParserReader(args[0]);
        Appendable out = System.out;
        if(args[2] != null){
            out = new FileWriter(args[1]);
        }
        CSVPrinter csvPrinter = new CSVPrinter(out, CSVFormat.DEFAULT);
        pr.writeToCsvPrinter(csvPrinter);
    }
}
