package nl.nhl.groep16.parser.parsers;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ReleaseParser extends AbstractParser {

    private Pattern releaseRegex;
    DateFormat formatterLong;
    DateFormat formatterShort;

    public ReleaseParser() {
        this.releaseRegex = Pattern.compile("(^[^\"].*?)\\t\\s*(.*):([1-9].*[0-9])\\t");
        formatterLong = new SimpleDateFormat("dd MMMM yyyy", Locale.US);
        formatterShort = new SimpleDateFormat("yyyy", Locale.US);
    }

    @Override
    public String[] convertLine(String line) {
        return extractRelease(line);
    }

    public String[] extractRelease(String line) {
        Matcher m = releaseRegex.matcher(line);
        if (!m.find()) {
            return null;
        }
        Date date = null;
        try {
            if(m.group(3).length() < 10)
                date = formatterShort.parse(m.group(3));
            else
                date = formatterLong.parse(m.group(3));

        } catch (ParseException e) {
            e.printStackTrace();
        }

        return new String[]{m.group(1), m.group(2), date.toString()};
    }
}
