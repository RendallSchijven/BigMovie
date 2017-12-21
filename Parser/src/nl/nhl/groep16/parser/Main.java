package nl.nhl.groep16.parser;

import nl.nhl.groep16.parser.util.Config;
import nl.nhl.groep16.parser.util.util;

import java.io.*;
import java.util.Scanner;

public class Main {

    static boolean running = true;

    static Scanner scanner;

    static Parser parser;

    public static void main(String[] args) {
        System.out.println(Config.NAME + " " + Config.VERSION);
        scanner = new Scanner(System.in);
        parser = new Parser();

        while (running) {
            mainLoop();
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
                parser.checkLine(line);

                //Prints out percentage
                counter += line.length();
                double percent = (double) counter / (double) numChars * 100;
                System.out.println(util.round(percent, 2) + "%");
            }

            System.out.println(100 + "%");
            System.out.println("done");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
