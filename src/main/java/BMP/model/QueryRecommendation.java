package BMP.model;

import jakarta.persistence.*;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Objects;

/**
 * Класс, представляющий рекомендацию продукта.
 * Содержит информацию о продукте, его аргументах и флаге отрицания.
 */
@Table(name = "product1")
@Entity
public class QueryRecommendation {
    @Id
    @GeneratedValue
    private Long id;

    private String query;
    private List<String> arguments;
    private boolean negate;

    /**
     * Конструктор для создания рекомендации запроса.
     *
     * @param query    Запрос, который будет использоваться в рекомендации.
     * @param arguments Список аргументов, связанных с запросом.
     * @param negate    Флаг, указывающий, является ли запрос отрицательным.
     */
    public QueryRecommendation(String query, List<String> arguments, boolean negate) {
        this.query = query;
        this.arguments = arguments;
        this.negate = negate;
    }

    public QueryRecommendation() {
    }

    @Override
    public String toString() {
        return "QueryRecommendation{" +
                "query='" + query + '\'' +
                ", arguments=" + arguments +
                ", negate=" + negate +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        QueryRecommendation that = (QueryRecommendation) o;
        return negate == that.negate && Objects.equals(query, that.query) && Objects.equals(arguments, that.arguments);
    }

    @Override
    public int hashCode() {
        return Objects.hash(query, arguments, negate);
    }

    public String getQuery() {
        return query;
    }

    public void setQuery(String query) {
        this.query = query;
    }

    public List<String> getArguments() {
        return arguments;
    }

    public void setArguments(List<String> arguments) {
        this.arguments = arguments;
    }

    public boolean isNegate() {
        return negate;
    }

    public void setNegate(boolean negate) {
        this.negate = negate;
    }
}
