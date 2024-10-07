package BMP.interfaces;

import BMP.model.Recommendation;
import BMP.repository.RecommendationsRepository;
import org.springframework.stereotype.Component;

import java.util.UUID;

/**
 * Реализация интерфейса RecommendationRuleSet для проверки рекомендации Top Saving
 */

@Component
public class RecommendationRuleSetTopSaving implements RecommendationRuleSet {

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
            recommendation = new Recommendation(
                    "Top Saving",
                    2,
                    """
                            Откройте свою собственную «Копилку» с нашим банком!\s
                            «Копилка» — это уникальный банковский инструмент, который поможет вам легко и удобно накапливать деньги на важные цели.\s
                            Больше никаких забытых чеков и потерянных квитанций — всё под контролем!

                            Преимущества «Копилки»:

                            Накопление средств на конкретные цели.\s
                            Установите лимит и срок накопления, и банк будет автоматически переводить определенную сумму на ваш счет.

                            Прозрачность и контроль.\s
                            Отслеживайте свои доходы и расходы, контролируйте процесс накопления и корректируйте стратегию при необходимости.

                            Безопасность и надежность.\s
                            Ваши средства находятся под защитой банка, а доступ к ним возможен только через мобильное приложение или интернет-банкинг.

                            Начните использовать «Копилку» уже сегодня и станьте ближе к своим финансовым целям!""");
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
