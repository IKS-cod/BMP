package BMP.controller;

import BMP.interfaces.RecommendationRuleSetInvest500;
import BMP.interfaces.RecommendationRuleSetSimpleCredit;
import BMP.interfaces.RecommendationRuleSetTopSaving;
import BMP.model.Product;
import BMP.model.QueryRecommendation;
import BMP.repository.QueryRecommendationRepository;
import BMP.repository.RecommendationsRepository;
import BMP.repository.RulesRecommendationsRepository;
import BMP.repository.StatsRepository;
import BMP.service.RecommendationsService;
import BMP.service.RulesRecommendationsService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(RulesRecommendationsController.class)
class RulesRecommendationsControllerTest {
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
    @MockBean
    private RecommendationRuleSetInvest500 recommendationRuleSetInvest500;
    @MockBean
    private RecommendationRuleSetSimpleCredit recommendationRuleSetSimpleCredit;
    @MockBean
    private RecommendationRuleSetTopSaving recommendationRuleSetTopSaving;

    @Test
    void createRulesRecommendationsTest() throws Exception {
        Product product = new Product("Test", "test", new ArrayList<>(List.of(
                new QueryRecommendation("Test1", new ArrayList<>(List.of("test2"
                )), true
                ))));
        String jsonRequest = new ObjectMapper().writeValueAsString(product);
        when(rulesRecommendationsRepository.save(product)).thenReturn(product);
        mockMvc.perform(post("/rule")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonRequest))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.productName").value(product.getProductName()))
                .andExpect(jsonPath("$.productText").value(product.getProductText()));
    }

    @Test
    void getAllRulesRecommendationsTest() throws Exception {
        Collection<Product> product = new ArrayList<>();
        product.add(new Product("Test", "test", new ArrayList<>(List.of(
                new QueryRecommendation("Test1", new ArrayList<>(List.of("test2"
                )), true
                )))));
        when(rulesRecommendationsRepository.findAll()).thenReturn(product.stream().toList());
        mockMvc.perform(get("/rule")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].productName").value("Test"))
                .andExpect(jsonPath("$[0].productText").value("test"));
    }

    @Test
    void getAllQueryRecommendationsTest() throws Exception {
        Collection<QueryRecommendation> queryRecommendations = new ArrayList<>();
        queryRecommendations.add(new QueryRecommendation("Test1", new ArrayList<>(List.of("test2"
        )), true
        ));
        when(queryRecommendationRepository.findAll()).thenReturn(queryRecommendations.stream().toList());
        mockMvc.perform(get("/rule/query")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].query").value("Test1"));


    }

    @Test
    void deleteRulesRecommendationsTest() throws Exception {
        Long id = 1L;
        Product product = new Product("Test", "test", new ArrayList<>(List.of(
                new QueryRecommendation("Test1", new ArrayList<>(List.of("test2"
                )), true
                ))));
        when(!rulesRecommendationsRepository.existsById(id)).thenReturn(true);
        when(rulesRecommendationsRepository.findById(id)).thenReturn(Optional.of(product));
        mockMvc.perform(delete("/rule/{id}", id)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
        verify(rulesRecommendationsRepository, times(1)).deleteById(id);
    }
}