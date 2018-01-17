package Nickybot;

import org.telegram.telegrambots.ApiContextInitializer;
import org.telegram.telegrambots.TelegramBotsApi;
import org.telegram.telegrambots.exceptions.TelegramApiException;

import javax.script.ScriptException;
import java.io.FileNotFoundException;

/**
 *
 * @author Groep 16
 */
public class Main {
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws ScriptException {
        // Initialize Api Context
        ApiContextInitializer.init();

        // Instantiate Telegram Bots API
        TelegramBotsApi botsApi = new TelegramBotsApi();

        // Register our bot
        try {
            botsApi.registerBot(new Nickybot());
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
        System.out.println("Nickybot Activated!");
    }

}
