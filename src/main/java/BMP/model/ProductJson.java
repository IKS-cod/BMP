package BMP.model;

import java.util.List;

public class ProductJson {
    private List<Product> data;

    public ProductJson() {
    }

    public ProductJson(List<Product> data) {
        this.data = data;
    }

    public List<Product> getData() {
        return data;
    }
}
