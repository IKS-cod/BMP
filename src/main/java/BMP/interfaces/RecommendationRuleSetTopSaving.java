package BMP.interfaces;

import BMP.model.Recommendation;
import BMP.repository.RecommendationsRepository;
import org.springframework.stereotype.Component;

import java.util.UUID;

/**
 * Реализация интерфейса RecommendationRuleSet для проверки рекомендации Top Saving
 */

@Component
public class RecommendationRuleSetTopSaving implements RecommendationRuleSet{

    RecommendationsRepository repository;

    public RecommendationRuleSetTopSaving(RecommendationsRepository repository) {
        this.repository = repository;
    }

    @Override
    public Recommendation check(String id) {
        UUID uuid = UUID.fromString(id);
        Recommendation recommendation = null;
        if (checkSumProductTypeTransactionType(uuid, "DEBIT",
                "DEPOSIT") +
                checkSumProductTypeTransactionType(uuid, "DEBIT",
                        "WITHDRAW") > 0 &&
                checkSumProductTypeTransactionType(uuid, "DEBIT",
                        "DEPOSIT") >
                        checkSumProductTypeTransactionType(uuid, "DEBIT",
                                "WITHDRAW") &&
                (checkSumProductTypeTransactionType(uuid, "DEBIT",
                        "DEPOSIT") >= 50_000 ||
                        checkSumProductTypeTransactionType(uuid, "SAVING",
                                "DEPOSIT") > 50_000)
        ) {
            recommendation = new Recommendation("Top Saving", 2, "Описание Top Saving");
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
