package BMP.model;

import org.springframework.stereotype.Component;

import java.util.Objects;
import java.util.UUID;

/**
 * Класс, представляющий рекомендацию.
 * Содержит информацию о названии, уникальном идентификаторе и тексте рекомендации.
 */
@Component
public class Recommendation {
    private String name;
    private UUID id;
    private String text;

    /**
     * Конструктор для создания рекомендации.
     *
     * @param name Название рекомендации.
     * @param id   Уникальный идентификатор рекомендации.
     * @param text Текст рекомендации.
     */
    public Recommendation(String name, UUID id, String text) {
        this.name = name;
        this.id = id;
        this.text = text;
    }
    public Recommendation() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    @Override
    public String toString() {
        return "Recommendation{" +
                "name='" + name + '\'' +
                ", id=" + id +
                ", text='" + text + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Recommendation that = (Recommendation) o;
        return Objects.equals(name, that.name) && Objects.equals(id, that.id) && Objects.equals(text, that.text);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, id, text);
    }
}
