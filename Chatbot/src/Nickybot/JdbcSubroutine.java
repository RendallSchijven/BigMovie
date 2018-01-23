package Nickybot;

import com.rivescript.macro.Subroutine;
import org.json.JSONArray;
import org.json.JSONObject;
import org.telegram.telegrambots.bots.DefaultAbsSender;

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

        if (!args[0].equals("SELECT")) {
            for (int i = 1; i < args.length; i++) {
                sql = sql + " " + args[i];
            }

            sql = sql.trim();
            response = Database.query(sql);

            if (args[0].equals("film_list")) {

                String jsonButtonString = "";

                InlineKeyboardSubroutine.MakeButtonMessage(rs, das, jsonButtonString);

            } else if(args[0].equals("1")){

            }

        } else {
            for (int i = 0; i < args.length; i++) {
                sql = sql + " " + args[i];
            }
            sql = sql.trim();
            response = Database.query(sql);


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