package nl.nhl.groep16.parser.parsers;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MovieParser implements ParserInterface{

    private final Pattern movieRegex;

    public MovieParser() {
        this.movieRegex = Pattern.compile("^[^\"](.*?)\\t");
    }

    @Override
    public String[] convertLine(String line) {

        String movie = extractMovie(line);
        if (movie == null) {
            return null;
        }
        return new String[]{movie};
    }

    public String extractMovie(String line) {
        Matcher m = movieRegex.matcher(line);
        if (!m.find()) {
            return null;
        }

        return m.group(0).replaceAll("\t", "");
    }
}
