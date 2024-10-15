package BMP.service;

import BMP.model.Product;
import BMP.model.QueryRecommendation;
import BMP.repository.QueryRecommendationRepository;
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

import static org.mockito.Mockito.when;

@WebMvcTest(RulesRecommendationsService.class)
class RulesRecommendationsServiceTest {
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private RulesRecommendationsRepository rulesRecommendationsRepository;
    @MockBean
    private QueryRecommendationRepository queryRecommendationRepository;
    @SpyBean
    private RulesRecommendationsService rulesRecommendationsService;

    @Test
    void createRulesRecommendationsPositiveTest() {
        Product product = new Product(
                "Test",
                "Test",
                new ArrayList<>(
                        List.of(
                                new QueryRecommendation("USER_OF", List.of("CREDIT"), false)
                        )
                )
        );
        when(rulesRecommendationsRepository.save(product)).thenReturn(product);
        Product actual = rulesRecommendationsService.createRulesRecommendations(product);
        Assertions.assertEquals(product, actual);
    }

    /*@Test
    void createRulesRecommendationsNegativeTest() {
        Product product = null;
        when(rulesRecommendationsRepository.save(product)).thenReturn(product);
        Assertions.assertThrows(IllegalArgumentException.class)
                .isThrownBy(() -> rulesRecommendationsService.createRulesRecommendations(product));
    }*/


}