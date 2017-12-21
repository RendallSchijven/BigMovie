package nl.nhl.groep16.parser.parsers;

import java.util.regex.Pattern;

public class ActorParser extends AbstractParser{

    private final Pattern actorRegex;

    public ActorParser() {
        this.actorRegex = Pattern.compile("^(..*)\\t");
    }

    @Override
    public String convertLine(String line) {
        return null;
    }

}
