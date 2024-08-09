package ru.aksenov.onlineshop.helperClasses;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.aksenov.onlineshop.models.Category;
import ru.aksenov.onlineshop.models.Product;
import ru.aksenov.onlineshop.repository.CategoryRepository;

import java.io.File;
import java.io.IOException;
import java.util.List;

@Component
public class DataLoader {
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Autowired
    CategoryRepository categoryRepository;

    /**
     * Загрузка списка продуктов из файла JSON.
     *
     * @param filePath Путь к файлу JSON, содержащему данные о продуктах.
     * @return Список объектов типа Product, загруженных из файла.
     * @throws IOException В случае ошибок при чтении файла или парсинге JSON.
     */
    public List<Product> loadProducts(String filePath) throws IOException {
        return objectMapper.readValue(new File(filePath), new TypeReference<List<Product>>() {
        });
    }

    /**
     * Загрузка списка категорий из файла JSON.
     *
     * @param filePath Путь к файлу JSON, содержащему данные о категориях.
     * @return Список объектов типа CategoryJsonInfo, загруженных из файла.
     * @throws IOException В случае ошибок при чтении файла или парсинге JSON.
     */
    public List<CategoryJsonInfo> loadCategories(String filePath) throws IOException {
        return objectMapper.readValue(new File(filePath), new TypeReference<List<CategoryJsonInfo>>() {});
    }

    /**
     * Загрузка списка соответствий продуктов и категорий из файла JSON.
     *
     * @param filePath Путь к файлу JSON, содержащему данные о соответствиях продуктов и категорий.
     * @return Список объектов типа ProductCategoryMapper, загруженных из файла.
     * @throws IOException В случае ошибок при чтении файла или парсинге JSON.
     */
    public List<ProductCategoryMapper> loadProductCategories(String filePath) throws IOException {
        return objectMapper.readValue(new File(filePath), new TypeReference<List<ProductCategoryMapper>>() {
        });
    }
}
