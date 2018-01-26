package Nickybot;

import com.rivescript.RiveScript;
import com.rivescript.macro.Subroutine;
import org.json.JSONArray;
import org.json.JSONObject;
import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.api.objects.Update;
import org.telegram.telegrambots.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.bots.DefaultAbsSender;
import org.telegram.telegrambots.exceptions.TelegramApiException;

import java.util.ArrayList;
import java.util.List;

public class InlineKeyboardSubroutine implements Subroutine {
    DefaultAbsSender das;

    public InlineKeyboardSubroutine(DefaultAbsSender sender) {
        das = sender;
    }

    @Override
    public String call(RiveScript riveScript, String[] args) {
        String jsonString = "";

        for (String arg : args) jsonString = jsonString + " " + arg;
        jsonString = jsonString.trim();

        MakeButtonMessage(riveScript, das, jsonString);

        return "";
    }

    public static void MakeButtonMessage(RiveScript riveScript, DefaultAbsSender das, String jsonString) {

        SendMessage message = new SendMessage()
                .setText("test text")
                .setChatId(riveScript.currentUser())
                .setParseMode("HTML");

        InlineKeyboardMarkup markupInline = new InlineKeyboardMarkup();
        List<List<InlineKeyboardButton>> rowButtons = new ArrayList<>();

        System.out.println(jsonString);
        JSONObject jsonObject = new JSONObject(jsonString);
        System.out.println(jsonObject);

        message.setText(jsonObject.getString("text"));

        JSONArray rowJsonArray = jsonObject.getJSONArray("buttons");
        for (int i = 0; i < rowJsonArray.length(); i++) {
            List<InlineKeyboardButton> colButtons = new ArrayList<>();
            JSONArray colJsonArray = rowJsonArray.getJSONArray(i);

            for (int j = 0; j < colJsonArray.length(); j++) {
                String text = colJsonArray.getJSONObject(j).getString("text");
                String callback = colJsonArray.getJSONObject(j).getString("callback");

                colButtons.add(new InlineKeyboardButton().setText(text).setCallbackData(callback));
            }
            // Set the keyboard to the markup
            rowButtons.add(colButtons);
        }
        // Add it to the message
        markupInline.setKeyboard(rowButtons);
        message.setReplyMarkup(markupInline);

        try {
            das.execute(message); // Call method to send the photo with caption
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    public static SendMessage CallBack(Update update, RiveScript bot) {
// Set variables
        String call_data = update.getCallbackQuery().getData();
        long chat_id = update.getCallbackQuery().getMessage().getChatId();

        String reply = "";

        switch (call_data) {
            case "questions_1":
                reply = bot.reply(String.valueOf(chat_id), "shortest movie highest rating");
                break;
            case "questions_2":
                reply = bot.reply(String.valueOf(chat_id), "what year from 1990 produced the most movies with beer in the title");
                break;
            case "questions_3":
                reply = bot.reply(String.valueOf(chat_id), "give me movies liam neeson played in");
                break;
            case "questions_4":
                reply = bot.reply(String.valueOf(chat_id), "which director has filmed the most movies with liam neeson");
                break;
            case "questions_5":
                reply = bot.reply(String.valueOf(chat_id), "which movie was the most expensive to create");
                break;
            case "questions_6":
                reply = bot.reply(String.valueOf(chat_id), "what are the most occuring genres");
                break;
            case "questions_8":
                reply = bot.reply(String.valueOf(chat_id), "what are the most occuring genres in france");
                break;
            case "questions_9":
                reply = bot.reply(String.valueOf(chat_id), "which countries made the most movies");
                break;
            case "questions_10":
                reply = bot.reply(String.valueOf(chat_id), "what are the top rated movies");
                break;
            case "questions_11":
                reply = bot.reply(String.valueOf(chat_id), "show me the distribution of genres");
                break;
            case "questions_12":
                reply = bot.reply(String.valueOf(chat_id), "does france have more violent movies than new zealand");
                break;
            default:
                if (call_data.startsWith("movie_id_trailer_")) {
                    String movieID = call_data.substring(17);
                    String sql = "SELECT Title FROM Movies WHERE ID = " + movieID;
                    String response = Database.query(sql);
                    JSONObject jsonObject = new JSONArray(response).getJSONObject(0);
                    reply = bot.reply(String.valueOf(chat_id), "trailer " + jsonObject.getString("Title"));
                } else if (call_data.startsWith("movie_id_trailer_")) {
                    String movieID = call_data.substring(16);
                    System.out.println(movieID);
                    reply = bot.reply(String.valueOf(chat_id), "search person movie id " + movieID);
                } else if (call_data.startsWith("movie_id_staff_")) {
                    String movieID = call_data.substring(15);
                    reply = bot.reply(String.valueOf(chat_id), "search non-actor movie id " + movieID);
                }  else if (call_data.startsWith("movie_id_cast_")) {
                    String movieID = call_data.substring(14);
                    reply = bot.reply(String.valueOf(chat_id), "search actor movie id " + movieID);
                } else if (call_data.startsWith("movie_id_plot_")) {
                    String movieID = call_data.substring(14);
                    reply = bot.reply(String.valueOf(chat_id), "search movie plot id " + movieID);
                } else if (call_data.startsWith("movie_id_mpaa_")) {
                    String movieID = call_data.substring(14);
                    reply = bot.reply(String.valueOf(chat_id), "mpaa " + movieID);
                } else if (call_data.startsWith("movie_id_")) {
                    String movieID = call_data.substring(9);
                    reply = bot.reply(String.valueOf(chat_id), "search movie id " + movieID);
                } else if (call_data.startsWith("actor_id_movie_")) {
                    String personID = call_data.substring(15);
                    reply = bot.reply(String.valueOf(chat_id), "search movie actor id " + personID);
                } else if (call_data.startsWith("actor_id_")) {
                    String personID = call_data.substring(10);
                    reply = bot.reply(String.valueOf(chat_id), "search actor id " + personID);
                } else if (call_data.startsWith("person_id_")) {
                    String personID = call_data.substring(10);
                    reply = bot.reply(String.valueOf(chat_id), "search person id " + personID);
                }
                break;
        }
        SendMessage message = new SendMessage() // Create a message object object
                .setChatId(chat_id)
                .setText(reply);
        message.setParseMode("HTML");
        return message;
    }
}
