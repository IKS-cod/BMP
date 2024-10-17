package BMP.model;

import java.util.List;
import java.util.Objects;

public class ModelDtoInJson {
    private String userId;
    private List<Recommendation> recommendationList;

    public ModelDtoInJson(String userId, List<Recommendation> recommendationList) {
        this.userId = userId;
        this.recommendationList = recommendationList;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public List<Recommendation> getRecommendationList() {
        return recommendationList;
    }

    public void setRecommendationList(List<Recommendation> recommendationList) {
        this.recommendationList = recommendationList;
    }

    public ModelDtoInJson() {

    }

    @Override
    public String toString() {
        return "ModelJSon{" +
                "user_id='" + userId + '\'' +
                ", recommendationList=" + recommendationList +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ModelDtoInJson modelDtoInJson = (ModelDtoInJson) o;
        return Objects.equals(userId, modelDtoInJson.userId) && Objects.equals(recommendationList, modelDtoInJson.recommendationList);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userId, recommendationList);
    }
}
