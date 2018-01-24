package Nickybot;

import com.rivescript.macro.Subroutine;
import org.json.JSONArray;
import org.json.JSONObject;
import org.telegram.telegrambots.bots.DefaultAbsSender;

import java.util.Iterator;
import java.util.Objects;


/**
 * @author Groep 16
 */
public class JdbcSubroutine implements Subroutine {
    DefaultAbsSender das;

    public JdbcSubroutine(DefaultAbsSender sender) {
        das = sender;
    }

    @Override
    public String call(com.rivescript.RiveScript rs, String[] args) {

        String sql = "";
        String response;
        String result = "";

        if (!args[0].equals("SELECT")) {
            for (int i = 1; i < args.length; i++) {
                sql = sql + " " + args[i];
            }

            sql = sql.trim();
            response = Database.query(sql);
            System.out.println(response);
            JSONArray JsonArray = new JSONArray(response);
            String jsonButtonString;

            switch (args[0]) {
                case "film_list":
                    System.out.println(args[0]);
                    jsonButtonString = "{\"text\":\"The answer is\",\"buttons\":[";

                    for (int i = 0; i < JsonArray.length(); i++) {
                        JSONObject jsonObject = JsonArray.getJSONObject(i);
                        if (i != 0)
                            jsonButtonString += ",";

                        jsonButtonString += "[{\"text\":\"" + jsonObject.getString("Title") + "\",\"callback\":\"movie_id_" + jsonObject.getInt("ID") + "\"}]";
                    }

                    jsonButtonString += "]}";
                    System.out.println(jsonButtonString);
                    InlineKeyboardSubroutine.MakeButtonMessage(rs, das, jsonButtonString);

                    break;
                case "film_info":
                    System.out.println(args[0]);
                    JSONObject movieObject = JsonArray.getJSONObject(0);
                    jsonButtonString = "{\"text\":\"" +
                            "<b>Title :</b> " + movieObject.getString("Title") + "\\n" +
                            "<b>Release year :</b> " + movieObject.getString("ReleaseYear") + "\\n";

                    if (!movieObject.getString("MPAA").equals("null"))
                        jsonButtonString += "<b>MPAA rating :</b> " + movieObject.getString("MPAA") + "\\n";
                    if (!movieObject.getString("Duration").equals("null"))
                        jsonButtonString += "<b>Duration :</b> " + movieObject.getString("Duration") + " minutes\\n";
                    if (!movieObject.getString("Rating").equals("null"))
                        jsonButtonString += "<b>Rating :</b> " + movieObject.getString("Rating") + " with " + movieObject.getString("Votes") + "votes\\n";
                    if (!movieObject.getString("Budget").equals("null"))
                        jsonButtonString += "<b>Budget :</b> " + movieObject.getString("Budget") + " " + movieObject.getString("Currency") + "\\n";

                    jsonButtonString += "\",\"buttons\":[";

                    jsonButtonString += "[{\"text\":\"Show my the trailer?\", \"callback\":\"movie_id_trailer_" + movieObject.getInt("ID") + "\"}]";

                    jsonButtonString += ",[{\"text\":\"What is the cast?\", \"callback\":\"movie_id_actors_" + movieObject.getInt("ID") + "\"}]";

                    jsonButtonString += ",[{\"text\":\"I want to know more about this movie?\", \"callback\":\"movie_info_full_" + movieObject.getInt("ID") + "\"}]";

                    if (!movieObject.getString("Plot").equals("null")) {
                        jsonButtonString += ",[{\"text\":\"What is the story?\", \"callback\":\"movie_id_plot_" + movieObject.getInt("ID") + "\"}]";
                    }
                    jsonButtonString += "]}";
                    System.out.println(jsonButtonString);
                    InlineKeyboardSubroutine.MakeButtonMessage(rs, das, jsonButtonString);
                    break;
                case "actor_list":
                    System.out.println(args[0]);
                    jsonButtonString = "{\"text\":\"The answer is\",\"buttons\":[";

                    for (int i = 0; i < JsonArray.length(); i++) {
                        JSONObject jsonObject = JsonArray.getJSONObject(i);
                        if (i != 0)
                            jsonButtonString += ",";

                        jsonButtonString += "[{\"text\":\"" + jsonObject.getString("Name") + "\",\"callback\":\"person_id_" + jsonObject.getInt("ID") + "\"}]";
                    }


                    jsonButtonString += "]}";
                    System.out.println(jsonButtonString);
                    InlineKeyboardSubroutine.MakeButtonMessage(rs, das, jsonButtonString);

                    break;
            }

        } else {
            for (int i = 0; i < args.length; i++) {
                sql = sql + " " + args[i];
            }
            sql = sql.trim();
            response = Database.query(sql);

            System.out.println(sql);


            JSONArray JsonArray = new JSONArray(response);
            for (int i = 0; i < JsonArray.length(); i++) {
                JSONObject jsonObject = JsonArray.getJSONObject(i);
                Iterator jsonIterator = jsonObject.keys();
                boolean isFirst = true;
                while (jsonIterator.hasNext()) {
                    String key = (String) jsonIterator.next();
                    if (isFirst) {
                        result += key + ": " + jsonObject.getString(key);
                        isFirst = false;
                    } else
                        result += " | " + key + ": " + jsonObject.getString(key);
                }
                result += "\n";
            }
        }
        return result;
    }

}