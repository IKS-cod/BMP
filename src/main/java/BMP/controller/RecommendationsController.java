package BMP.controller;

import BMP.model.ModelDtoInJson;
import BMP.model.StatsDto;
import BMP.model.UserFromDb;
import BMP.service.RecommendationsService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Контроллер для обработки веб-запросов, связанных с рекомендациями пользователей.
 * Этот контроллер предоставляет методы для получения рекомендаций и управления кэшами.
 *
 * @author Гуров Дмитрий
 */
@RestController
public class RecommendationsController {
    private final RecommendationsService recommendationsService;

    /**
     * Конструктор контроллера, который инициализирует сервис рекомендаций.
     *
     * @param recommendationsService Сервис для работы с рекомендациями.
     */
    public RecommendationsController(RecommendationsService recommendationsService) {
        this.recommendationsService = recommendationsService;
    }

    /**
     * Обрабатывает GET запрос на получение информации о рекомендациях для пользователя.
     *
     * @param id Идентификатор пользователя, для которого запрашиваются рекомендации.
     * @return ModelJSon Модель ответа, содержащая рекомендации для указанного пользователя.
     */
    @GetMapping()
    public ModelDtoInJson get(String id) {
        return recommendationsService.get(id);
    }

    /**
     * Обрабатывает GET запрос на получение информации о пользователе по его имени.
     *
     * @param userName Имя пользователя, для которого запрашивается информация.
     * @return UserFromDb Объект, содержащий информацию о пользователе из базы данных.
     */
    @GetMapping("/userId")
    public UserFromDb getUserId(String userName) {
        return recommendationsService.getUserId(userName);
    }

    /**
     * Обрабатывает POST запрос для очистки кэша системы рекомендаций.
     * Этот метод может быть использован для обновления данных и освобождения памяти.
     */
    @PostMapping("/management/clear-caches")
    public void clearCaches() {
        recommendationsService.clearCaches();
    }

    /**
     * Обрабатывает GET-запрос для получения статистики правил.
     *
     * @return объект StatsDto, содержащий все статистические записи из репозитория.
     */
    @GetMapping("/rule/stats")
    public StatsDto getStats() {
        return recommendationsService.getStats();
    }

}
