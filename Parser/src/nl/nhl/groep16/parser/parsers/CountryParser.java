package nl.nhl.groep16.parser.parsers;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CountryParser implements ParserInterface{

    private Pattern countryParser;

    public CountryParser() {
        this.countryParser = Pattern.compile("(^[^\"].*?)\\t+(.*)");
    }

    @SuppressWarnings("Duplicates")
    @Override
    public String[] convertLine(String line) {
        Matcher m = countryParser.matcher(line);
        if(!m.matches()){
            return null;
        }

        String movie = m.group(1);
        String country = m.group(2);

        return new String[]{movie, country};
    }
}

