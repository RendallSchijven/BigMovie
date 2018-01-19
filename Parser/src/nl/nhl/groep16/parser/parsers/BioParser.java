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

    private String currentPerson = null;
    private String currentBirthDay = null;
    private String currentDeathDay = null;

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

        if(person != null && currentPerson != null && person != currentPerson) {
            String[] rValue = new String[]{currentPerson, currentBirthDay, currentDeathDay};
            currentPerson = person;
            currentBirthDay = null;
            currentDeathDay = null;
            return rValue;
        }

        if(person != null) {
            currentPerson = person;
        }

        String birthDay = extractBirthDay(line);
        if(birthDay != null) {
            currentBirthDay = birthDay;
        }

        String deathDay = extractDeathDay(line);
        if(deathDay != null) {
            currentDeathDay = deathDay;
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

        try {
            return formatterFinal.format(formatter.parse(m.group(1)));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return  null;
    }

    public String extractDeathDay(String line) {
        Matcher m = deathDayRegex.matcher(line);
        if (!m.find()) {
            return null;
        }

        try {
            return formatterFinal.format(formatter.parse( m.group(1)));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }
}
