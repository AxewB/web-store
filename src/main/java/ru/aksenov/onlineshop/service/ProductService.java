package ru.aksenov.onlineshop.service;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.aksenov.onlineshop.models.Category;
import ru.aksenov.onlineshop.models.Product;
import ru.aksenov.onlineshop.repository.CategoryRepository;
import ru.aksenov.onlineshop.repository.ProductRepository;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private CategoryService categoryService;

    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    public Product getProductById(Long id) {
        return productRepository
                .findById(id)
                .orElseThrow(() -> new RuntimeException("Product not found"));
    }

    public Product addProduct(Product product) {
        return productRepository.save(product);
    }

    public List<Product> addMultipleProducts(List<Product> products) {
        List<Product> newProducts = new ArrayList<>();
        for (Product product : products) {
            newProducts.add(productRepository.save(product));
        }
        return newProducts;
    }

    public Product updateProduct(Long id, Product productDetails) {
        Product product = productRepository.findById(id).orElseThrow(() -> new RuntimeException("Product not found"));

        BeanUtils.copyProperties(productDetails, product,  "id", "category");
        Set<Category> categories = new HashSet<>();
        for (Category category : productDetails.getCategories()) {
            Category singleCategory = categoryRepository.findById(category.getId()).orElseThrow(() -> new RuntimeException("Category not found"));
            categories.add(singleCategory);
        }
        product.setCategories(categories);

        return productRepository.save(product);
    }

    public void deleteProduct(Long id) {
        Product product = productRepository.findById(id).orElseThrow(() -> new RuntimeException("Product not found"));
        productRepository.delete(product);
    }

    public List<Product> getProductsByCategoryAndSubcategories(Long categoryId) {
        Set<Long> categoryIds = categoryService.getAllChildCategoryIds(categoryId);
        categoryIds.add(categoryId); // Добавляем родительскую категорию
        return productRepository.findByCategoriesIdIn(categoryIds);
    }

    public List<Product> getProductsByName(String name) {
        return productRepository.findByName(name);
    }

    public List<Product> getProductsByNameIgnoreCaseAndSpace(String name) {
        return productRepository.findByNameIgnoreCaseAndSpaces(name);
    }
}
