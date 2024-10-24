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

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@WebMvcTest(RecommendationRuleSetInvest500Test.class)
class RecommendationRuleSetInvest500Test {
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
    void checkRecommendationRuleSetInvest500PositiveTest() {
        String id = "147f6a0f-3b91-413b-ab99-87f081d60d5a";
        UUID uuid = UUID.fromString(id);
        when(checkSumProductTypeTransactionType(uuid, "DEBIT",
                "DEPOSIT")).thenReturn(1);
        when(checkSumProductTypeTransactionType(uuid, "DEBIT",
                "WITHDRAW")).thenReturn(1);
        when(checkSumProductTypeTransactionType(uuid, "SAVING",
                "DEPOSIT")).thenReturn(2000);
        when(recommendationsRepository.checkRuleInBaseUserNotUseProductType(uuid, "INVEST")).thenReturn(2000);

        Recommendation recommendation = new Recommendation(
                "Invest 500",
                UUID.fromString("147f6a0f-3b91-413b-ab99-87f081d60d5a"),
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
        when(recommendationsRepository.checkRuleInBaseUserNotUseProductType(uuid, "INVEST")).thenReturn(2000);

        Recommendation actual = recommendationRuleSetInvest500.check(id);
        Assertions.assertNull(actual);
    }
}