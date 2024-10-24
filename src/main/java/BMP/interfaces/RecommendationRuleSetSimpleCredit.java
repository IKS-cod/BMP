package BMP.interfaces;

import BMP.model.Recommendation;
import BMP.repository.RecommendationsRepository;
import org.springframework.stereotype.Component;

import java.util.UUID;

/**
 * Реализация интерфейса {@link RecommendationRuleSet} для проверки рекомендации "Simple Credit".
 * <p>
 * Данный класс отвечает за проверку условий, при которых пользователю может быть предложен кредит.
 * Он использует репозиторий {@link RecommendationsRepository} для взаимодействия с базой данных
 * и определения, соответствует ли пользователь критериям для получения рекомендации.
 * </p>
 *
 * @author Невзорова Екатерина
 */
@Component
public class RecommendationRuleSetSimpleCredit implements RecommendationRuleSet {

    private final RecommendationsRepository repository;

    /**
     * Конструктор класса {@link RecommendationRuleSetSimpleCredit}.
     *
     * @param repository Репозиторий для работы с рекомендациями.
     */
    public RecommendationRuleSetSimpleCredit(RecommendationsRepository repository) {
        this.repository = repository;
    }

    /**
     * Проверяет, соответствует ли пользователь критериям для получения рекомендации.
     *
     * @param id Идентификатор пользователя в виде строки (UUID).
     * @return {@link Recommendation} с предложением кредита, если условия выполнены;
     *         {@code null} в противном случае.
     */
    @Override
    public Recommendation check(String id) {
        UUID uuid = UUID.fromString(id);
        Recommendation recommendation = null;

        if (repository.checkRuleInBaseUserNotUseProductType(uuid, "CREDIT") > 0 &&
                checkSumProductTypeTransactionType(uuid, "DEBIT", "WITHDRAW") > 100_000 &&
                checkSumProductTypeTransactionType(uuid, "DEBIT", "DEPOSIT") >
                        checkSumProductTypeTransactionType(uuid, "DEBIT", "WITHDRAW")) {
            recommendation = new Recommendation(
                    "Simple Credit",
                    UUID.fromString("ab138afb-f3ba-4a93-b74f-0fcee86d447f"),
                    """
                            Откройте мир выгодных кредитов с нами!

                            Ищете способ быстро и без лишних хлопот получить нужную сумму?
                            Тогда наш выгодный кредит — именно то, что вам нужно!
                            Мы предлагаем низкие процентные ставки, гибкие условия и индивидуальный подход к каждому клиенту.

                            Почему выбирают нас:

                            Быстрое рассмотрение заявки.
                            Мы ценим ваше время, поэтому процесс рассмотрения заявки занимает всего несколько часов.

                            Удобное оформление.
                            Подать заявку на кредит можно онлайн на нашем сайте или в мобильном приложении.

                            Широкий выбор кредитных продуктов.
                            Мы предлагаем кредиты на различные цели: покупку недвижимости, автомобиля, образование, лечение и многое другое.

                            Не упустите возможность воспользоваться выгодными условиями кредитования от нашей компании!""");
        }
        return recommendation;
    }

    /**
     * Обращается в базу данных для получения суммы транзакций пользователя по заданному типу продукта и типу транзакции.
     *
     * @param uuid Идентификатор пользователя (UUID).
     * @param productType Тип продукта (например, "CREDIT" или "DEBIT").
     * @param transactionType Тип транзакции (например, "WITHDRAW" или "DEPOSIT").
     * @return Сумма транзакций по заданному типу продукта и типу транзакции.
     */
    private int checkSumProductTypeTransactionType(UUID uuid, String productType, String transactionType) {
        return repository.checkRuleInBaseSumTransactionForProductType(uuid, productType, transactionType);
    }
}
