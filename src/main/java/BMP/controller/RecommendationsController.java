package BMP.controller;

import BMP.model.ModelDtoInJson;
import BMP.service.RecommendationsService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Контроллер для обработки веб-запросов
 * @author Гуров Дмитрий
 */
@RestController
public class RecommendationsController {
    private final RecommendationsService recommendationsService;

    public RecommendationsController(RecommendationsService recommendationsService) {
        this.recommendationsService = recommendationsService;
    }
    /**
     * GET запрос на получение информации о рекомендациях для пользователя
     * @param id пользователя
     * @return ModelJSon модель ответа на запрос
     */
    @GetMapping()
    public ModelDtoInJson get(String id) {
        return recommendationsService.get(id);
    }
    /**
     * Обрабатывает POST запрос для очистки кэша системы рекомендаций.
     * Этот метод может быть использован для обновления данных и освобождения памяти.
     */
    @PostMapping("/management/clear-caches")
    public void clearCaches() {
        recommendationsService.clearCaches();
    }

}
