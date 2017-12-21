package nl.nhl.groep16.parser.input;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by human on 20/12/17.
 */
public class ActorFile {

    private Pattern actorRegex;

    private BufferedReader input;

    public ActorFile(BufferedReader input) {
        this.input = input;
        this.actorRegex = Pattern.compile("^(..*)\\t");
    }

    public String getNextActor() {
        String line;
        try {
            line = this.input.readLine();
            while(line != null) {
                String actor = extractActor(line);
                if(actor != null && !actor.equals("")) {
                    return actor;
                }
                line = this.input.readLine();
            }
        } catch (IOException e) {
            return null;
        }
        return null;
    }

    private String extractActor(String line) {
        Matcher m = actorRegex.matcher(line);
        if(m.find()) {
            return m.group(0).replaceAll("\t", "");
        }
        return null;
    }

}
