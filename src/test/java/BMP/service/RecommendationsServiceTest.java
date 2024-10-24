package BMP.service;

import BMP.interfaces.RecommendationRuleSetInvest500;
import BMP.interfaces.RecommendationRuleSetSimpleCredit;
import BMP.interfaces.RecommendationRuleSetTopSaving;
import BMP.model.*;
import BMP.repository.RecommendationsRepository;
import BMP.repository.RulesRecommendationsRepository;
import BMP.repository.StatsRepository;
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

import static org.mockito.Mockito.*;

@WebMvcTest(RecommendationsService.class)
class RecommendationsServiceTest {
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private RecommendationsRepository repository;
    @MockBean
    private StatsRepository statsRepository;
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
        Recommendation recommendation11 = new Recommendation("Invest 500", UUID.fromString("147f6a0f-3b91-413b-ab99-87f081d60d5a"), "Описание Инвест 500");
        Recommendation recommendation22 = new Recommendation("Simple Credit", UUID.fromString("ab138afb-f3ba-4a93-b74f-0fcee86d447f"), "Описание Simple credit");
        Recommendation recommendation33 = new Recommendation("Top Saving", UUID.fromString("59efc529-2fff-41af-baff-90ccd7402925"), "Описание Top Saving");
        recommendationList.add(recommendation11);
        recommendationList.add(recommendation22);
        recommendationList.add(recommendation33);
        ModelDtoInJson modelJSon = new ModelDtoInJson(id, recommendationList);

        when(recommendationRuleSetInvest500.check(id)).thenReturn(recommendation11);
        when(recommendationRuleSetTopSaving.check(id)).thenReturn(recommendation22);
        when(recommendationRuleSetSimpleCredit.check(id)).thenReturn(recommendation33);

        ModelDtoInJson actual = recommendationsService.get(id);
        Assertions.assertEquals(modelJSon, actual);
    }

    @Test
    void checkGetModelJSonNegativeTest() {
        String id = "1f9b149c-6577-448a-bc94-16bea229b71a";
        List<Recommendation> recommendationList = new ArrayList<>();//expected
        Recommendation recommendation11 = new Recommendation("Invest 500", UUID.fromString("147f6a0f-3b91-413b-ab99-87f081d60d5a"), "Описание Инвест 500");
        Recommendation recommendation22 = new Recommendation("Simple Credit", UUID.fromString("ab138afb-f3ba-4a93-b74f-0fcee86d447f"), "Описание Simple credit");
        Recommendation recommendation33 = new Recommendation("Top Saving", UUID.fromString("59efc529-2fff-41af-baff-90ccd7402925"), "Описание Top Saving");
        Recommendation recommendation44 = new Recommendation("Test", UUID.fromString("59efc529-9fff-41af-baff-90ccd7402925"), "Описание Test");
        recommendationList.add(recommendation11);
        recommendationList.add(recommendation22);
        recommendationList.add(recommendation33);
        ModelDtoInJson modelJSon = new ModelDtoInJson(id, recommendationList);

        when(recommendationRuleSetInvest500.check(id)).thenReturn(recommendation11);
        when(recommendationRuleSetTopSaving.check(id)).thenReturn(recommendation44);
        when(recommendationRuleSetSimpleCredit.check(id)).thenReturn(recommendation33);

        ModelDtoInJson actual = recommendationsService.get(id);
        Assertions.assertNotEquals(modelJSon, actual);
    }

    @Test
    void getStatsTest() {
        StatsDto statsDto = new StatsDto();
        statsDto.setStats(new ArrayList<>(List.of(
                new Stats(UUID.fromString("147f6a0f-3b91-413b-ab99-87f081d60d5a"), 1),
                new Stats(UUID.fromString("ab138afb-f3ba-4a93-b74f-0fcee86d447f"), 1)
        )));

        when(statsRepository.findAll()).thenReturn(statsDto.getStats());
        StatsDto actual = recommendationsService.getStats();
        Assertions.assertEquals(statsDto, actual);
    }

    @Test
    void clearCachesPositiveTest() {
        recommendationsService.clearCaches();
        verify(repository, times(1)).clearCaches();
    }

    @Test
    void getUserIdTest() {
        UserFromDb userFromDb = new UserFromDb();
        userFromDb.setId(UUID.fromString("147f6a0f-3b91-413b-ab99-87f081d60d5a"));
        userFromDb.setLastName("Test");
        userFromDb.setFirstName("Test");
        String userName = "sheron.berge";
        when(repository.getIdUser(userName)).thenReturn(userFromDb);
        UserFromDb actual = repository.getIdUser(userName);
        Assertions.assertEquals(userFromDb, actual);

    }
}