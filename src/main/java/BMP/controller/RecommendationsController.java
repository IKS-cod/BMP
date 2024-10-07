package BMP.controller;

import BMP.model.ModelJSon;
import BMP.service.RecommendationsService;
import org.springframework.web.bind.annotation.GetMapping;
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
    public ModelJSon get(String id) {
        return recommendationsService.get(id);
    }
}
