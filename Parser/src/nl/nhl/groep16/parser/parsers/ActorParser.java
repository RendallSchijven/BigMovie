package nl.nhl.groep16.parser.parsers;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ActorParser implements ParserInterface{

    private final Pattern actorRegex;
    private final Pattern movieRegex;

    private String currentActor;

    public ActorParser() {
        this.actorRegex = Pattern.compile("^(\\S[^\\t]+)\\t");
        this.movieRegex = Pattern.compile("\\t+([^\"]\\w.*(?<=[0-9IV\\?])\\))");
    }

    /**
     * Converts a string line into an array of strings (columns) which can be used to generate CSV a row.
     *
     * @param String line
     * @return String[]
     */
    @Override
    public String[] convertLine(String line) {

        String extractedActor = extractActor(line);
        if(extractedActor != null) {
            currentActor = extractedActor;
        }
        if(currentActor == null) {
            return null;
        }

        String movie = extractMovie(line);

        if(movie == null) {
            return null;
        }

        return new String[]{ currentActor, movie };
    }

    /**
     * Extracts the actor from the current line, return null if not found
     * @param String line
     * @return String
     */
    public String extractActor(String line) {
        Matcher m = actorRegex.matcher(line);
        if(!m.find()) {
            return null;
        }
        return m.group(1);
    }

    /**
     * Extracts the movie from the current line, returns null if not found
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
