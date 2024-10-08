package BMP.model;

import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
public class Recommendation {
    String name;
    int id;
    String text;

    public Recommendation(String name, int id, String text) {
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

    public int getId() {
        return id;
    }

    public void setId(int id) {
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
        return id == that.id && Objects.equals(name, that.name) && Objects.equals(text, that.text);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, id, text);
    }
}
