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
            if (response.equals("]"))
                return "I can't find any information on that.";
            System.out.println(response);
            JSONArray JsonArray = new JSONArray(response);
            JSONObject jsonObject;
            String jsonButtonString;

            switch (args[0]) {
                case "film_list":
                    System.out.println(args[0]);
                    jsonButtonString = "{\"text\":\"The answer is\",\"buttons\":[";

                    for (int i = 0; i < JsonArray.length(); i++) {
                        jsonObject = JsonArray.getJSONObject(i);
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
                    jsonObject = JsonArray.getJSONObject(0);
                    jsonButtonString = "{\"text\":\"" +
                            "<b>Title :</b> " + jsonObject.getString("Title") + "\\n" +
                            "<b>Release year :</b> " + jsonObject.getString("ReleaseYear") + "\\n";

                    if (!jsonObject.getString("MPAA").equals("null"))
                        jsonButtonString += "<b>MPAA rating :</b> " + jsonObject.getString("MPAA") + "\\n";
                    if (!jsonObject.getString("Duration").equals("null"))
                        jsonButtonString += "<b>Duration :</b> " + jsonObject.getString("Duration") + " minutes\\n";
                    if (!jsonObject.getString("Rating").equals("null"))
                        jsonButtonString += "<b>Rating :</b> " + jsonObject.getString("Rating") + " with " + jsonObject.getString("Votes") + " votes\\n";
                    if (!jsonObject.getString("Budget").equals("null"))
                        jsonButtonString += "<b>Budget :</b> " + jsonObject.getString("Budget") + " " + jsonObject.getString("Currency") + "\\n";

                    jsonButtonString += "\",\"buttons\":[";

                    jsonButtonString += "[{\"text\":\"Show me the trailer?\", \"callback\":\"movie_id_trailer_" + jsonObject.getInt("ID") + "\"}]";

                    jsonButtonString += ",[{\"text\":\"What is the cast?\", \"callback\":\"movie_id_cast_" + jsonObject.getInt("ID") + "\"}]";

                    jsonButtonString += ",[{\"text\":\"Which people work on the movie?\", \"callback\":\"movie_id_staff_" + jsonObject.getInt("ID") + "\"}]";

                    if (!jsonObject.getString("Plot").equals("null")) {
                        jsonButtonString += ",[{\"text\":\"What is the story?\", \"callback\":\"movie_id_plot_" + jsonObject.getInt("ID") + "\"}";
                        jsonButtonString += ",{\"text\":\"Calculate MPAA\", \"callback\":\"movie_id_mpaa_" + jsonObject.getInt("ID") + "\"}]";
                    }
                    jsonButtonString += "]}";
                    System.out.println(jsonButtonString);
                    InlineKeyboardSubroutine.MakeButtonMessage(rs, das, jsonButtonString);
                    break;
                case "film_info_cast":
                    System.out.println(args[0]);
                    jsonObject = JsonArray.getJSONObject(0);
                    jsonButtonString = "{\"text\":\"" +
                            "<b>Title :</b> " + jsonObject.getString("Title") + "\\n" +
                            "<b>Plot :</b> " + jsonObject.getString("Plot") + "\\n";

                    jsonButtonString += "\",\"buttons\":[";
                    jsonButtonString += "[{\"text\":\"Calculate MPAA\", \"callback\":\"movie_id_mpaa_" + jsonObject.getInt("ID") + "\"}]";

                    jsonButtonString += "]}";
                    System.out.println(jsonButtonString);
                    InlineKeyboardSubroutine.MakeButtonMessage(rs, das, jsonButtonString);
                    break;
                case "film_plot":
                    System.out.println(args[0]);
                    jsonObject = JsonArray.getJSONObject(0);
                    jsonButtonString = "{\"text\":\"" +
                            "<b>Title :</b> " + jsonObject.getString("Title") + "\\n" +
                            "<b>Plot :</b> " + jsonObject.getString("Plot") + "\\n";

                    jsonButtonString += "\",\"buttons\":[";
                    jsonButtonString += "[{\"text\":\"Calculate MPAA\", \"callback\":\"movie_id_mpaa_" + jsonObject.getInt("ID") + "\"}]";

                    jsonButtonString += "]}";
                    System.out.println(jsonButtonString);
                    InlineKeyboardSubroutine.MakeButtonMessage(rs, das, jsonButtonString);
                    break;
                case "person_list":
                    System.out.println(args[0]);
                    jsonButtonString = "{\"text\":\"The persons are\",\"buttons\":[";

                    for (int i = 0; i < JsonArray.length(); i++) {
                        jsonObject = JsonArray.getJSONObject(i);
                        if (i != 0)
                            jsonButtonString += ",";

                        jsonButtonString += "[{\"text\":\"" + jsonObject.getString("Name") + "\",\"callback\":\"person_id_" + jsonObject.getInt("ID") + "\"}]";
                    }

                    jsonButtonString += "]}";
                    System.out.println(jsonButtonString);
                    InlineKeyboardSubroutine.MakeButtonMessage(rs, das, jsonButtonString);
                    break;
                case "person_info":

                    System.out.println(args[0]);
                    jsonObject = JsonArray.getJSONObject(0);
                    jsonButtonString = "{\"text\":\"" +
                            "<b>Title :</b> " + jsonObject.getString("Name") + "\\n";

                    if (!jsonObject.getString("BirthDay").equals("null")) {
                        int age = 0;
                        if (!jsonObject.getString("DeathDay").equals("null")){

                        }
                        jsonButtonString += "<b>Age :</b> " + age + "\\n";
                        jsonButtonString += "<b>Birth day :</b> " + jsonObject.getString("BirthDay") + "\\n";

                    }
                    if (!jsonObject.getString("DeathDay").equals("null"))
                        jsonButtonString += "<b>Death day :</b> " + jsonObject.getString("DeathDay") + "\\n";
                    if (!jsonObject.getString("Sex").equals("null"))
                        jsonButtonString += "<b>Sex :</b> " + jsonObject.getString("Sex") + "\\n";

                    jsonButtonString += "\",\"buttons\":[";
                    jsonButtonString += "[{\"text\":\"Movies worked on\", \"callback\":\"actor_id_movie_" + jsonObject.getInt("ID") + "\"}]";

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

            System.out.println(response);

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