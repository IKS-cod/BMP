package BMP.service;

import BMP.interfaces.RecommendationRuleSetInvest500;
import BMP.interfaces.RecommendationRuleSetSimpleCredit;
import BMP.interfaces.RecommendationRuleSetTopSaving;
import BMP.model.*;
import BMP.repository.RecommendationsRepository;
import BMP.repository.RulesRecommendationsRepository;
import BMP.repository.StatsRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;


/**
 * Сервис для обработки рекомендаций на основе правил и транзакций пользователя.
 */
@Service
public class RecommendationsService {

    private Logger logger = LoggerFactory.getLogger(RecommendationsService.class);
    private final StatsRepository statsRepository;
    private final RecommendationsRepository recommendationsRepository;
    private final RulesRecommendationsRepository rulesRecommendationsRepository;
    private final RecommendationRuleSetInvest500 recommendationRuleSetInvest500;
    private final RecommendationRuleSetSimpleCredit recommendationRuleSetSimpleCredit;
    private final RecommendationRuleSetTopSaving recommendationRuleSetTopSaving;

    /**
     * Конструктор для инициализации зависимостей.
     *
     * @param statsRepository                   Репозиторий статистики.
     * @param recommendationsRepository          Репозиторий рекомендаций.
     * @param rulesRecommendationsRepository     Репозиторий правил рекомендаций.
     * @param recommendationRuleSetInvest500     Набор правил для Invest500.
     * @param recommendationRuleSetSimpleCredit  Набор правил для SimpleCredit.
     * @param recommendationRuleSetTopSaving     Набор правил для TopSaving.
     */
    public RecommendationsService(StatsRepository statsRepository,
                                  RecommendationsRepository recommendationsRepository,
                                  RulesRecommendationsRepository rulesRecommendationsRepository,
                                  RecommendationRuleSetInvest500 recommendationRuleSetInvest500,
                                  RecommendationRuleSetSimpleCredit recommendationRuleSetSimpleCredit,
                                  RecommendationRuleSetTopSaving recommendationRuleSetTopSaving) {
        this.statsRepository = statsRepository;
        this.recommendationsRepository = recommendationsRepository;
        this.rulesRecommendationsRepository = rulesRecommendationsRepository;
        this.recommendationRuleSetInvest500 = recommendationRuleSetInvest500;
        this.recommendationRuleSetSimpleCredit = recommendationRuleSetSimpleCredit;
        this.recommendationRuleSetTopSaving = recommendationRuleSetTopSaving;
    }


