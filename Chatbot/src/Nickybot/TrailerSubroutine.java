package Nickybot;

import com.rivescript.macro.Subroutine;
import org.apache.commons.io.IOUtils;
import org.apache.http.client.utils.URIBuilder;
import org.json.JSONObject;

import java.io.FileInputStream;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.charset.Charset;
import java.util.Properties;

/**
 * @author Groep 16
 */
public class TrailerSubroutine implements Subroutine {

    @Override
    public String call(com.rivescript.RiveScript rs, String[] args) {
        // Load properties file
        String rootPath = Thread.currentThread().getContextClassLoader().getResource("").getPath();
        String appConfigPath = rootPath + "resources/app.properties";

        // Build search string
        String keyword = "";
        for (String arg : args) keyword = keyword + arg + " ";

        // Init variables
        String id = "";
        URIBuilder url = null;

        try {
            // Load properties file
            Properties appProps = new Properties();
            appProps.load(new FileInputStream(appConfigPath));

            // Build URL
            url = new URIBuilder("https://www.googleapis.com/youtube/v3/search");
            url.addParameter("part", "snippet");
            url.addParameter("maxResults", "1");
            url.addParameter("q", keyword + "trailer");
            url.addParameter("type", "video");
            url.addParameter("key", appProps.getProperty("yt_apiKey"));
            System.out.println(url.build());

            // Get JSON result from GET request
            JSONObject json = new JSONObject(IOUtils.toString(url.build(), Charset.forName("UTF-8")));

            // Check if JSON has search results
            if (json.getJSONArray("items").length() == 0){
                return "hmm, can't seem to find a trailer for this";
            }

            // Get Video ID from JSON
            id = json.getJSONArray("items").getJSONObject(0).getJSONObject("id").getString("videoId");

        } catch (URISyntaxException | IOException e) {
            e.printStackTrace();
        }

        if(!id.isEmpty()){
            // Return youtube link for trailer video
            return "<a href=\"https://www.youtube.com/watch?v=" + id + "\">trailer</a>";
        }
        return "My brain stopped working";
    }
}