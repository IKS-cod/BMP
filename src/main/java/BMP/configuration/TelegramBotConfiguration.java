package BMP.configuration;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.model.DeleteMyCommands;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Конфигурация для создания экземпляра Telegram Bot.
 * <p>
 * Этот класс использует Spring Framework для внедрения токена бота и
 * создания экземпляра {@link TelegramBot}. Также выполняется команда
 * {@link DeleteMyCommands} для очистки команд бота.
 * </p>
 */
@Configuration
public class TelegramBotConfiguration {

    private static final Logger logger = LoggerFactory.getLogger(TelegramBotConfiguration.class);

    /**
     * Токен бота, получаемый из конфигурационного файла.
     */
    @Value("${telegram.bot.token}")
    private String token;

    /**
     * Создает и возвращает экземпляр {@link TelegramBot}.
     *
     * @return экземпляр {@link TelegramBot}
     * @throws IllegalArgumentException если токен пуст или равен null
     */
    @Bean
    public TelegramBot telegramBot() {
        if (token == null || token.isEmpty()) {
            throw new IllegalArgumentException("Токен бота не может быть пустым");
        }

        TelegramBot bot = new TelegramBot(token);
        try {
            bot.execute(new DeleteMyCommands());
            logger.info("Команды успешно очищены.");
        } catch (Exception e) {
            logger.error("Ошибка при очистке команд: {}", e.getMessage());
        }

        return bot;
    }
}
