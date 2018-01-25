package Nickybot;

import com.rivescript.Config;
import com.rivescript.RiveScript;
import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.api.objects.Update;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.exceptions.TelegramApiException;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;
import java.util.Scanner;

/**
 * @author Groep 16
 */
public class Nickybot extends TelegramLongPollingBot {

    private RiveScript bot = new RiveScript(Config.utf8());
    private static String username = "";

    public Nickybot() {
        super();
        bot.setSubroutine("system", new SystemSubroutine());
        bot.setSubroutine("jdbc", new JdbcSubroutine(this));
        bot.setSubroutine("send", new SendSubroutine(this));
        bot.setSubroutine("inlineKeyboard", new InlineKeyboardSubroutine(this));
        bot.setSubroutine("rive", new riveFeaturesSubroutine());
        bot.setSubroutine("trailer", new TrailerSubroutine());
        bot.setSubroutine("image", new ImageSearchSubroutine());
        bot.loadDirectory("Chatbot/resources/RiveScript/ENRive");
        bot.loadDirectory("Chatbot/resources/RiveScript/NLRive");
        bot.sortReplies();
    }

    @Override
    public void onUpdateReceived(Update update) {
        SendMessage message = null;
        // We check if the update has a message and the message has text
        if (update.hasMessage() && update.getMessage().hasText()) {
            // Set variables
            String message_text = update.getMessage().getText();
            long chat_id = update.getMessage().getChatId();

            Nickybot.username = update.getMessage().getChat().getFirstName();

            String reply = bot.reply(String.valueOf(chat_id), message_text);

            message = new SendMessage() // Create a message object object
                    .setChatId(chat_id)
                    .setText(reply);
            message.setParseMode("HTML");

            System.out.println(message_text);
        } else if (update.hasCallbackQuery()) {
            message = InlineKeyboardSubroutine.CallBack(update, bot);
        }
        if (message != null && !message.getText().equals("") && !message.getText().equals("ERR: No Reply Found")) {
            try {
                System.out.println(message.getText());
                execute(message); // Sending our message object to user
            } catch (TelegramApiException e) {
                e.printStackTrace();
            }
        }
    }

    public static String getName() {
        return Nickybot.username;
    }

    @Override
    public String getBotUsername() {
        // Return bot username
        return "Nicky_B_Bot";
    }

    @Override
    public String getBotToken() {
        String rootPath = Thread.currentThread().getContextClassLoader().getResource("").getPath();
        String appConfigPath = rootPath + "resources/app.properties";

        Properties appProps = new Properties();
        try {
            appProps.load(new FileInputStream(appConfigPath));
        } catch (IOException e) {
            e.printStackTrace();
        }
        // Return bot token from BotFather
        return appProps.getProperty("tg_apiKey");
    }
}
