package nl.nhl.groep16.parser.parsers;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MpaaParser implements ParserInterface{

    private final Pattern ratingRegex;
    private final Pattern moviesRegex;

    private String currentMovie = "";
    private String currentRating = "";

    public MpaaParser() {
        this.ratingRegex = Pattern.compile("RE:\\sRated\\s([^\\s]+)");
        this.moviesRegex = Pattern.compile("MV:\\s(.[^\"].*)");
    }


    /**
     * Converts a line into CSV columns
     * @param String line
     * @return String[]
     */
    @Override
    public String[] convertLine(String line) {
        String movie = extractMovie(line);

        if (movie != null) {
            currentMovie = movie;
            currentRating = "";
        } else {
            String rating = extractRatings(line);
            if (rating != null) {
                currentRating = rating;
                return new String[]{currentMovie, currentRating};
            }
        }
        return null;
    }

    /**
     * Extracts a movie from the line, returns null if not found
     * @param String line
     * @return String
     */
    public String extractMovie(String line) {
        Matcher m = moviesRegex.matcher(line);
        if (!m.find()) {
            return null;
        }

        return m.group(1);
    }

    /**
     * Extracts rating from the line, returns null if not found
     * @param String line
     * @return String
     */
    public String extractRatings(String line) {
        Matcher m = ratingRegex.matcher(line);
        if (!m.find()) {
            return null;
        }

        return m.group(1);
    }
}