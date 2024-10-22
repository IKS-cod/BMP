package BMP.controller;



import BMP.model.Product;
import BMP.model.QueryRecommendation;
import BMP.service.RulesRecommendationsService;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

/**
 * Контроллер для обработки веб-запросов по созданию, удалению и получению рекомендаций на основе правил.
 * <p>
 * Данный контроллер предоставляет REST API для работы с рекомендациями продуктов, включая создание новых рекомендаций,
 * получение всех рекомендаций и удаление существующих.
 * </p>
 *
 * @author Смирнов Иван
 */
@RestController
@RequestMapping("/rule")
public class RulesRecommendationsController {
    private final RulesRecommendationsService rulesRecommendationsService;

    /**
     * Конструктор контроллера.
     *
     * @param rulesRecommendationsService Сервис для обработки логики рекомендаций.
     */
    public RulesRecommendationsController(RulesRecommendationsService rulesRecommendationsService) {
        this.rulesRecommendationsService = rulesRecommendationsService;
    }

    /**
     * Создает новые рекомендации на основе переданного продукта.
     *
     * @param product Объект продукта, содержащий информацию для создания рекомендаций.
     * @return Созданный продукт с примененными рекомендациями.
     */
    @PostMapping
    public Product createRulesRecommendations(@RequestBody Product product) {
        return rulesRecommendationsService.createRulesRecommendations(product);
    }

    /**
     * Получает все рекомендации продуктов.
     *
     * @return Коллекция всех продуктов с рекомендациями.
     */
    @GetMapping
    public Collection<Product> getAllRulesRecommendations() {
        return rulesRecommendationsService.getAllRulesRecommendations();
    }

    /**
     * Получает все рекомендации запросов.
     *
     * @return Коллекция всех рекомендаций запросов.
     */
    @GetMapping("/query")
    public Collection<QueryRecommendation> getAllQueryRecommendations() {
        return rulesRecommendationsService.getAllQueryRecommendations();
    }

    /**
     * Удаляет рекомендации по заданному идентификатору.
     *
     * @param id Идентификатор рекомендации, которую необходимо удалить.
     */
    @DeleteMapping("/{id}")
    public void deleteRulesRecommendations(@PathVariable long id) {
        rulesRecommendationsService.deleteRulesRecommendations(id);
    }




}
