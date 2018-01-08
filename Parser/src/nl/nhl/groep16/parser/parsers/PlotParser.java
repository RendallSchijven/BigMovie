package nl.nhl.groep16.parser.parsers;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PlotParser implements ParserInterface{

    private final Pattern plotRegex;
    private final Pattern moviesRegex;

    private String currentMovie = "";
    private String currentPlot = "";

    public PlotParser() {
        this.plotRegex = Pattern.compile("PL: (.[^\"].*)");
        this.moviesRegex = Pattern.compile("MV: (.[^\"].*)");
    }

    @Override
    public String[] convertLine(String line) {
        String movie = extractMovie(line);

        if (movie != null) {
            currentMovie = movie;
            currentPlot = null;
        } else {
            String plot = extractPlot(line);
            if (plot != null) {
                if (currentPlot == null)
                    currentPlot = plot;
                else
                    currentPlot += " " + plot;
            } else if (currentPlot != null) {
                String returnPlot = new String(currentPlot);
                currentPlot = null;
                return new String[]{currentMovie, returnPlot};
            }
        }
        return null;
    }

    public String extractMovie(String line) {
        Matcher m = moviesRegex.matcher(line);
        if (!m.find()) {
            return null;
        }

        return m.group(1);
    }

    public String extractPlot(String line) {
        Matcher m = plotRegex.matcher(line);
        if (!m.find()) {
            return null;
        }

        return m.group(1);
    }
}
