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

@WebMvcTest(RecommendationRuleSetSimpleCreditTest.class)
class RecommendationRuleSetSimpleCreditTest {

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
    void checkRecommendationRuleSetSimpleCreditPositiveTest() {
        String id = "1f9b149c-6577-448a-bc94-16bea229b72a";
        UUID uuid = UUID.fromString(id);
        when(recommendationsRepository.checkRuleInBaseUserNotUseProductType(uuid, "CREDIT")).thenReturn(1);

        when(checkSumProductTypeTransactionType(uuid, "DEBIT",
                "WITHDRAW")).thenReturn(200_000);

        when(checkSumProductTypeTransactionType(uuid, "DEBIT",
                "DEPOSIT")).thenReturn(300_000);


        Recommendation recommendation =new Recommendation(
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
        Recommendation actual = recommendationRuleSetSimpleCredit.check(id);

        Assertions.assertEquals(recommendation, actual);
    }

    @Test
    void checkRecommendationRuleSetSimpleCreditNegativeTest() {
        String id = "1f9b149c-6577-448a-bc94-16bea229b72a";
        UUID uuid = UUID.fromString(id);
        when(recommendationsRepository.checkRuleInBaseUserNotUseProductType(uuid, "CREDIT")).thenReturn(0);

        when(checkSumProductTypeTransactionType(uuid, "DEBIT",
                "WITHDRAW")).thenReturn(200_000);

        when(checkSumProductTypeTransactionType(uuid, "DEBIT",
                "DEPOSIT")).thenReturn(300_000);

        Recommendation actual = recommendationRuleSetSimpleCredit.check(id);

        Assertions.assertNull(actual);
    }

}