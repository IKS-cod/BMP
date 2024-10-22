package BMP.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;

import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

/**
 * Класс, представляющий продукт в системе.
 * Содержит информацию о названии, описании и правилах рекомендаций для продукта.
 */
@Table(name = "product")
@Entity
public class Product {

    @Id
    @GeneratedValue
    @Schema(accessMode = Schema.AccessMode.READ_ONLY)
    private Long id;

    private String productName;

    @Schema(accessMode = Schema.AccessMode.READ_ONLY)
    private UUID productId;

    private String productText;

    @ElementCollection
    private List<QueryRecommendation> rule;

    /**
     * Конструктор для создания продукта с автоматической генерацией productId.
     *
     * @param productName Название продукта.
     * @param productText Описание продукта.
     * @param rule        Список правил рекомендаций для продукта.
     */
    public Product(String productName, String productText, List<QueryRecommendation> rule) {
        this.productName = productName;
        this.productId = generateUUIDFromString(productName); // Генерация UUID на основе названия продукта
        this.productText = productText;
        this.rule = rule;
    }

    public Product() {
        // Пустой конструктор для JPA
    }

    /**
     * Генерирует UUID на основе строки.
     *
     * @param input Входная строка.
     * @return Сгенерированный UUID.
     */
    private UUID generateUUIDFromString(String input) {
        // Преобразуем строку в массив байтов
        byte[] bytes = input.getBytes(StandardCharsets.UTF_8);
        // Генерируем UUID из массива байтов
        return UUID.nameUUIDFromBytes(bytes);
    }

    public Long getId() {
        return id;
    }

    @Override
    public String toString() {
        return "Product{" +
                "productName='" + productName + '\'' +
                ", productId=" + productId +
                ", productText='" + productText + '\'' +
                ", rule=" + rule +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Product product = (Product) o;
        return Objects.equals(productName, product.productName) &&
                Objects.equals(productId, product.productId) &&
                Objects.equals(productText, product.productText) &&
                Objects.equals(rule, product.rule);
    }

    @Override
    public int hashCode() {
        return Objects.hash(productName, productId, productText, rule);
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
        this.productId = generateUUIDFromString(productName); // Обновляем UUID при изменении имени продукта
    }

    public UUID getProductId() {
        return productId;
    }

    public String getProductText() {
        return productText;
    }

    public void setProductText(String productText) {
        this.productText = productText;
    }

    public List<QueryRecommendation> getRule() {
        return rule;
    }

    public void setRule(List<QueryRecommendation> rule) {
        this.rule = rule;
    }
}