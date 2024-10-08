package BMP.interfaces;

import BMP.model.Recommendation;
import BMP.repository.RecommendationsRepository;
import BMP.service.RecommendationsService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@WebMvcTest(RecommendationRuleSetInvest500Test.class)
class RecommendationRuleSetInvest500Test {
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private RecommendationsRepository repository;

    @SpyBean
    private RecommendationsService recommendationsService;

    @SpyBean
    private RecommendationRuleSetInvest500 recommendationRuleSetInvest500;
    @SpyBean
    private RecommendationRuleSetSimpleCredit recommendationRuleSetSimpleCredit;
    @SpyBean
    private RecommendationRuleSetTopSaving recommendationRuleSetTopSaving;

    private int checkSumProductTypeTransactionType(UUID uuid, String productType, String transactionType) {
        return repository.checkRuleInBaseSumTransactionForProductType(uuid, productType,
                transactionType);
    }
    @Test
    void checkRecommendationRuleSetInvest500PositiveTest() {
        String id = "1f9b149c-6577-448a-bc94-16bea229b71a";
        UUID uuid = UUID.fromString(id);
        when(checkSumProductTypeTransactionType(uuid, "DEBIT",
                "DEPOSIT")).thenReturn(1);
        when(checkSumProductTypeTransactionType(uuid, "DEBIT",
                "WITHDRAW")).thenReturn(1);
        when(checkSumProductTypeTransactionType(uuid, "SAVING",
                "DEPOSIT")).thenReturn(2000);
        when(repository.checkRuleInBaseUserNotUseProductType(uuid, "INVEST")).thenReturn(2000);

        Recommendation recommendation = new Recommendation(
                "Invest 500",
                1,
                """
                        Откройте свой путь к успеху с индивидуальным инвестиционным счетом (ИИС) от нашего банка!
                        Воспользуйтесь налоговыми льготами и начните инвестировать с умом.
                        Пополните счет до конца года и получите выгоду в виде вычета на взнос в следующем налоговом периоде.
                        Не упустите возможность разнообразить свой портфель, снизить риски и следить за актуальными рыночными тенденциями.
                        Откройте ИИС сегодня и станьте ближе к финансовой независимости!""");
        Recommendation actual = recommendationRuleSetInvest500.check(id);
        Assertions.assertEquals(recommendation, actual);
    }
    @Test
    void checkRecommendationRuleSetInvest500NegativeTest() {
        String id = "1f9b149c-6577-448a-bc94-16bea229b71a";
        UUID uuid = UUID.fromString(id);
        when(checkSumProductTypeTransactionType(uuid, "DEBIT",
                "DEPOSIT")).thenReturn(-5);
        when(checkSumProductTypeTransactionType(uuid, "DEBIT",
                "WITHDRAW")).thenReturn(1);
        when(checkSumProductTypeTransactionType(uuid, "SAVING",
                "DEPOSIT")).thenReturn(2000);
        when(repository.checkRuleInBaseUserNotUseProductType(uuid, "INVEST")).thenReturn(2000);

        Recommendation actual = recommendationRuleSetInvest500.check(id);
        Assertions.assertNull(actual);
    }
}