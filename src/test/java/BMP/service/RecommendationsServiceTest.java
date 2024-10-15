package BMP.service;

import BMP.interfaces.RecommendationRuleSetInvest500;
import BMP.interfaces.RecommendationRuleSetSimpleCredit;
import BMP.interfaces.RecommendationRuleSetTopSaving;
import BMP.model.ModelJSon;
import BMP.model.Recommendation;
import BMP.repository.RecommendationsRepository;
import BMP.repository.RulesRecommendationsRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@WebMvcTest(RecommendationsService.class)
class RecommendationsServiceTest {
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private RecommendationsRepository repository;

    @MockBean
    private RulesRecommendationsRepository rulesRecommendationsRepository;

    @SpyBean
    private RecommendationsService recommendationsService;

    @SpyBean
    private RecommendationRuleSetInvest500 recommendationRuleSetInvest500;
    @SpyBean
    private RecommendationRuleSetSimpleCredit recommendationRuleSetSimpleCredit;
    @SpyBean
    private RecommendationRuleSetTopSaving recommendationRuleSetTopSaving;
    @Test
    void checkGetModelJSonPositiveTest() {
        String id = "1f9b149c-6577-448a-bc94-16bea229b71a";
        List<Recommendation> recommendationList = new ArrayList<>();//expected
        Recommendation recommendation11 = new Recommendation("Invest 500",
                UUID.fromString("147f6a0f-3b91-413b-ab99-87f081d60d5a"), "Описание Инвест 500");
        Recommendation recommendation22 = new Recommendation("Simple Credit",
                UUID.fromString("ab138afb-f3ba-4a93-b74f-0fcee86d447f"), "Описание Simple credit");
        Recommendation recommendation33 = new Recommendation("Top Saving",
                UUID.fromString("59efc529-2fff-41af-baff-90ccd7402925"), "Описание Top Saving");
        recommendationList.add(recommendation11);
        recommendationList.add(recommendation22);
        recommendationList.add(recommendation33);
        ModelJSon modelJSon = new ModelJSon(id, recommendationList);

        when(recommendationRuleSetInvest500.check(id)).thenReturn(recommendation11);
        when(recommendationRuleSetTopSaving.check(id)).thenReturn(recommendation22);
        when(recommendationRuleSetSimpleCredit.check(id)).thenReturn(recommendation33);

        ModelJSon actual = recommendationsService.get(id);
        Assertions.assertEquals(modelJSon, actual);
    }
    @Test
    void checkGetModelJSonNegativeTest() {
        String id = "1f9b149c-6577-448a-bc94-16bea229b71a";
        List<Recommendation> recommendationList = new ArrayList<>();//expected
        Recommendation recommendation11 = new Recommendation("Invest 500",
                UUID.fromString("147f6a0f-3b91-413b-ab99-87f081d60d5a"), "Описание Инвест 500");
        Recommendation recommendation22 = new Recommendation("Simple Credit",
                UUID.fromString("ab138afb-f3ba-4a93-b74f-0fcee86d447f"), "Описание Simple credit");
        Recommendation recommendation33 = new Recommendation("Top Saving",
                UUID.fromString("59efc529-2fff-41af-baff-90ccd7402925"), "Описание Top Saving");
        Recommendation recommendation44 = new Recommendation("Test", UUID.fromString
                ("59efc529-2fff-41af-baff-90ccd7402930"), "Описание Test");
        recommendationList.add(recommendation11);
        recommendationList.add(recommendation22);
        recommendationList.add(recommendation33);
        ModelJSon modelJSon = new ModelJSon(id, recommendationList);

        when(recommendationRuleSetInvest500.check(id)).thenReturn(recommendation11);
        when(recommendationRuleSetTopSaving.check(id)).thenReturn(recommendation44);
        when(recommendationRuleSetSimpleCredit.check(id)).thenReturn(recommendation33);

        ModelJSon actual = recommendationsService.get(id);
        Assertions.assertNotEquals(modelJSon, actual);
    }
}