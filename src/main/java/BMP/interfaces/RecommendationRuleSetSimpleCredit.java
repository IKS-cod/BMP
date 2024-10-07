package BMP.interfaces;

import BMP.model.Recommendation;
import BMP.repository.RecommendationsRepository;
import org.springframework.stereotype.Component;

import java.util.UUID;

/**
 * Реализация интерфейса RecommendationRuleSet для проверки рекомендации Simple Credit
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
            recommendation = new Recommendation("Simple Credit", 3,"Описание Simple credit");
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
