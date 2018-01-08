package nl.nhl.groep16.parser.parsers;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ActorParser implements ParserInterface{

    private final Pattern actorRegex;
    private final Pattern movieRegex;

    private String currentActor;

    public ActorParser() {
        this.actorRegex = Pattern.compile("^(\\w+,\\s+\\w+)");
        this.movieRegex = Pattern.compile("\\t+(\\w.*)");
    }

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

    public String extractActor(String line) {
        Matcher m = actorRegex.matcher(line);
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
