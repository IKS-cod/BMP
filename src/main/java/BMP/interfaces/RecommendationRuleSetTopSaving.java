package BMP.interfaces;

import BMP.model.Recommendation;
import BMP.repository.RecommendationsRepository;
import org.springframework.stereotype.Component;

import java.util.UUID;

/**
 * Реализация интерфейса {@link RecommendationRuleSet} для проверки рекомендаций по программе "Top Saving".
 * <p>
 * Данный класс содержит логику проверки условий для выдачи рекомендации пользователю на открытие
 * банковского продукта "Копилка". Рекомендация выдается, если выполняются определенные критерии
 * по транзакциям пользователя.
 * </p>
 */
@Component
public class RecommendationRuleSetTopSaving implements RecommendationRuleSet {

    private final RecommendationsRepository repository;

    /**
     * Конструктор класса {@link RecommendationRuleSetTopSaving}.
     *
     * @param repository Репозиторий для доступа к данным о транзакциях пользователей.
     */
    public RecommendationRuleSetTopSaving(RecommendationsRepository repository) {
        this.repository = repository;
    }

    /**
     * Проверяет, соответствует ли пользователь условиям для получения рекомендации "Top Saving".
     *
     * @param id Идентификатор пользователя в формате UUID.
     * @return {@link Recommendation} с информацией о продукте "Копилка", если условия выполнены,
     *         иначе возвращает null.
     */
    @Override
    public Recommendation check(String id) {
        UUID uuid = UUID.fromString(id);
        Recommendation recommendation = null;

        if (checkSumProductTypeTransactionType(uuid, "DEBIT", "DEPOSIT") +
                checkSumProductTypeTransactionType(uuid, "DEBIT", "WITHDRAW") > 0 &&
                checkSumProductTypeTransactionType(uuid, "DEBIT", "DEPOSIT") >
                        checkSumProductTypeTransactionType(uuid, "DEBIT", "WITHDRAW") &&
                (checkSumProductTypeTransactionType(uuid, "DEBIT", "DEPOSIT") >= 50_000 ||
                        checkSumProductTypeTransactionType(uuid, "SAVING", "DEPOSIT") > 50_000)) {

            recommendation = new Recommendation(
                    "Top Saving",
                    UUID.fromString("59efc529-2fff-41af-baff-90ccd7402925"),
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
     * Проверяет сумму транзакций пользователя по заданному типу продукта и типу транзакции.
     *
     * @param uuid Идентификатор пользователя в формате UUID.
     * @param productType Тип продукта (например, "DEBIT" или "SAVING").
     * @param transactionType Тип транзакции (например, "DEPOSIT" или "WITHDRAW").
     * @return Сумма транзакций для указанных параметров.
     */
    private int checkSumProductTypeTransactionType(UUID uuid, String productType, String transactionType) {
        return repository.checkRuleInBaseSumTransactionForProductType(uuid, productType, transactionType);
    }
}
