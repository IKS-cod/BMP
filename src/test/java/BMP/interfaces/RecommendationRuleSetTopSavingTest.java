package BMP.interfaces;

import BMP.model.Recommendation;
import BMP.repository.QueryRecommendationRepository;
import BMP.repository.RecommendationsRepository;
import BMP.repository.RulesRecommendationsRepository;
import BMP.repository.StatsRepository;
import BMP.service.RecommendationsService;
import BMP.service.RulesRecommendationsService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.UUID;

import static org.mockito.Mockito.when;

@WebMvcTest(RecommendationRuleSetTopSaving.class)
class RecommendationRuleSetTopSavingTest {
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private RecommendationsRepository recommendationsRepository;
    @MockBean
    private StatsRepository statsRepository;

    @MockBean
    private RulesRecommendationsRepository rulesRecommendationsRepository;
    @SpyBean
    private RulesRecommendationsService rulesRecommendationsService;
    @SpyBean
    private RecommendationsService recommendationsService;
    @MockBean
    private QueryRecommendationRepository queryRecommendationRepository;
    @SpyBean
    private RecommendationRuleSetInvest500 recommendationRuleSetInvest500;
    @SpyBean
    private RecommendationRuleSetSimpleCredit recommendationRuleSetSimpleCredit;
    @SpyBean
    private RecommendationRuleSetTopSaving recommendationRuleSetTopSaving;


    private int checkSumProductTypeTransactionType(UUID uuid, String productType, String transactionType) {
        return recommendationsRepository.checkRuleInBaseSumTransactionForProductType(uuid, productType,
                transactionType);
    }
    @Test
    void checkRecommendationRuleSetTopSavingPositiveTest() {
        String id = "1f9b149c-6577-448a-bc94-16bea229b71a";
        UUID uuid = UUID.fromString(id);
        when(checkSumProductTypeTransactionType(uuid, "DEBIT",
                "DEPOSIT")).thenReturn(1);

        when(checkSumProductTypeTransactionType(uuid, "DEBIT",
                "WITHDRAW")).thenReturn(1);

        when(checkSumProductTypeTransactionType(uuid, "DEBIT",
                "DEPOSIT")).thenReturn(2);

        when(checkSumProductTypeTransactionType(uuid, "DEBIT",
                "WITHDRAW")).thenReturn(1);

        when(checkSumProductTypeTransactionType(uuid, "DEBIT",
                "DEPOSIT")).thenReturn(60_000);

        when(checkSumProductTypeTransactionType(uuid, "SAVING",
                "DEPOSIT")).thenReturn(60_000);

        Recommendation recommendation = new Recommendation("Top Saving",
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
        Recommendation actual = recommendationRuleSetTopSaving.check(id);
        Assertions.assertEquals(recommendation, actual);
    }

    @Test
    void checkRecommendationRuleSetTopSavingNegativeTest() {
        String id = "1f9b149c-6577-448a-bc94-16bea229b71a";
        UUID uuid = UUID.fromString(id);
        when(checkSumProductTypeTransactionType(uuid, "DEBIT",
                "DEPOSIT")).thenReturn(1);

        when(checkSumProductTypeTransactionType(uuid, "DEBIT",
                "WITHDRAW")).thenReturn(1);

        when(checkSumProductTypeTransactionType(uuid, "DEBIT",
                "DEPOSIT")).thenReturn(2);

        when(checkSumProductTypeTransactionType(uuid, "DEBIT",
                "WITHDRAW")).thenReturn(1);

        when(checkSumProductTypeTransactionType(uuid, "DEBIT",
                "DEPOSIT")).thenReturn(-200_000);

        when(checkSumProductTypeTransactionType(uuid, "SAVING",
                "DEPOSIT")).thenReturn(60_000);

        Recommendation actual = recommendationRuleSetTopSaving.check(id);
        Assertions.assertNull(actual);
    }



}