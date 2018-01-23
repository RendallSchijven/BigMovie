package Nickybot;

import com.rivescript.macro.Subroutine;
import org.apache.commons.io.IOUtils;
import org.apache.http.client.utils.URIBuilder;
import org.json.JSONObject;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.charset.Charset;

/**
 * @author Groep 16
 */
public class TrailerSubroutine implements Subroutine {

    @Override
    public String call(com.rivescript.RiveScript rs, String[] args) {
        String keyword = "";
        for (String arg : args) keyword = keyword + arg + "%20";
        //TODO add to properties file
        String apiKey = "AIzaSyCtpI8KqxHAUexIJ1iGXcVa2X8-x37beg0";
        String id = "";
        URIBuilder url = null;
        try {
            url = new URIBuilder("https://www.googleapis.com/youtube/v3/search");
            url.addParameter("part", "snippet");
            url.addParameter("maxResults", "1");
            url.addParameter("q", keyword + "trailer");
            url.addParameter("type", "video");
            url.addParameter("key", apiKey);

            JSONObject json = new JSONObject(IOUtils.toString(url.build(), Charset.forName("UTF-8")));

            if (json.getJSONArray("items").length() == 0){
                return "hmm, can't seem to find a trailer for this";
            }

            id = json.getJSONArray("items").getJSONObject(0).getJSONObject("id").getString("videoId");

        } catch (URISyntaxException | IOException e) {
            e.printStackTrace();
        }

        if(!id.isEmpty()){
            return "https://www.youtube.com/watch?v=" + id;
        }
        return "My brain stopped working";
    }
}