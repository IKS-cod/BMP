package BMP.interfaces;

import BMP.model.Recommendation;
import BMP.repository.RecommendationsRepository;
import org.springframework.stereotype.Component;

import java.util.UUID;
/**
* Реализация интерфейса RecommendationRuleSet Класс для проверки рекомендации Invest500
 */
@Component
public class RecommendationRuleSetInvest500 implements RecommendationRuleSet{
    RecommendationsRepository repository;

    public RecommendationRuleSetInvest500(RecommendationsRepository repository) {
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
                repository.checkRuleInBaseUserNotUseProductType(uuid, "INVEST") > 0 &&
                checkSumProductTypeTransactionType(uuid, "SAVING",
                        "DEPOSIT") > 1_000
        ) {
            recommendation = new Recommendation("Invest 500",1, "Описание Инвест 500");
        }
        return recommendation;
    }
    //Обращение в базу данных для предоставления информации о транзакциях пользователя
    private int checkSumProductTypeTransactionType(UUID uuid, String productType, String transactionType) {
        return repository.checkRuleInBaseSumTransactionForProductType(uuid, productType,
                transactionType);
    }
}
