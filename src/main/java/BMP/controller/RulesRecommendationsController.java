package BMP.controller;



import BMP.model.Product;
import BMP.model.QueryRecommendation;
import BMP.service.RulesRecommendationsService;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

/**
 * Контроллер для обработки веб-запросов по созданию условий выдачи рекомендаций, удалению и получению.
 * @author Смирнов Иван
 */
@RestController
@RequestMapping("/rule")
public class RulesRecommendationsController {
    private RulesRecommendationsService rulesRecommendationsService;


    public RulesRecommendationsController(RulesRecommendationsService rulesRecommendationsService) {
        this.rulesRecommendationsService = rulesRecommendationsService;

    }

    @PostMapping
    public Product createRulesRecommendations(@RequestBody Product product) {
        System.out.println(product.toString());
        return rulesRecommendationsService.createRulesRecommendations(product);
    }

    @GetMapping
    public Collection<Product> getAllRulesRecommendations() {
        return rulesRecommendationsService.getAllRulesRecommendations();
    }
    @GetMapping("/query")
    public Collection<QueryRecommendation> getAllQueryRecommendations() {
        return rulesRecommendationsService.getAllQueryRecommendations();
    }

    @DeleteMapping("/{id}")
    public void deleteRulesRecommendations(@PathVariable long id) {
        rulesRecommendationsService.deleteRulesRecommendations(id);
    }




}
