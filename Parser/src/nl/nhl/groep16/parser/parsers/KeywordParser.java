package nl.nhl.groep16.parser.parsers;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class KeywordParser extends AbstractParser {

    private String ignoreUntilFound;
    private boolean isIgnoreUntilFoundFound = false;

    private Pattern keywordParser;

    public KeywordParser() {
        this.ignoreUntilFound = "8: THE KEYWORDS LIST";
        this.keywordParser = Pattern.compile("(^[^\"].*[^\\t])\\t+(\\S+)");
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

        Matcher m = keywordParser.matcher(line);
        if(!m.matches()){
            return null;
        }

        String movie = m.group(1);
        String keyword = m.group(2);

        return new String[]{movie, keyword};
    }
}
