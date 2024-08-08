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

    public List<Product> loadProducts(String filePath) throws IOException {
        return objectMapper.readValue(new File(filePath), new TypeReference<List<Product>>() {
        });
    }

    public List<CategoryJsonInfo> loadCategories(String filePath) throws IOException {
        return objectMapper.readValue(new File(filePath), new TypeReference<List<CategoryJsonInfo>>() {});
    }

    public List<ProductCategoryMapper> loadProductCategories(String filePath) throws IOException {
        return objectMapper.readValue(new File(filePath), new TypeReference<List<ProductCategoryMapper>>() {
        });
    }
}
