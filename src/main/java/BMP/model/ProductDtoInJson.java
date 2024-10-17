package BMP.model;

import java.util.List;

public class ProductDtoInJson {
    private List<Product> data;

    public ProductDtoInJson() {
    }

    public ProductDtoInJson(List<Product> data) {
        this.data = data;
    }

    public List<Product> getData() {
        return data;
    }
}
