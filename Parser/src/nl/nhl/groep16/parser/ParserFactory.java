package nl.nhl.groep16.parser;
import nl.nhl.groep16.parser.parsers.*;

import java.lang.reflect.Constructor;
import java.util.HashMap;
import java.util.Map;
import org.apache.commons.io.FilenameUtils;

public class ParserFactory {

    private Map<String, Class> mapper = new HashMap<>();

    public ParserFactory() {
        mapper.put("actors", ActorParser.class);
        mapper.put("actresses", ActorParser.class);
        mapper.put("editors", EditorParser.class);
        mapper.put("genres", GenreParser.class);
        mapper.put("keywords", KeywordParser.class);
        mapper.put("movies", MovieParser.class);
        mapper.put("mpaa-ratings-reasons", MpaaParser.class);
        mapper.put("plot", PlotParser.class);
        mapper.put("producers", ProducerParser.class);
        mapper.put("ratings", RatingsParser.class);
        mapper.put("writers", WriterParser.class);
        mapper.put("running-times", DurationParser.class);
        mapper.put("directors", EditorParser.class);
        mapper.put("release-dates", ReleaseParser.class);
        mapper.put("biographies", BioParser.class);
        mapper.put("countries", CountryParser.class);
    }

    /**
     *
     * Reflection
     *
     * @param filePath
     * @return
     * @throws Exception
     */
    public ParserInterface createParser(String filePath) throws Exception {
        String basename = FilenameUtils.getBaseName(filePath);

        if(mapper.containsKey(basename)) {
            Class classToCreate = mapper.get(basename);
            Class[] type = {};
            Constructor<?> cons = classToCreate.getConstructor(type);
            return (ParserInterface)cons.newInstance();
        }

        throw new Exception(String.format("Datatype %s not supported", basename));
    }

}
