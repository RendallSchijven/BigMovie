package nl.nhl.groep16.parser.parsers;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RatingsParser implements ParserInterface{

    private final Pattern ratingRegex;

    public RatingsParser() {
        this.ratingRegex = Pattern.compile("\\s(\\d[\\.]\\d)\\s+(.*)");
    }

    @Override
    public String[] convertLine(String line) {
        return extractMovieRating(line);
    }

    public String[] extractMovieRating(String line) {
        Matcher m = ratingRegex.matcher(line);
        if (!m.find()) {
            return null;
        }

        return new String[]{m.group(2), m.group(1)};
    }
}
