package BMP.controller;

import BMP.interfaces.RecommendationRuleSetInvest500;
import BMP.interfaces.RecommendationRuleSetSimpleCredit;
import BMP.interfaces.RecommendationRuleSetTopSaving;
import BMP.model.*;
import BMP.repository.RecommendationsRepository;
import BMP.repository.RulesRecommendationsRepository;
import BMP.repository.StatsRepository;
import BMP.service.RecommendationsService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(RecommendationsController.class)
class RecommendationsControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private RecommendationsRepository recommendationsRepository;
    @MockBean
    private StatsRepository statsRepository;

    @MockBean
    private RulesRecommendationsRepository rulesRecommendationsRepository;

    @SpyBean
    private RecommendationsService recommendationsService;

    @MockBean
    private RecommendationRuleSetInvest500 recommendationRuleSetInvest500;
    @MockBean
    private RecommendationRuleSetSimpleCredit recommendationRuleSetSimpleCredit;
    @MockBean
    private RecommendationRuleSetTopSaving recommendationRuleSetTopSaving;

    @Test
    void getTest() throws Exception {
        String id = "147f6a0f-3b91-413b-ab99-87f081d60d5a";
        ModelDtoInJson modelJSon = new ModelDtoInJson();
        modelJSon.setUserId(id);
        modelJSon.setRecommendationList(new ArrayList<>(List.of(
                new Recommendation("Test", UUID.fromString(id), "test")
        )));
        Boolean flag = true;
        when(recommendationsService.get(id)).thenReturn(modelJSon);

        // Act & Assert
        mockMvc.perform(get("/")
                        .param("id", id)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.recommendationList[0].name").value("Test"))
                .andExpect(jsonPath("$.recommendationList[0].id").value(id))
                .andExpect(jsonPath("$.recommendationList[0].text").value("test"));
    }


    @Test
    void getUserIdTest() throws Exception {
        UserFromDb userFromDb = new UserFromDb();
        userFromDb.setId(UUID.fromString("147f6a0f-3b91-413b-ab99-87f081d60d5a"));
        userFromDb.setLastName("Test");
        userFromDb.setFirstName("Test");
        String userName = "sheron.berge";
        when(recommendationsRepository.getIdUser(userName)).thenReturn(userFromDb);
        mockMvc.perform(get("/userId")
                        .param("userName", userName)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.lastName").value("Test"))
                .andExpect(jsonPath("$.firstName").value("Test"))
                .andExpect(jsonPath("$.id").value(userFromDb.getId().toString()));

    }


    @Test
    void clearCachesPositiveTest() throws Exception {
        mockMvc.perform(post("/management/clear-caches")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
        verify(recommendationsRepository).clearCaches();
    }

    @Test
    void getStats() throws Exception {
        StatsDto statsDto = new StatsDto();
        statsDto.setStats(new ArrayList<>(List.of(
                new Stats(UUID.fromString("147f6a0f-3b91-413b-ab99-87f081d60d5a"),1)
        )));

        when(statsRepository.findAll()).thenReturn(statsDto.getStats());
        mockMvc.perform(get("/rule/stats")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.stats[0].ruleId").value("147f6a0f-3b91-413b-ab99-87f081d60d5a"))
                .andExpect(jsonPath("$.stats[0].count").value(1));


    }
}