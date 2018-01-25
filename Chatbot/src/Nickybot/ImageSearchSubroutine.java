package Nickybot;

import com.rivescript.macro.Subroutine;
import com.sun.javafx.fxml.builder.URLBuilder;

import org.apache.commons.io.IOUtils;
import org.apache.http.client.utils.URIBuilder;
import org.json.JSONObject;

import java.io.FileInputStream;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.charset.Charset;
import java.util.Properties;
import java.util.Scanner;
import java.net.URL;

/**
 * @author Groep 16
 */
public class ImageSearchSubroutine implements Subroutine {

    @Override
    public String call(com.rivescript.RiveScript rs, String[] args) {

        // Build search string
        String keyword = "";
        for (String arg : args)
            keyword = keyword + arg + " ";

        return getImage(keyword, false);

    }

    public static String getImage(String keyword, boolean outside){
        // Load properties file
        String rootPath = Thread.currentThread().getContextClassLoader().getResource("").getPath();
        String appConfigPath = rootPath + "resources/app.properties";

        // Init variables
        String thumbnail = "";
        URIBuilder url = null;

        try {
            // Load properties file
            Properties appProps = new Properties();
            appProps.load(new FileInputStream(appConfigPath));

            url = new URIBuilder("https://api.qwant.com/api/search/images");
            url.addParameter("count", "1");
            url.addParameter("offset", "1");
            url.addParameter("q", keyword);

            // Get JSON result from GET request
            JSONObject json = new JSONObject(IOUtils.toString(url.build(), Charset.forName("UTF-8")));

            System.out.println(json.toString(4));
            // Get Video ID from JSON
            thumbnail = json.getJSONObject("data").getJSONObject("result").getJSONArray("items").getJSONObject(0).getString("thumbnail");

        } catch (URISyntaxException | IOException e) {
            e.printStackTrace();
        }

        if (!thumbnail.isEmpty()) {
            // Return youtube link for trailer video
            System.out.println(thumbnail);
            if (outside)
                return "<a href=\'https:" + thumbnail + "\'>image</a>";

            return "<a href=\"https:" + thumbnail + "\">image</a>";
        }
        return "My brain stopped working";
    }
}