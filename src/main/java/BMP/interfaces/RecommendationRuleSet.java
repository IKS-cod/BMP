package BMP.interfaces;

import BMP.model.Recommendation;

/**
 * Интерфейс для проверки условий предоставления рекомендаций
 */
public interface RecommendationRuleSet {
     Recommendation check(String id);
}
