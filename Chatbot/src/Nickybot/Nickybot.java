package Nickybot;

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

    private RiveScript bot = new RiveScript();

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
            // Set variables
            String call_data = update.getCallbackQuery().getData();
            long message_id = update.getCallbackQuery().getMessage().getMessageId();
            long chat_id = update.getCallbackQuery().getMessage().getChatId();

            if(call_data.contains("inline"))
                InlineKeyboardSubroutine.CallBack(update);

            String answer = "";
            EditMessageText new_message = new EditMessageText()
                    .setChatId(chat_id)
                    .setMessageId((int) message_id);

            switch (call_data) {
                case "update_msg_text":
                    answer = "Updated message text";
                    new_message.setText(answer);
                    break;
                case "test_button_1":
                    answer = "test 1";
                    new_message.setText(answer);
                    break;
                case "test_button_2":
                    answer = "test 2";
                    new_message.setText(answer);
                    break;
                case "test_button_3":
                    answer = "test 3";
                    new_message.setText(answer);
                    break;
                case "test_button_4":
                    answer = "test 4";
                    new_message.setText(answer);
                    break;
            }

            try {
                execute(new_message);
            } catch (TelegramApiException e) {
                e.printStackTrace();
            }
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
