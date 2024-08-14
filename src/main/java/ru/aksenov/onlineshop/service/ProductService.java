package ru.aksenov.onlineshop.service;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
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

    /**
     * Получение списка всех продуктов.
     *
     * @return Список всех продуктов.
     */
    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    /**
     * Получение продукта по его идентификатору.
     *
     * @param id Идентификатор продукта.
     * @return Продукт с указанным идентификатором.
     * @throws RuntimeException Если продукт не найден.
     */
    public Product getProductById(Long id) {
        return productRepository
                .findById(id)
                .orElseThrow(() -> new RuntimeException("Product not found"));
    }

    /**
     * Добавление нового продукта.
     *
     * @param product Продукт, который нужно добавить.
     * @return Добавленный продукт.
     */
    public Product addProduct(Product product) {
        return productRepository.save(product);
    }

    /**
     * Добавление нескольких продуктов.
     *
     * @param products Список продуктов для добавления.
     * @return Список добавленных продуктов.
     */
    public List<Product> addMultipleProducts(List<Product> products) {
        List<Product> newProducts = new ArrayList<>();
        for (Product product : products) {
            newProducts.add(productRepository.save(product));
        }
        return newProducts;
    }

    /**
     * Обновление информации о продукте.
     *
     * @param id Идентификатор продукта, который нужно обновить.
     * @param productDetails Объект с новыми данными продукта.
     * @return Обновленный продукт.
     * @throws RuntimeException Если продукт с указанным идентификатором не найден.
     */
    @Transactional
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

    /**
     * Удаление продукта по его идентификатору.
     *
     * @param id Идентификатор продукта, который нужно удалить.
     * @throws RuntimeException Если продукт с указанным идентификатором не найден.
     */
    @Transactional
    public void deleteProduct(Long id) {
        Product product = productRepository.findById(id).orElseThrow(() -> new RuntimeException("Product not found"));
        productRepository.delete(product);
    }

    /**
     * Получение списка продуктов, относящихся к указанной категории и её подкатегориям.
     *
     * @param categoryId Идентификатор категории.
     * @return Список продуктов, относящихся к указанной категории и её подкатегориям.
     */
    @Transactional
    public List<Product> getProductsByCategoryAndSubcategories(Long categoryId) {
        if (categoryId == null) {
            return this.getAllProducts();
        }
        Set<Long> categoryIds = categoryService.getAllChildCategoryIds(categoryId);
        categoryIds.add(categoryId); // Добавляем родительскую категорию
        return productRepository.findByCategoriesIdIn(categoryIds);
    }

    /**
     * Получение списка продуктов по названию.
     *
     * @param name Название продукта.
     * @return Список продуктов с указанным названием.
     */
    public List<Product> getProductsByName(String name) {
        if (name.isEmpty())
            return productRepository.findAll();
        else
            return productRepository.findByName(name);
    }

    /**
     * Получение списка продуктов по названию, игнорируя регистр и пробелы.
     *
     * @param name Название продукта.
     * @return Список продуктов, соответствующих указанному названию, игнорируя регистр и пробелы.
     */
    public List<Product> getProductsByNameIgnoreCaseAndSpace(String name) {
        return productRepository.findByNameIgnoreCaseAndSpaces(name);
    }
}
