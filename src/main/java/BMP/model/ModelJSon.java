package BMP.model;

import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Objects;

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

    @Override
    public String toString() {
        return "ModelJSon{" +
                "user_id='" + user_id + '\'' +
                ", recommendationList=" + recommendationList +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ModelJSon modelJSon = (ModelJSon) o;
        return Objects.equals(user_id, modelJSon.user_id) && Objects.equals(recommendationList, modelJSon.recommendationList);
    }

    @Override
    public int hashCode() {
        return Objects.hash(user_id, recommendationList);
    }
}
