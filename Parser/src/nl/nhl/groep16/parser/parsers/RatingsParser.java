package nl.nhl.groep16.parser.parsers;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RatingsParser implements ParserInterface{

    private final Pattern ratingRegex;

    public RatingsParser() {
        this.ratingRegex = Pattern.compile("\\s(\\d+)\\s+(\\d[\\.]\\d)\\s+(.*)");
    }

    /**
     * Converts a line into a Array of Strings representing the columns of a CSV
     * @param String line
     * @return String[]
     */
    @Override
    public String[] convertLine(String line) {
        return extractMovieRating(line);
    }

    /**
     * Converts a line into a Array of Strings representing the columns of a CSV
     * @param String line
     * @return String[]
     */
    public String[] extractMovieRating(String line) {
        Matcher m = ratingRegex.matcher(line);
        if (!m.find()) {
            return null;
        }

        return new String[]{m.group(3), m.group(2), m.group(1)};
    }
}
