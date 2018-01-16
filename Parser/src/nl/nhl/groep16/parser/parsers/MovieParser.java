package nl.nhl.groep16.parser.parsers;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MovieParser implements ParserInterface{

    private final Pattern movieRegex;

    public MovieParser() {
        this.movieRegex = Pattern.compile("^([^\"].*?)\\t+(\\d+)?");
    }

    @Override
    public String[] convertLine(String line) {

        String[] movie = extractMovie(line);
        if (movie == null) {
            return null;
        }
        return movie;
    }

    public String[] extractMovie(String line) {
        Matcher m = movieRegex.matcher(line);
        if (!m.find()) {
            return null;
        }

        return new String[]{m.group(1), m.group(2)};
    }
}
