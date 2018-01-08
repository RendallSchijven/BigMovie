package nl.nhl.groep16.parser.parsers;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class EditorParser implements ParserInterface{

    private final Pattern editorRegex;
    private final Pattern movieRegex;

    private String currentEditor;

    public EditorParser() {
        this.editorRegex = Pattern.compile("^(\\w+,\\s+\\w+)");
        this.movieRegex = Pattern.compile("\\t+(\\w.*)");
    }

    @SuppressWarnings("Duplicates")
    @Override
    public String[] convertLine(String line) {

        String extractedEditor = extractEditor(line);
        if(extractedEditor != null) {
            currentEditor = extractedEditor;
        }
        if(currentEditor == null) {
            return null;
        }

        String movie = extractMovie(line);

        if(movie == null) {
            return null;
        }

        return new String[]{currentEditor, movie };
    }

    public String extractEditor(String line) {
        Matcher m = editorRegex.matcher(line);
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
