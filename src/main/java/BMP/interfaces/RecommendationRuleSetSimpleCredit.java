package BMP.interfaces;

import BMP.model.Recommendation;
import BMP.repository.RecommendationsRepository;
import org.springframework.stereotype.Component;

import java.util.UUID;

/**
 * Реализация интерфейса RecommendationRuleSet для проверки рекомендации Simple Credit
 *
 * @author Невзорова Екатерина
 */
@Component
public class RecommendationRuleSetSimpleCredit implements RecommendationRuleSet {

    RecommendationsRepository repository;

    public RecommendationRuleSetSimpleCredit(RecommendationsRepository repository) {
        this.repository = repository;
    }

    @Override
    public Recommendation check(String id) {
        UUID uuid = UUID.fromString(id);
        Recommendation recommendation = null;
        if (repository.checkRuleInBaseUserNotUseProductType(uuid, "CREDIT") > 0 &&
                checkSumProductTypeTransactionType(uuid, "DEBIT",
                        "WITHDRAW") > 100_000 &&
                checkSumProductTypeTransactionType(uuid, "DEBIT",
                        "DEPOSIT") >
                        checkSumProductTypeTransactionType(uuid, "DEBIT",
                                "WITHDRAW")
        ) {
            recommendation = new Recommendation(
                    "Simple Credit",
                    UUID.fromString("ab138afb-f3ba-4a93-b74f-0fcee86d447f"),
                    """
                            Откройте мир выгодных кредитов с нами!

                            Ищете способ быстро и без лишних хлопот получить нужную сумму?\s
                            Тогда наш выгодный кредит — именно то, что вам нужно!\s
                            Мы предлагаем низкие процентные ставки, гибкие условия и индивидуальный подход к каждому клиенту.

                            Почему выбирают нас:

                            Быстрое рассмотрение заявки.\s
                            Мы ценим ваше время, поэтому процесс рассмотрения заявки занимает всего несколько часов.

                            Удобное оформление.\s
                            Подать заявку на кредит можно онлайн на нашем сайте или в мобильном приложении.

                            Широкий выбор кредитных продуктов.\s
                            Мы предлагаем кредиты на различные цели: покупку недвижимости, автомобиля, образование, лечение и многое другое.

                            Не упустите возможность воспользоваться выгодными условиями кредитования от нашей компании!""");
        }
        return recommendation;
    }

    /**
     * Обращение в базу данных для предоставления информации о транзакциях пользователя
     */
    private int checkSumProductTypeTransactionType(UUID uuid, String productType, String transactionType) {
        return repository.checkRuleInBaseSumTransactionForProductType(uuid, productType,
                transactionType);
    }
}
