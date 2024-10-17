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
import java.util.Collection;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

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
        assertEquals(product, actual);
    }

    @Test
    void createRulesRecommendationsNegativeTest() {
        Product product = null;
        when(rulesRecommendationsRepository.save(product)).thenReturn(product);
        assertThrows(IllegalArgumentException.class, () -> {
            rulesRecommendationsService.createRulesRecommendations(product);
        });
    }

    @Test
    void getAllQueryRecommendationsPositiveTest() {
        List<QueryRecommendation> queryRecommendationList = new ArrayList<>();
        QueryRecommendation queryRecommendation = new QueryRecommendation("USER_OF",
                List.of("CREDIT"), false);
        queryRecommendationList.add(queryRecommendation);
        when(queryRecommendationRepository.findAll()).thenReturn(queryRecommendationList);
        Collection<QueryRecommendation> actual = rulesRecommendationsService.getAllQueryRecommendations();
        assertEquals(queryRecommendationList, actual);

    }

    @Test
    void getAllRulesRecommendationsPositiveTest() {
        List<Product> productCollection = new ArrayList<>();
        Product product = new Product(
                "Test",
                "Test",
                new ArrayList<>(
                        List.of(new QueryRecommendation("USER_OF", List.of("CREDIT"), false))));
        productCollection.add(product);
        when(rulesRecommendationsRepository.findAll()).thenReturn(productCollection);
        Collection<Product> actual = rulesRecommendationsService.getAllRulesRecommendations();
        assertEquals(productCollection, actual);

    }

    @Test
    void deleteRulesRecommendationsPositiveTest() {
        Long id = 1L;
        when(rulesRecommendationsRepository.existsById(id)).thenReturn(true);
        rulesRecommendationsService.deleteRulesRecommendations(id);
        verify( rulesRecommendationsRepository, times(1)).deleteById(any());

    }
    @Test
    void deleteRulesRecommendationsNegativeTest() {
        Long id = 1L;
        when(rulesRecommendationsRepository.existsById(id)).thenReturn(false);
        assertThrows(RuntimeException.class, () -> {
            rulesRecommendationsService.deleteRulesRecommendations(id);;
        });
    }

}