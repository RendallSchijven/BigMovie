package nl.nhl.groep16.parser.parsers;


import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class GenreParser extends AbstractParser {

    Pattern genreParser;

    public GenreParser() {
        this.genreParser = Pattern.compile("^(.*)(\\t+)(.+)");
    }

    @Override
    public String[] convertLine(String line) {
        Matcher m = genreParser.matcher(line);
        if(!m.matches()){
            return null;
        }

        String movie = m.group(1).replace("\t", "");
        String genre = m.group(3);

        return new String[]{movie, genre};
    }
}
