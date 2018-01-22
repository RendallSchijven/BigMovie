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


        SendMessage message = new SendMessage()
                .setChatId(riveScript.currentUser());

        InlineKeyboardMarkup markupInline = new InlineKeyboardMarkup();
        List<List<InlineKeyboardButton>> rowButtons = new ArrayList<>();

        JSONArray rowJsonArray = new JSONArray(jsonString);
        message.setText(rowJsonArray.getString(0));
        for(int i = 1; i < rowJsonArray.length(); i++){
            List<InlineKeyboardButton> colButtons = new ArrayList<>();
            JSONArray colJsonArray = rowJsonArray.getJSONArray(i);

            for(int j = 0; j < colJsonArray.length(); j++){
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

        return "";

    }

    public static void CallBack(Update update){

    }
}
