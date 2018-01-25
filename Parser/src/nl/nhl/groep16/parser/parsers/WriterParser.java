package nl.nhl.groep16.parser.parsers;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class WriterParser implements ParserInterface{

    private final Pattern writerRegex;
    private final Pattern movieRegex;

    private String currentWriter;

    public WriterParser() {
        this.writerRegex = Pattern.compile("^(.*,.*)\\t");
        this.movieRegex = Pattern.compile("\\t+(.*?)  ");
    }

    /**
     * Converts a line into a Array of Strings representing the columns of a CSV
     * @param String line
     * @return String[]
     */
    @SuppressWarnings("Duplicates")
    @Override
    public String[] convertLine(String line) {

        String extractedWriter = extractWriter(line);
        if(extractedWriter != null) {
            currentWriter = extractedWriter;
        }
        if(currentWriter == null) {
            return null;
        }

        String movie = extractMovie(line);

        if(movie == null) {
            return null;
        }

        return new String[]{currentWriter, movie };
    }

    /**
     * Extracts the writer from the line, if non-existant return null
     * @param String line
     * @return String
     */
    public String extractWriter(String line) {
        Matcher m = writerRegex.matcher(line);
        if(!m.find()) {
            return null;
        }
        return m.group(1);
    }

    /**
     * Try and find a movie in the line, if not found return null
     * @param String line
     * @return String
     */
    public String extractMovie(String line) {
        Matcher m = movieRegex.matcher(line);
        if(!m.find()) {
            return null;
        }
        return m.group(1);
    }

}
