package BMP.model;

import org.springframework.stereotype.Component;

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
}
