package Nickybot;

import com.rivescript.macro.Subroutine;
import org.json.JSONArray;
import org.json.JSONObject;
import org.telegram.telegrambots.bots.DefaultAbsSender;

import java.time.LocalDate;
import java.time.Period;
import java.util.Iterator;


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
        String message = "";

        if (!args[0].equals("SELECT")) {
            for (int i = 1; i < args.length; i++) {
                sql = sql + " " + args[i];
            }

            if (args[0].equals("-m") || args[1].equals("-m")) {
                message = sql.substring(sql.lastIndexOf("-m") + 1, sql.indexOf("SELECT") - 1);
                sql = sql.substring(sql.indexOf("SELECT"), sql.length() - 1);
            }

            sql = sql.trim();
            response = Database.query(sql);
            if (response.equals("]"))
                return "I can't find any information on that.";

            JSONArray JsonArray = new JSONArray(response);
            JSONObject jsonObject;
            String jsonButtonString;

            switch (args[0]) {
                case "film_list":
                    if (message.isEmpty()) {
                        jsonButtonString = "{\"text\":\"The answer is\",\"buttons\":[";
                    } else {
                        jsonButtonString = "{\"text\":\"" + message + "\",\"buttons\":[";
                    }

                    String jsonActorString = "{\"text\":\"Played in\",\"buttons\":[";
                    String jsonDirectorString = "{\"text\":\"Directed\",\"buttons\":[";
                    String jsonProducerString = "{\"text\":\"Produced\",\"buttons\":[";
                    String jsonWriterString = "{\"text\":\"Wrote\",\"buttons\":[";
                    String jsonEditorString = "{\"text\":\"Edited\",\"buttons\":[";

                    int jsonButtonCount = 0;
                    int jsonActorCount = 0;
                    int jsonDirectorCount = 0;
                    int jsonProducerCount = 0;
                    int jsonWriterCount = 0;
                    int jsonEditorCount = 0;
                    int maxFilms = 20;

                    for (int i = 0; i < JsonArray.length(); i++) {
                        jsonObject = JsonArray.getJSONObject(i);
                        if (jsonObject.has("Role")) {
                            switch (jsonObject.getString("Role")) {
                                case "actor":
                                    if (jsonActorCount < maxFilms)
                                        jsonActorString += "[{\"text\":\"" + jsonObject.getString("Title") + "\",\"callback\":\"movie_id_" + jsonObject.getInt("ID") + "\"}],";
                                    jsonActorCount++;
                                    break;
                                case "director":
                                    if (jsonDirectorCount < maxFilms)
                                        jsonDirectorString += "[{\"text\":\"" + jsonObject.getString("Title") + "\",\"callback\":\"movie_id_" + jsonObject.getInt("ID") + "\"}],";
                                    jsonDirectorCount++;
                                    break;
                                case "producer":
                                    if (jsonProducerCount < maxFilms)
                                        jsonProducerString += "[{\"text\":\"" + jsonObject.getString("Title") + "\",\"callback\":\"movie_id_" + jsonObject.getInt("ID") + "\"}],";
                                    jsonProducerCount++;
                                    break;
                                case "writer":
                                    if (jsonWriterCount < maxFilms)
                                        jsonWriterString += "[{\"text\":\"" + jsonObject.getString("Title") + "\",\"callback\":\"movie_id_" + jsonObject.getInt("ID") + "\"}],";
                                    jsonWriterCount++;
                                    break;
                                case "editor":
                                    if (jsonEditorCount < maxFilms)
                                        jsonEditorString += "[{\"text\":\"" + jsonObject.getString("Title") + "\",\"callback\":\"movie_id_" + jsonObject.getInt("ID") + "\"}],";
                                    jsonEditorCount++;
                                    break;
                                default:
                                    if (jsonButtonCount < maxFilms)
                                        jsonButtonString += "[{\"text\":\"" + jsonObject.getString("Title") + "\",\"callback\":\"movie_id_" + jsonObject.getInt("ID") + "\"}],";
                                    jsonButtonCount++;
                                    break;
                            }
                        } else
                            jsonButtonString += "[{\"text\":\"" + jsonObject.getString("Title") + "\",\"callback\":\"movie_id_" + jsonObject.getInt("ID") + "\"}],";
                    }

                    jsonButtonString = jsonButtonString.substring(0, jsonButtonString.length() - 1);
                    jsonActorString = jsonActorString.substring(0, jsonActorString.length() - 1);
                    jsonDirectorString = jsonDirectorString.substring(0, jsonDirectorString.length() - 1);
                    jsonProducerString = jsonProducerString.substring(0, jsonProducerString.length() - 1);
                    jsonWriterString = jsonWriterString.substring(0, jsonWriterString.length() - 1);
                    jsonEditorString = jsonEditorString.substring(0, jsonEditorString.length() - 1);

                    jsonButtonString += "]}";
                    jsonActorString += "]}";
                    jsonDirectorString += "]}";
                    jsonProducerString += "]}";
                    jsonWriterString += "]}";
                    jsonEditorString += "]}";

                    System.out.println(jsonButtonString);
                    if (jsonButtonString.length() > 40)
                        InlineKeyboardSubroutine.MakeButtonMessage(rs, das, jsonButtonString);
                    if (jsonActorString.length() > 40)
                        InlineKeyboardSubroutine.MakeButtonMessage(rs, das, jsonActorString);
                    if (jsonDirectorString.length() > 40)
                        InlineKeyboardSubroutine.MakeButtonMessage(rs, das, jsonDirectorString);
                    if (jsonProducerString.length() > 40)
                        InlineKeyboardSubroutine.MakeButtonMessage(rs, das, jsonProducerString);
                    if (jsonWriterString.length() > 40)
                        InlineKeyboardSubroutine.MakeButtonMessage(rs, das, jsonWriterString);
                    if (jsonEditorString.length() > 40)
                        InlineKeyboardSubroutine.MakeButtonMessage(rs, das, jsonEditorString);

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
                    if (message.isEmpty()) {
                        jsonButtonString = "{\"text\":\"The persons are\",\"buttons\":[";
                    } else {
                        jsonButtonString = "{\"text\":\"" + message + "\",\"buttons\":[";
                    }

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
                            "<b>Name :</b> " + jsonObject.getString("Name") + "\\n";

                    if (!jsonObject.getString("BirthDay").equals("null")) {
                        int age = 0;
                        LocalDate deathDay;
                        LocalDate birthDay = LocalDate.parse(jsonObject.getString("BirthDay"));

                        if (!jsonObject.getString("DeathDay").equals("null")) {
                            deathDay = LocalDate.parse(jsonObject.getString("DeathDay"));
                            age = Period.between(birthDay, deathDay).getYears();
                        } else {
                            age = Period.between(birthDay, LocalDate.now()).getYears();
                        }

                        jsonButtonString += "<b>Age :</b> " + age + "\\n";
                        jsonButtonString += "<b>Birth day :</b> " + jsonObject.getString("BirthDay") + "\\n";

                    }
                    if (!jsonObject.getString("DeathDay").equals("null"))
                        jsonButtonString += "<b>Death day :</b> " + jsonObject.getString("DeathDay") + "\\n";
                    if (!jsonObject.getString("Sex").equals("null"))
                        jsonButtonString += "<b>Sex :</b> " + jsonObject.getString("Sex") + "\\n";

                    jsonButtonString += ImageSearchSubroutine.getImage(jsonObject.getString("Name"), true) + "\\n";
                    jsonButtonString += "\",\"buttons\":[";
                    jsonButtonString += "[{\"text\":\"Movies worked on\", \"callback\":\"actor_id_movie_" + jsonObject.getInt("ID") + "\"}]";

                    jsonButtonString += "]}";
                    System.out.println(jsonButtonString);
                    InlineKeyboardSubroutine.MakeButtonMessage(rs, das, jsonButtonString);
                    break;
                case "-m":
                    for (int i = 0; i < JsonArray.length(); i++) {
                        jsonObject = JsonArray.getJSONObject(i);
                        Iterator jsonIterator = jsonObject.keys();
                        boolean isFirst = true;
                        while (jsonIterator.hasNext()) {
                            String key = (String) jsonIterator.next();
                            if (isFirst) {
                                result += message + " " + jsonObject.getString(key);
                                isFirst = false;
                            } else
                                result += " | " + message + " " + jsonObject.getString(key);
                        }
                        result += "\n";
                    }
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