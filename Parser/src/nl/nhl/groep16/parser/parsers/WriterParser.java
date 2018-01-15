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

    public String extractWriter(String line) {
        Matcher m = writerRegex.matcher(line);
        if(!m.find()) {
            return null;
        }
        return m.group(1);
    }

    public String extractMovie(String line) {
        Matcher m = movieRegex.matcher(line);
        if(!m.find()) {
            return null;
        }
        return m.group(1);
    }

}
