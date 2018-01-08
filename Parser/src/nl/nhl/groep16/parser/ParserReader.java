package nl.nhl.groep16.parser;

import nl.nhl.groep16.parser.parsers.ParserInterface;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;
import org.apache.commons.io.input.ReaderInputStream;

import java.io.*;

public class ParserReader {

    private final ParserInterface parser;
    private final BufferedReader reader;

    public ParserReader(ParserInterface parser, Reader reader) {
        this.parser = parser;
        this.reader = new BufferedReader(reader);
    }

    public ParserReader(String filePath) throws Exception {
        ParserFactory parserFactory = new ParserFactory();
        this.parser = parserFactory.createParser(filePath);
        this.reader = new BufferedReader(new FileReader(filePath));
    }


    public void writeToCsvPrinter(CSVPrinter csvPrinter) throws IOException {
        String line;
        while ((line = reader.readLine()) != null) {
            String[] out = parser.convertLine(line);
            if (out != null) {
                csvPrinter.printRecord((Object[]) out);
            }
        }
        csvPrinter.flush();
        reader.reset();
    }
}
