import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class SemplyBot extends TelegramLongPollingBot {


    public void onUpdateReceived(Update update) {

        if (update.hasMessage() && update.getMessage().hasText()) {
            String textMsg = update.getMessage().getText();
            String textAnsw;
            Long chatId = update.getMessage().getChatId();

            if (textMsg.startsWith("/")) {
                switch (textMsg.toLowerCase()) {
                    case "/start": {
                        textAnsw = "Hello, " + update.getMessage().getFrom().getFirstName();
                        sendmsg(chatId, textAnsw);
                        break;
                    }
                    case "/time": {
                        textAnsw = new Date().toString();
                        sendmsg(chatId, textAnsw);
                        break;
                    }
                }
            } else {
                textAnsw = textMsg;
                sendmsg(chatId, textAnsw);
            }
        }

    }

    private synchronized void setKeyboard(SendMessage message){
        ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup()
                .setSelective(true)
                .setResizeKeyboard(false)
                .setOneTimeKeyboard(false);
        message.setReplyMarkup(replyKeyboardMarkup);
        List<KeyboardRow> keyboardRows = new ArrayList<>();
        KeyboardRow keyboardButtons = new KeyboardRow();
        keyboardButtons.add(new KeyboardButton("/start"));
        keyboardButtons.add(new KeyboardButton("/time"));
        keyboardRows.add(keyboardButtons);
        replyKeyboardMarkup.setKeyboard(keyboardRows);
    }

    private synchronized void sendmsg(Long chatId, String textAnsw) {
        SendMessage message = new SendMessage()
                .setChatId(chatId)
                .setText(textAnsw);
        try {
            setKeyboard(message);
            execute(message);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    public String getBotUsername() {
        return "SempliBot";
    }

    public String getBotToken() {
        return "574609337:AAHn2dMYt2HT7LxzXSpakOtr-VtlJ7zxDHg";
    }

}
