package BMP.model;

import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ModelJSon {
    private String user_id;
    private List<Recommendation> recommendationList;

    public ModelJSon(String user_id, List<Recommendation> recommendationList) {
        this.user_id = user_id;
        this.recommendationList = recommendationList;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public List<Recommendation> getRecommendationList() {
        return recommendationList;
    }

    public void setRecommendationList(List<Recommendation> recommendationList) {
        this.recommendationList = recommendationList;
    }

    public ModelJSon() {

    }
}
