package BMP.model;

import java.util.List;

/**
 * Представляет объект передачи данных (DTO) для входных данных JSON,
 * который содержит список продуктов.
 */
public class ProductDtoInJson {

    /** Список продуктов. */
    private List<Product> data;

    /**
     * Конструктор по умолчанию для создания пустого объекта ProductDtoInJson.
     */
    public ProductDtoInJson() {
        // Конструктор по умолчанию
    }

    /**
     * Конструктор для создания нового объекта ProductDtoInJson с указанным списком продуктов.
     *
     * @param data список продуктов
     */
    public ProductDtoInJson(List<Product> data) {
        this.data = data;
    }

    /**
     * Возвращает список продуктов.
     *
     * @return список продуктов
     */
    public List<Product> getData() {
        return data;
    }
}
