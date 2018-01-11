package nl.nhl.groep16.parser.parsers;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class BioParser implements ParserInterface {
    String regex = "^DB:\\s(\\d{1,2}\\s\\w+\\s\\d{4})";


    private final Pattern birthDayRegex;
    private final Pattern personRegex;
    DateFormat formatter;
    DateFormat formatterFinal;

    private String currentPerson = "";

    public BioParser() {
        this.birthDayRegex = Pattern.compile("^DB:\\s(\\d{1,2}\\s\\w+\\s\\d{4})");
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
            if (birthDayString != null) {
                Date birthDay = null;
                try {
                    birthDay = formatter.parse(birthDayString);
                } catch (ParseException e) {
                    e.printStackTrace();
                }

                return new String[]{currentPerson, formatterFinal.format(birthDay)};
            }
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
}
