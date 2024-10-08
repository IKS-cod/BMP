package BMP.service;

import BMP.interfaces.RecommendationRuleSetInvest500;
import BMP.interfaces.RecommendationRuleSetSimpleCredit;
import BMP.interfaces.RecommendationRuleSetTopSaving;
import BMP.model.ModelJSon;
import BMP.model.Recommendation;
import BMP.repository.RecommendationsRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@WebMvcTest(RecommendationsService.class)
class RecommendationsServiceTest {
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
    @Test
    void checkGetModelJSonPositiveTest() {
        String id = "1f9b149c-6577-448a-bc94-16bea229b71a";
        List<Recommendation> recommendationList = new ArrayList<>();//expected
        Recommendation recommendation11 = new Recommendation("Invest 500", 1, "Описание Инвест 500");
        Recommendation recommendation22 = new Recommendation("Simple Credit", 3, "Описание Simple credit");
        Recommendation recommendation33 = new Recommendation("Top Saving", 2, "Описание Top Saving");
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
        Recommendation recommendation11 = new Recommendation("Invest 500", 1, "Описание Инвест 500");
        Recommendation recommendation22 = new Recommendation("Simple Credit", 3, "Описание Simple credit");
        Recommendation recommendation33 = new Recommendation("Top Saving", 2, "Описание Top Saving");
        Recommendation recommendation44 = new Recommendation("Test", 5, "Описание Test");
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