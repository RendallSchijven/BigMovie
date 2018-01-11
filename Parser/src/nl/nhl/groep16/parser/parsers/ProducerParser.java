package nl.nhl.groep16.parser.parsers;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ProducerParser implements ParserInterface{

    private final Pattern producerRegex;
    private final Pattern infoRegex;

    private String currentProducer;

    public ProducerParser() {
        this.producerRegex = Pattern.compile("^(.*,.*)\\t");
        this.infoRegex = Pattern.compile("\\t+(\\w.*)\\s{2,}\\(([^\\)]+)\\).*");
    }

    @SuppressWarnings("Duplicates")
    @Override
    public String[] convertLine(String line) {

        String extractedProducer = extractProducer(line);
        if(extractedProducer != null) {
            currentProducer = extractedProducer;
        }
        if(currentProducer == null) {
            return null;
        }

        String[] extractedInfo = extractInfo(line);

        if(extractedInfo == null) {
            return null;
        }

        return new String[]{currentProducer, extractedInfo[0], extractedInfo[1]};
    }

    public String extractProducer(String line) {
        Matcher m = producerRegex.matcher(line);
        if(!m.find()) {
            return null;
        }
        return m.group(1);
    }

    public String[] extractInfo(String line) {
        Matcher m = infoRegex.matcher(line);
        if(!m.find()) {
            return null;
        }
        return new String[]{m.group(1),m.group(2)};
    }

}