    /**
     * Получает рекомендации на основе идентификатора пользователя.
     *
     * @param id Идентификатор пользователя.
     * @return Объект ModelDtoInJson с рекомендациями.
     */
    public ModelDtoInJson get(String id) {
        logger.info("Запрос рекомендаций для пользователя с ID: {}", id);

        List<Recommendation> recommendationList = new ArrayList<>();

        // Проверка и добавление рекомендаций на основе набора правил
        if (!Objects.isNull(recommendationRuleSetInvest500.check(id))) {
            recommendationList.add(recommendationRuleSetInvest500.check(id));
            logger.info("Добавлена рекомендация \"Invest 500\"");
        }
        if (!Objects.isNull(recommendationRuleSetTopSaving.check(id))) {
            recommendationList.add(recommendationRuleSetTopSaving.check(id));
            logger.info("Добавлена рекомендация \"Top Saving\"");
        }
        if (!Objects.isNull(recommendationRuleSetSimpleCredit.check(id))) {
            recommendationList.add(recommendationRuleSetSimpleCredit.check(id));
            logger.info("Добавлена рекомендация \"Simple Credit\"");
        }

        // Получение списка продуктов
        List<Product> productList = rulesRecommendationsRepository.findAll();
        UUID uuid = UUID.fromString(id);
        logger.info("Получен список продуктов, размер: {}", productList.size());

        // Проверка правил для каждого продукта
        for (int i = 0; i < productList.size(); i++) {
            for (int j = 0; j < productList.get(i).getRule().size(); j++) {
                String queryType = productList.get(i).getRule().get(j).getQuery();
                logger.debug("Проверка правила: {}", queryType);

                // Проверка правила "USER_OF"
                if (queryType.equals("USER_OF")) {
                    boolean negate = recommendationsRepository
                            .checkUserOfAndActiveUserOf(uuid, productList.get(i).getRule().get(j).getArguments().get(0), "SUM");
                    productList.get(i).getRule().get(j).setNegate(negate);
                    logger.debug("Правило USER_OF: {}", negate);
                }
                // Проверка правила "ACTIVE_USER_OF"
                if (queryType.equals("ACTIVE_USER_OF")) {
                    boolean negate = recommendationsRepository
                            .checkUserOfAndActiveUserOf(uuid, productList.get(i).getRule().get(j).getArguments().get(0), "COUNT");
                    productList.get(i).getRule().get(j).setNegate(negate);
                    logger.debug("Правило ACTIVE_USER_OF: {}", negate);
                }
                // Проверка правила "TRANSACTION_SUM_COMPARE"
                if (queryType.equals("TRANSACTION_SUM_COMPARE")) {
                    boolean negate = recommendationsRepository
                            .checkTransactionSumCompare(uuid, productList.get(i).getRule().get(j).getArguments());
                    productList.get(i).getRule().get(j).setNegate(negate);
                    logger.debug("Правило TRANSACTION_SUM_COMPARE: {}", negate);
                }
                // Проверка правила "TRANSACTION_SUM_COMPARE_DEPOSIT_WITHDRAW"
                if (queryType.equals("TRANSACTION_SUM_COMPARE_DEPOSIT_WITHDRAW")) {
                    boolean negate = recommendationsRepository
                            .checkTransactionSumCompareDepositWithdraw(uuid, productList.get(i).getRule().get(j).getArguments());
                    productList.get(i).getRule().get(j).setNegate(negate);
                    logger.debug("Правило TRANSACTION_SUM_COMPARE_DEPOSIT_WITHDRAW: {}", negate);
                }
            }

            // Если все правила продукта возвращают true, добавляем продукт в рекомендации
            if (productList.get(i).getRule()
                    .stream()
                    .allMatch(QueryRecommendation::isNegate)) {
                recommendationList.add(new Recommendation(
                        productList.get(i).getProductName(),
                        productList.get(i).getProductId(),
                        productList.get(i).getProductText()
                ));
                statsRepository.incrementCount(productList.get(i).getProductId());
                logger.info("Добавлена рекомендация для продукта: {}", productList.get(i).getProductName());
            }
        }

        logger.info("Рекомендации сформированы для пользователя с ID: {}", id);
        return new ModelDtoInJson(id, recommendationList);
    }

    /**
     * Получает список продуктов в формате JSON.
     *
     * @return Объект ProductDtoInJson с продуктами.
     */
    private ProductDtoInJson getProductJson() {
        List<Product> products = rulesRecommendationsRepository.findAll();
        logger.info("Получен список продуктов, размер: {}", products.size());
        return new ProductDtoInJson(products);
    }

    /**
     * Генерирует UUID из строки.
     *
     * @param input Входная строка для генерации UUID.
     * @return Сгенерированный UUID.
     */
    private UUID generateUUIDFromString(String input) {
        // Преобразуем строку в массив байтов
        byte[] bytes = input.getBytes(StandardCharsets.UTF_8);
        // Генерируем UUID из массива байтов
        return UUID.nameUUIDFromBytes(bytes);
    }

    /**
     * Получает идентификатор пользователя по имени пользователя.
     *
     * @param userName Имя пользователя.
     * @return Объект UserFromDb с идентификатором пользователя.
     */
    public UserFromDb getUserId(String userName) {
        return recommendationsRepository.getIdUser(userName);
    }

    /**
     * Очищает кэш в репозитории рекомендаций.
     */
    public void clearCaches() {
        recommendationsRepository.clearCaches();
    }

    /**
     * Получает статистику и возвращает объект StatsDto, содержащий список статистических записей.
     *
     * @return объект StatsDto, содержащий все статистические записи из репозитория.
     */
    public StatsDto getStats() {
        return new StatsDto(statsRepository.findAll());
    }}