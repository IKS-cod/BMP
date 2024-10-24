package BMP.listener;

import BMP.repository.RecommendationsRepository;
import BMP.service.RecommendationsService;
import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@WebMvcTest(TelegramBotUpdatesListener.class)
class TelegramBotUpdatesListenerTest {
    @MockBean
    private TelegramBot telegramBot;
    @SpyBean
    private TelegramBotUpdatesListener telegramBotUpdatesListener;
    @MockBean
    private RecommendationsService recommendationsService;
    @MockBean
    private RecommendationsRepository recommendationsRepository;
    @Captor
    private ArgumentCaptor<SendMessage> massageCaptor;

    @Test
    void process() {
        //data
        Update update = mock(Update.class);
        com.pengrad.telegrambot.model.Message message = mock(com.pengrad.telegrambot.model.Message.class);
        when(update.message()).thenReturn(message);
        when(message.text()).thenReturn("/start");
        when(message.chat()).thenReturn(mock(com.pengrad.telegrambot.model.Chat.class));
        when(message.chat().id()).thenReturn(55778945L);
        List<Update> updates = new ArrayList<>();
        updates.add(update);
        String expectedText = "Привет! Для получения информации по банковским продуктам введите \"/recommend\"";
        //test
        telegramBotUpdatesListener.process(updates);
        verify(telegramBot).execute(massageCaptor.capture());
        SendMessage capturedMessage = massageCaptor.getValue();
        //check
        assertEquals(55778945L, capturedMessage.getParameters().get("chat_id"));
        assertEquals(expectedText, capturedMessage.getParameters().get("text"));
    }

}