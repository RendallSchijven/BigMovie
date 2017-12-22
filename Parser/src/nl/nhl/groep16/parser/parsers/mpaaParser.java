package nl.nhl.groep16.parser.parsers;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class mpaaParser extends AbstractParser{

    private final Pattern ratingRegex;
    private final Pattern moviesRegex;

    public mpaaParser() {
        this.ratingRegex = Pattern.compile("^[^\"](.*?)\\t");
        this.moviesRegex = Pattern.compile("^[^\"](.*?)\\t");
    }


    @Override
    public String[] convertLine(String line) {
        return new String[0];
    }

    public String extractMovies(String line) {
        Matcher m = moviesRegex.matcher(line);
        if (!m.find()) {
            return null;
        }

        return m.group(0).replaceAll("\t", "");
    }

    public String extractRatings(String line) {
        Matcher m = ratingRegex.matcher(line);
        if (!m.find()) {
            return null;
        }

        return m.group(0).replaceAll("\t", "");
    }
}
}
