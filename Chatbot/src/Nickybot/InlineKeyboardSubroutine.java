package Nickybot;

import com.rivescript.RiveScript;
import com.rivescript.macro.Subroutine;
import org.json.JSONArray;
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
                .setChatId(riveScript.currentUser());

        InlineKeyboardMarkup markupInline = new InlineKeyboardMarkup();
        List<List<InlineKeyboardButton>> rowButtons = new ArrayList<>();

        JSONArray rowJsonArray = new JSONArray(jsonString);
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
                reply = bot.reply(String.valueOf(chat_id), "in what year between 1990 and 2018 are the most films with the word beer in the title produced");
                break;
            case "questions_3":
                reply = bot.reply(String.valueOf(chat_id), "test");
                break;
            case "questions_4":
                reply = bot.reply(String.valueOf(chat_id), "test");
                break;
            case "questions_5":
                reply = bot.reply(String.valueOf(chat_id), "which movie was the most expensive to create");
                break;
            case "questions_6":
                reply = bot.reply(String.valueOf(chat_id), "which actor has the longest movie career");
                break;
            case "questions_7":
                reply = bot.reply(String.valueOf(chat_id), "what are the 5 most occuring genres");
                break;
            case "questions_8":
                reply = bot.reply(String.valueOf(chat_id), "test");
                break;
            case "questions_9":
                reply = bot.reply(String.valueOf(chat_id), "which 5 countries made the most movies");
                break;
            case "questions_10":
                reply = bot.reply(String.valueOf(chat_id), "what are the top rated movies");
                break;
            case "questions_11":
                reply = bot.reply(String.valueOf(chat_id), "who is the oldest living actor");
                break;
        }
        SendMessage message = new SendMessage() // Create a message object object
                .setChatId(chat_id)
                .setText(reply);
        message.setParseMode("HTML");
        return message;
    }
}
