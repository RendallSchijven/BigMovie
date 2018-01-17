package nl.nhl.groep16.parser.parsers;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class BioParser implements ParserInterface {

    private String regex = "^DB:\\s(\\d{1,2}\\s\\w+\\s\\d{4})";


    private final Pattern birthDayRegex;
    private final Pattern deathDayRegex;
    private final Pattern personRegex;
    DateFormat formatter;
    DateFormat formatterFinal;

    private String currentPerson = "";

    public BioParser() {
        this.birthDayRegex = Pattern.compile("^DB:\\s(\\d{1,2}\\s\\w+\\s\\d{4})");
        this.deathDayRegex = Pattern.compile("^DD:\\s(\\d{1,2}\\s\\w+\\s\\d{4})");
        this.personRegex = Pattern.compile("^NM:\\s(.[^\"].*)");
        formatter = new SimpleDateFormat("dd MMMM yyyy", Locale.US);
        formatterFinal = new SimpleDateFormat("yyyy-MM-dd", Locale.US);
    }


    @Override
    public String[] convertLine(String line) {
        String person = extractPerson(line);

        if (person != null) {
            currentPerson = person;
        } else {
            String birthDayString = extractBirthDay(line);
            String birthDay = null;
            if (birthDayString != null) {
                try {
                    birthDay = formatterFinal.format(formatter.parse(birthDayString));
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }

            String deathDayString = extractDeathDay(line);
            String deathDay = null;
            if (deathDayString != null) {
                try {
                    deathDay = formatterFinal.format(formatter.parse(deathDayString));
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }
            return new String[]{currentPerson, birthDay, deathDay};
        }
        return null;
    }

    public String extractPerson(String line) {
        Matcher m = personRegex.matcher(line);
        if (!m.find()) {
            return null;
        }

        return m.group(1);
    }

    public String extractBirthDay(String line) {
        Matcher m = birthDayRegex.matcher(line);
        if (!m.find()) {
            return null;
        }

        return m.group(1);
    }

    public String extractDeathDay(String line) {
        Matcher m = deathDayRegex.matcher(line);
        if (!m.find()) {
            return null;
        }

        return m.group(1);
    }
}
