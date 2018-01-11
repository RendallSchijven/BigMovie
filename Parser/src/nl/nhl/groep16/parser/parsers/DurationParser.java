package nl.nhl.groep16.parser.parsers;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DurationParser implements ParserInterface{

    private final Pattern regex;


    public DurationParser() {
        this.regex = Pattern.compile("^([^\"].*?)\\t+.*?(\\d+)");
    }

    @SuppressWarnings("Duplicates")
    @Override
    public String[] convertLine(String line) {
        String[] result = extractEditor(line);
        if(result == null) {
            return null;
        }

        return result;
    }

    public String[] extractEditor(String line) {
        Matcher m = regex.matcher(line);
        if(!m.find()) {
            return null;
        }
        return new String[]{m.group(1), m.group(2)};
    }


}
