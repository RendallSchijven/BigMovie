package nl.nhl.groep16.parser.parsers;


import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class GenreParser extends AbstractParser {

    private String ignoreUntilFound;
    private boolean isIgnoreUntilFoundFound = false;

    private Pattern genreParser;

    public GenreParser() {
        this.ignoreUntilFound = "8: THE GENRES LIST";
        this.genreParser = Pattern.compile("(^[^\"].*[^\\t])\\t+(\\S+)");
    }

    @SuppressWarnings("Duplicates")
    @Override
    public String[] convertLine(String line) {

        if(!isIgnoreUntilFoundFound) {
            if(line.equals(ignoreUntilFound)) {
                isIgnoreUntilFoundFound = true;
            }
            return null;
        }

        Matcher m = genreParser.matcher(line);
        if(!m.matches()){
            return null;
        }

        String movie = m.group(1);
        String genre = m.group(2);

        return new String[]{movie, genre};
    }
}
