package nl.nhl.groep16.parser;

import nl.nhl.groep16.parser.parsers.ParserInterface;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;
import org.apache.commons.io.input.ReaderInputStream;

import java.awt.*;
import java.io.*;
import java.util.concurrent.TimeUnit;

public class ParserReader extends Thread{

    private String filePath = "";
    private final ParserInterface parser;
    private final BufferedReader reader;

    public ParserReader(ParserInterface parser, Reader reader) {
        this.parser = parser;
        this.reader = new BufferedReader(reader);
    }

    /**
     * Build a ParserReader with only a filePath using the map in the ParserFactory
     *
     * @param filePath
     * @throws Exception
     */
    public ParserReader(String filePath) throws Exception {
        this.filePath = filePath;
        ParserFactory parserFactory = new ParserFactory();
        this.parser = parserFactory.createParser(filePath);
        this.reader = new BufferedReader(new FileReader(filePath));
    }

    /**
     * Writes the current member reader until EOF to the CSVPrinter object
     *
     * @param csvPrinter
     * @throws IOException
     */
    public void writeToCsvPrinter(CSVPrinter csvPrinter) throws IOException {
        String line;
        while ((line = reader.readLine()) != null) {
            String[] out = parser.convertLine(line);
            if (out != null) {
                csvPrinter.printRecord((Object[]) out);
            }
        }
        csvPrinter.flush();
    }

    /**
     * Write the current member reader until EOF or numLines has been reacher to the CSVPrinter object
     *
     * @param csvPrinter
     * @param numLines
     * @throws IOException
     */
    public void writeToCsvPrinter(CSVPrinter csvPrinter, int numLines) throws IOException {
        String line;
        int count = 0;
        while ((line = reader.readLine()) != null && count < numLines) {
            String[] out = parser.convertLine(line);
            if (out != null) {
                csvPrinter.printRecord((Object[]) out);
                count++;
            }
        }
        csvPrinter.flush();
    }

    /**
     *
     * For multi-threading
     *
     */
    @Override
    public void run() {
        long startTime = System.nanoTime();

        System.out.println("Thread is running");
        String runFilePath = filePath;
        Appendable out = System.out;
        try {

            String string = filePath;
            String[] parts = string.split("/");
            String name = parts[parts.length-1];
            String outLocation = "out/" + name;

            out = new FileWriter("./out/" + name.toString() + ".csv");
            CSVPrinter csvPrinter = new CSVPrinter(out, CSVFormat.MYSQL);

            if (Main.linesArg == true) {
                writeToCsvPrinter(csvPrinter, Main.amountOfLinesToParse);
            }
            else {
                writeToCsvPrinter(csvPrinter);
            }
        } catch (IOException e) {
             System.out.println(e.toString());
        }

        long endTime = System.nanoTime();
        long time = TimeUnit.SECONDS.convert((endTime - startTime), TimeUnit.NANOSECONDS);
        System.out.println("Done\nTime: " + time);
    }
}
