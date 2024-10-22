package BMP.service;


import BMP.model.Product;
import BMP.model.QueryRecommendation;
import BMP.model.Stats;
import BMP.repository.QueryRecommendationRepository;
import BMP.repository.RulesRecommendationsRepository;
import BMP.repository.StatsRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;
import java.util.Objects;

/**
 * Сервис для обработки правил рекомендаций.
 */
@Service
public class RulesRecommendationsService {
    private final StatsRepository statsRepository;
    private static final Logger logger = LoggerFactory.getLogger(RulesRecommendationsService.class);
    private final RulesRecommendationsRepository rulesRecommendationsRepository;
    private final QueryRecommendationRepository queryRecommendationRepository;

    /**
     * Конструктор для инициализации зависимостей.
     *
     * @param rulesRecommendationsRepository  Репозиторий правил рекомендаций.
     * @param queryRecommendationRepository    Репозиторий запросов рекомендаций.
     */
    public RulesRecommendationsService(StatsRepository statsRepository,
                                       RulesRecommendationsRepository rulesRecommendationsRepository,
                                       QueryRecommendationRepository queryRecommendationRepository) {
        this.statsRepository = statsRepository;
        this.rulesRecommendationsRepository = rulesRecommendationsRepository;
        this.queryRecommendationRepository = queryRecommendationRepository;
    }

    public Product createRulesRecommendations(Product product) {
        if (Objects.isNull(product)) {
            throw new IllegalArgumentException("Некорректные условия");
        }
        if (product.getRule().isEmpty() || Objects.isNull(product.getRule())) {
            throw new IllegalArgumentException("Некорректные условия");
        }

        List<QueryRecommendation> queryRecommendationList = product.getRule();
        logger.info("Создание правил рекомендаций для продукта: {}", product.getProductName());

        // Сохраняем каждое правило рекомендации
        for (QueryRecommendation queryRecommendation : queryRecommendationList) {
            queryRecommendationRepository.save(queryRecommendation);
            logger.debug("Сохранено правило рекомендации: {}", queryRecommendation);
        }

        Stats stats = new Stats(product.getProductId(), 0);
        statsRepository.save(stats);

        return rulesRecommendationsRepository.save(product);
    }
    public Collection<QueryRecommendation> getAllQueryRecommendations() {
        logger.info("Вызван метод для получения всех запросов рекомендаций.");
        Collection<QueryRecommendation> productCollection = queryRecommendationRepository.findAll();
        logger.info("Получена коллекция запросов рекомендаций, размер: {}", productCollection.size());
        return productCollection;
    }

    /**
     * Получает все правила рекомендаций.
     *
     * @return Коллекция всех правил рекомендаций.
     */
    public Collection<Product> getAllRulesRecommendations() {
        logger.info("Вызван метод для получения всех правил рекомендаций.");
        Collection<Product> productCollection = rulesRecommendationsRepository.findAll();
        logger.info("Получена коллекция правил рекомендаций, размер: {}", productCollection.size());
        return productCollection;
    }

    /**
     * Удаляет правило рекомендации по идентификатору.
     *
     * @param id Идентификатор правила рекомендации для удаления.
     * @throws RuntimeException Если правило с указанным идентификатором не найдено.
     */
    public void deleteRulesRecommendations(long id) {
        logger.info("Вызван метод для удаления правила рекомендации с id: {}", id);
        if (!rulesRecommendationsRepository.existsById(id)) {
            logger.error("Правило рекомендации с id = {} не найдено", id);
            throw new RuntimeException("Рекомендация с id= %d не найдена".formatted(id));
        }
        statsRepository.deleteById(rulesRecommendationsRepository.findById(id).get().getProductId());
        rulesRecommendationsRepository.deleteById(id);

        logger.info("Удалено правило рекомендации с id: {}", id);
    }
}