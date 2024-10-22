package BMP.listener;

import BMP.model.ModelDtoInJson;
import BMP.model.UserFromDb;
import BMP.repository.RecommendationsRepository;
import BMP.service.RecommendationsService;
import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.UpdatesListener;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import jakarta.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Класс для обработки обновлений от Telegram-бота.
 * Реализует интерфейс UpdatesListener для обработки входящих сообщений.
 */
@Service
public class TelegramBotUpdatesListener implements UpdatesListener {

    private final Logger logger = LoggerFactory.getLogger(TelegramBotUpdatesListener.class);
    private final RecommendationsService recommendationsService;
    private final RecommendationsRepository recommendationsRepository;

    @Autowired
    private TelegramBot telegramBot;

    private final Pattern pattern = Pattern.compile("^/recommend\\s+(.+)$");

    /**
     * Конструктор класса.
     *
     * @param recommendationsService Сервис для получения рекомендаций.
     * @param recommendationsRepository Репозиторий для работы с пользователями.
     */
    public TelegramBotUpdatesListener(RecommendationsService recommendationsService,
                                      RecommendationsRepository recommendationsRepository) {
        this.recommendationsService = recommendationsService;
        this.recommendationsRepository = recommendationsRepository;
    }

    /**
     * Инициализация бота после создания бина.
     * Устанавливает слушатель обновлений для Telegram-бота.
     */
    @PostConstruct
    public void init() {
        telegramBot.setUpdatesListener(this);
    }

    /**
     * Обработка списка обновлений от Telegram.
     *
     * @param updates Список обновлений, полученных от Telegram.
     * @return Статус обработки обновлений (подтверждение всех обновлений).
     */
    @Override
    public int process(List<Update> updates) {
        updates.forEach(update -> {
            logger.info("Processing update: {}", update);
            if (Objects.isNull(update.message())) {
                return;
            }
            String updateText = update.message().text();
            Long chatId = update.message().chat().id();
            Matcher matcher = pattern.matcher(updateText);
            // Обработка обновлений

            try {
                if (Objects.isNull(update.message().text())) {
                    throw new RuntimeException("Нет текста");
                }
            } catch (RuntimeException e) {
                sendMessageInTelegramBot(update.message().chat().id(), "Я работаю только с текстом");
                logger.info("Отправлен не текст");
                return;
            }
            if (updateText.equals("/start")) {
                String messageText = "Привет! Для получения информации по банковским продуктам введите \"/recommend <username>\"";
                sendMessageInTelegramBot(chatId, messageText);
            }
            if (updateText.equals("/recommend")) {
                String messageText =  "Введите запрос в формате \"/recommend <username>\"";
                sendMessageInTelegramBot(chatId, messageText);
            }
            if (matcher.matches()) {
                String item = matcher.group(1);
                System.out.println(matcher.toString());
                UserFromDb userFromDb = null;
                try {
                    userFromDb = recommendationsRepository.getIdUser(item);
                } catch (Exception e) {
                    logger.error("An unexpected error occurred: {}", e.getMessage());
                    sendMessageInTelegramBot(chatId, "Пользователь не найден!");
                    return;
                }

                ModelDtoInJson modelJSon = recommendationsService.get(userFromDb.getId().toString());
                sendMessageInTelegramBot(chatId, "Здравствуйте, " + userFromDb.getFirstName() + " " + userFromDb.getLastName());
                sendMessageInTelegramBot(chatId, "Новые продукты для вас: ");

                for (int i = 0; i < modelJSon.getRecommendationList().size(); i++) {
                    sendMessageInTelegramBot(chatId, modelJSon.getRecommendationList().get(i).getName() + '\n' +
                            modelJSon.getRecommendationList().get(i).getText());
                }
            }
        });
        return UpdatesListener.CONFIRMED_UPDATES_ALL; // Подтверждение обработки всех обновлений
    }

    /**
     * Отправка сообщения в Telegram.
     *
     * @param chatId Идентификатор чата, куда будет отправлено сообщение.
     * @param message Текст сообщения для отправки.
     */
    private void sendMessageInTelegramBot(Long chatId, String message) {
        logger.info("Sending message to chat ID {}: {}", chatId, message);
        SendMessage messageForSend = new SendMessage(chatId, message);
        telegramBot.execute(messageForSend);
    }
}
