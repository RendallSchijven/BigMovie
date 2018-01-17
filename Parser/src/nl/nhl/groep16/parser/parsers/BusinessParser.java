package nl.nhl.groep16.parser.parsers;

import java.util.Currency;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class BusinessParser implements ParserInterface {

    private final Pattern regex;

    private String currentMovie = "";
    private String currentBudget = "";
    private String currentCurrency = "";

    public BusinessParser() {
        this.regex = Pattern.compile("MV:.([^\"].+\\w.)|BT:.(...).(.+).");
    }

    public String[] extractEditor(String line) {
        Matcher m = regex.matcher(line);
        if(!m.find()) {
            return null;
        }
        return new String[]{m.group(1), m.group(2)};
    }

    @Override
    public String[] convertLine(String line) {
        String movie = extractMovie(line);

        if (movie != null) {
            currentMovie = movie;
            currentBudget = null;
            currentCurrency = null;
        } else {
            String[] budget = extractBudget(line);
            if (budget != null) {
                if (currentBudget == null) {
                    currentBudget = budget[0];
                    currentCurrency = budget[1];
                }
                else {
                    currentBudget += " " + budget[0];
                    currentCurrency += " " +  budget[1];
                }
            } else if (currentBudget != null) {
                String returnBudget = new String(currentBudget);
                String returnCurrency = new String(currentCurrency);
                returnBudget = returnBudget.replaceAll(",", "");
                currentBudget = null;
                currentCurrency = null;
                return new String[]{currentMovie, returnBudget, returnCurrency};
            }
        }
        return null;
    }

    public String extractMovie(String line) {
        Matcher m = regex.matcher(line);
        if (!m.find()) {
            return null;
        }

        return m.group(1);
    }

    public String[] extractBudget(String line) {
        Matcher m = regex.matcher(line);
        if (!m.find()) {
            return null;
        }

        String currency = m.group(2);
        String amount = m.group(3);
        return new String[] {amount, currency};
    }


}
