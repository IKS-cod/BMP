package BMP.model;

import java.util.List;
import java.util.Objects;

/**
 * Представляет объект передачи данных (DTO) для входных данных JSON,
 * который содержит информацию о пользователе и список рекомендаций.
 */
public class ModelDtoInJson {

    /** Уникальный идентификатор пользователя. */
    private String userId;

    /** Список рекомендаций, связанных с пользователем. */
    private List<Recommendation> recommendationList;

    /**
     * Конструктор для создания нового объекта ModelDtoInJson с указанным идентификатором пользователя
     * и списком рекомендаций.
     *
     * @param userId уникальный идентификатор пользователя
     * @param recommendationList список рекомендаций
     */
    public ModelDtoInJson(String userId, List<Recommendation> recommendationList) {
        this.userId = userId;
        this.recommendationList = recommendationList;
    }

    /**
     * Конструктор по умолчанию для создания пустого объекта ModelDtoInJson.
     */
    public ModelDtoInJson() {
        // Конструктор по умолчанию
    }

    /**
     * Возвращает идентификатор пользователя.
     *
     * @return идентификатор пользователя
     */
    public String getUserId() {
        return userId;
    }

    /**
     * Устанавливает идентификатор пользователя.
     *
     * @param userId уникальный идентификатор пользователя
     */
    public void setUserId(String userId) {
        this.userId = userId;
    }

    /**
     * Возвращает список рекомендаций.
     *
     * @return список рекомендаций
     */
    public List<Recommendation> getRecommendationList() {
        return recommendationList;
    }

    /**
     * Устанавливает список рекомендаций.
     *
     * @param recommendationList список рекомендаций для установки
     */
    public void setRecommendationList(List<Recommendation> recommendationList) {
        this.recommendationList = recommendationList;
    }

    /**
     * Возвращает строковое представление объекта ModelDtoInJson.
     *
     * @return строковое представление этого объекта
     */
    @Override
    public String toString() {
        return "ModelJSon{" +
                "user_id='" + userId + '\'' +
                ", recommendationList=" + recommendationList +
                '}';
    }

    /**
     * Сравнивает этот объект с другим на равенство.
     *
     * @param o объект для сравнения
     * @return true, если объекты равны; false в противном случае
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ModelDtoInJson modelDtoInJson = (ModelDtoInJson) o;
        return Objects.equals(userId, modelDtoInJson.userId) &&
                Objects.equals(recommendationList, modelDtoInJson.recommendationList);
    }

    /**
     * Возвращает хэш-код для этого объекта.
     *
     * @return хэш-код для этого объекта
     */
    @Override
    public int hashCode() {
        return Objects.hash(userId, recommendationList);
    }
}
