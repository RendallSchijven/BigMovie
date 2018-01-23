package nl.nhl.groep16.parser.parsers;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MovieParser implements ParserInterface{

    private final Pattern movieRegex;

    public MovieParser() {
        this.movieRegex = Pattern.compile("^([^\"].*?)\\t+(\\d+)?");
    }

    /**
     * Converts a line into a String Array representing the values of the CSV columns.
     * @param String line
     * @return String
     */
    @Override
    public String[] convertLine(String line) {

        String[] movie = extractMovie(line);
        if (movie == null) {
            return null;
        }
        return movie;
    }

    /**
     * Extracts the movie from the line returns null if not found
     * @param String line
     * @return String[]
     */
    public String[] extractMovie(String line) {
        Matcher m = movieRegex.matcher(line);
        if (!m.find()) {
            return null;
        }

        return new String[]{m.group(1), m.group(2)};
    }
}
