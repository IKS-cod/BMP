package BMP.interfaces;

import BMP.model.Recommendation;
import BMP.repository.RecommendationsRepository;
import org.springframework.stereotype.Component;

import java.util.UUID;

/**
 * Реализация интерфейса {@link RecommendationRuleSet} для проверки рекомендации "Invest 500".
 * <p>
 * Этот класс проверяет, соответствует ли пользователь определенным критериям,
 * чтобы получить рекомендацию по открытию индивидуального инвестиционного счета (ИИС).
 * </p>
 */
@Component
public class RecommendationRuleSetInvest500 implements RecommendationRuleSet {
    private final RecommendationsRepository repository;

    /**
     * Конструктор класса {@link RecommendationRuleSetInvest500}.
     *
     * @param repository репозиторий для доступа к данным рекомендаций.
     */
    public RecommendationRuleSetInvest500(RecommendationsRepository repository) {
        this.repository = repository;
    }

    /**
     * Проверяет, соответствует ли пользователь критериям для получения рекомендации "Invest 500".
     *
     * @param id идентификатор пользователя в виде строки.
     * @return объект {@link Recommendation}, если пользователь соответствует критериям, иначе null.
     */
    @Override
    public Recommendation check(String id) {
        UUID uuid = UUID.fromString(id);
        Recommendation recommendation = null;

        if (checkSumProductTypeTransactionType(uuid, "DEBIT", "DEPOSIT") +
                checkSumProductTypeTransactionType(uuid, "DEBIT", "WITHDRAW") > 0 &&
                repository.checkRuleInBaseUserNotUseProductType(uuid, "INVEST") > 0 &&
                checkSumProductTypeTransactionType(uuid, "SAVING", "DEPOSIT") > 1_000) {

            recommendation = new Recommendation(
                    "Invest 500",
                    UUID.fromString("147f6a0f-3b91-413b-ab99-87f081d60d5a"),
                    """
                            Откройте свой путь к успеху с индивидуальным инвестиционным счетом (ИИС) от нашего банка!
                            Воспользуйтесь налоговыми льготами и начните инвестировать с умом.
                            Пополните счет до конца года и получите выгоду в виде вычета на взнос в следующем налоговом периоде.
                            Не упустите возможность разнообразить свой портфель, снизить риски и следить за актуальными рыночными тенденциями.
                            Откройте ИИС сегодня и станьте ближе к финансовой независимости!""");
        }
        return recommendation;
    }

    /**
     * Проверяет сумму транзакций определенного типа для заданного продукта.
     *
     * @param uuid          идентификатор пользователя.
     * @param productType   тип продукта (например, "DEBIT", "SAVING").
     * @param transactionType тип транзакции (например, "DEPOSIT", "WITHDRAW").
     * @return сумма транзакций для заданного типа продукта и транзакции.
     */
    private int checkSumProductTypeTransactionType(UUID uuid, String productType, String transactionType) {
        return repository.checkRuleInBaseSumTransactionForProductType(uuid, productType, transactionType);
    }
}
