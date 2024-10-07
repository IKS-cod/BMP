package BMP.service;

import BMP.interfaces.RecommendationRuleSetInvest500;
import BMP.interfaces.RecommendationRuleSetSimpleCredit;
import BMP.interfaces.RecommendationRuleSetTopSaving;
import BMP.model.ModelJSon;
import BMP.model.Recommendation;
import BMP.repository.RecommendationsRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;


@Service
public class RecommendationsService {

    private Logger logger = LoggerFactory.getLogger(RecommendationsService.class);

    private final RecommendationsRepository recommendationsRepository;

    private final RecommendationRuleSetInvest500 recommendationRuleSetInvest500;

    private final RecommendationRuleSetSimpleCredit recommendationRuleSetSimpleCredit;

    private final RecommendationRuleSetTopSaving recommendationRuleSetTopSaving;

    public RecommendationsService(RecommendationsRepository recommendationsRepository,
                                  RecommendationRuleSetInvest500 recommendationRuleSetInvest500,
                                  RecommendationRuleSetSimpleCredit recommendationRuleSetSimpleCredit,
                                  RecommendationRuleSetTopSaving recommendationRuleSetTopSaving) {

        this.recommendationsRepository = recommendationsRepository;
        this.recommendationRuleSetInvest500 = recommendationRuleSetInvest500;
        this.recommendationRuleSetSimpleCredit = recommendationRuleSetSimpleCredit;
        this.recommendationRuleSetTopSaving = recommendationRuleSetTopSaving;
    }

    public ModelJSon get(String id) {
        List<Recommendation> recommendationList = new ArrayList<>();
        recommendationList.add(recommendationRuleSetInvest500.check(id));
        logger.info("Добавлена рекомендация \"Invest 500\"");
        recommendationList.add(recommendationRuleSetTopSaving.check(id));
        logger.info("Добавлена рекомендация \"Top Saving\"");
        recommendationList.add(recommendationRuleSetSimpleCredit.check(id));
        logger.info("Добавлена рекомендация \"Simple Credit\"");
        return new ModelJSon(id, recommendationList);
    }
}
