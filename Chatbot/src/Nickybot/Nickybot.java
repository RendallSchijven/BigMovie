package Nickybot;

import com.rivescript.Config;
import com.rivescript.RiveScript;
import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.api.methods.updatingmessages.EditMessageText;
import org.telegram.telegrambots.api.objects.Update;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.exceptions.TelegramApiException;

/**
 * @author Groep 16
 */
public class Nickybot extends TelegramLongPollingBot {

    private RiveScript bot = new RiveScript(Config.utf8());

    public Nickybot() {
        super();
        bot.setSubroutine("system", new SystemSubroutine());
        bot.setSubroutine("jdbc", new JdbcSubroutine());
        bot.setSubroutine("send", new SendSubroutine(this));
        bot.setSubroutine("inlineKeyboard", new InlineKeyboardSubroutine(this));
        bot.loadDirectory("Chatbot/resources/RiveScript");
        bot.sortReplies();
    }

    @Override
    public void onUpdateReceived(Update update) {

        // We check if the update has a message and the message has text
        if (update.hasMessage() && update.getMessage().hasText()) {
            // Set variables
            String message_text = update.getMessage().getText();
            long chat_id = update.getMessage().getChatId();

            String reply = bot.reply(String.valueOf(chat_id), message_text);

            SendMessage message = new SendMessage() // Create a message object object
                    .setChatId(chat_id)
                    .setText(reply);

            System.out.println(message_text);
            try {
                execute(message); // Sending our message object to user
            } catch (TelegramApiException e) {
                e.printStackTrace();
            }
        } else if (update.hasCallbackQuery()) {
            InlineKeyboardSubroutine.CallBack(update, bot);
        }
    }

    @Override
    public String getBotUsername() {
        // Return bot username
        return "Nicky_B_Bot";
    }

    @Override
    public String getBotToken() {
        // Return bot token from BotFather
        return "504474641:AAHRSD9huFwZApje8nJBzK_tNIC4ZEg-tqs";
    }
}
