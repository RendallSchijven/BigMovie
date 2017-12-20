package nl.nhl.groep16.parser;

import nl.nhl.groep16.parser.input.ActorFile;
import nl.nhl.groep16.parser.util.Config;

import java.io.*;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.Scanner;

public class Main {

    static boolean running = true;

    static Scanner scanner;

    static Parser parser;

    public static void main(String[] args) throws FileNotFoundException {
        System.out.println(Config.NAME + " " + Config.VERSION);
        scanner = new Scanner(System.in);
        parser = new Parser();

        while (running) {
            mainLoop();
        }
//        BufferedReader br = new BufferedReader(new FileReader("/home/human/Documents/BigData/actors.list"));
//        ActorFile a = new ActorFile(br);
//        while(true) {
//            System.out.println(a.getNextActor());
//        }

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
