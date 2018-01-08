package nl.nhl.groep16.parser;

import nl.nhl.groep16.parser.parsers.*;

import java.io.*;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Scanner;

public class Main {

    static boolean running = true;

    static Scanner scanner;

    static Parser parser;

    public static void main(String[] args) throws IOException {
//        System.out.println(Config.NAME + " " + Config.VERSION);
//        scanner = new Scanner(System.in);
//        parser = new Parser();
//
//        while (running) {
//            mainLoop();
//        }

//        BufferedReader br = new BufferedReader(new FileReader("D:/IMDB/mpaa-ratings-reasons.list"));
        BufferedReader br = new BufferedReader(new FileReader("C:/Users/twant/Desktop/ratings.list"));

        ParserInterface parser = new RatingsParser();
        String line;
        while ((line = br.readLine()) != null) {
            String[] out = parser.convertLine(line);
            if(out != null) {
                System.out.println(String.join(",",out));
            }
        }
    }

    //Main loop of the console
    public static void mainLoop() {
        System.out.println("Give file path:");
        String filePath = scanner.next();
        loadFile(filePath);
    }

    //Loads a file and loops trough the lines
    public static void loadFile(String path) {

        long numChars = new File(path).length();

        try (BufferedReader br = new BufferedReader(new FileReader(path))) {
            long counter = 0;

            for (String line; (line = br.readLine()) != null; ) {

                //Prints out percentage
                counter += line.length();
                double percent = (double) counter / (double) numChars * 100;
                System.out.println(round(percent, 2) + "%");
            }

            System.out.println(100 + "%");
            System.out.println("done");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //Round method to round doubles
    public static double round(double value, int places) {
        if (places < 0) throw new IllegalArgumentException();

        BigDecimal bd = new BigDecimal(value);
        bd = bd.setScale(places, RoundingMode.HALF_UP);
        return bd.doubleValue();
    }
}
