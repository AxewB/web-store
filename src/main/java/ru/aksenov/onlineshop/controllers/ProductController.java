package ru.aksenov.onlineshop.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import ru.aksenov.onlineshop.helperClasses.ResponseWrapper;
import ru.aksenov.onlineshop.models.Product;
import ru.aksenov.onlineshop.repository.ProductRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.aksenov.onlineshop.service.ProductService;

import java.util.List;
import java.util.stream.Collectors;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/products")
public class ProductController {

    @Autowired
    ProductService productService;

    /**
     * Получение списка всех продуктов с поддержкой пагинации.
     *
     * @param limit Максимальное количество продуктов, возвращаемых за один запрос (по умолчанию 10).
     * @param skip Количество продуктов, которые нужно пропустить (по умолчанию 0).
     * @return ResponseEntity с обёрнутым списком продуктов, общим количеством продуктов, лимитом и количеством пропущенных элементов.
     */
    @GetMapping
    public ResponseEntity<ResponseWrapper<Product>> getAllProducts(
            @RequestParam(defaultValue = "10") int limit,
            @RequestParam(defaultValue = "0") int skip
    ) {
        List<Product> products = productService.getAllProducts();
        ResponseWrapper<Product> response = new ResponseWrapper<>(
                limit == 0
                        ? products.stream().skip(skip).collect(Collectors.toList())
                        : products.stream().skip(skip).limit(limit).collect(Collectors.toList()),
                products.size(),
                limit,
                skip
        );
        return ResponseEntity.ok(response);
    }

    /**
     * Получение продукта по его идентификатору.
     *
     * @param id Идентификатор продукта.
     * @return ResponseEntity с продуктом, если он найден, или статусом "Not Found" в случае ошибки.
     */
    @GetMapping("{id}")
    public ResponseEntity<Product> getById(@PathVariable("id") Long id) {
        try {
            Product product = productService.getProductById(id);
            return ResponseEntity.ok(product);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * Добавление нового продукта.
     *
     * @param product Объект продукта, который нужно добавить.
     * @return ResponseEntity с созданным продуктом и статусом "Created".
     */
    @PostMapping("/add")
    @PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
    public ResponseEntity<Product> addProduct(@RequestBody Product product) {
        Product newProduct = productService.addProduct(product);
        return ResponseEntity.status(HttpStatus.CREATED).body(newProduct);
    }

    /**
     * Обновление существующего продукта.
     *
     * @param id Идентификатор продукта, который нужно обновить.
     * @param productDetails Обновлённые данные продукта.
     * @return ResponseEntity с обновлённым продуктом или статусом "Not Found" в случае ошибки.
     */
    @PutMapping("/{id}")
    @PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
    public ResponseEntity<Product> updateProduct(
            @PathVariable Long id,
            @RequestBody Product productDetails
    ) {
        try {
            Product updatedProduct = productService.updateProduct(id, productDetails);
            return ResponseEntity.ok(updatedProduct);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }

    }

    /**
     * Удаление продукта по его идентификатору.
     *
     * @param id Идентификатор продукта, который нужно удалить.
     * @return ResponseEntity с пустым телом и статусом "No Content" или статусом "Not Found" в случае ошибки.
     */
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> deleteProduct(@PathVariable("id") Long id) {

        try {
            productService.deleteProduct(id);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * Получение продуктов по идентификатору категории и её подкатегориям с поддержкой пагинации.
     *
     * @param categoryId Идентификатор категории.
     * @param limit Максимальное количество продуктов, возвращаемых за один запрос (по умолчанию 10).
     * @param skip Количество продуктов, которые нужно пропустить (по умолчанию 0).
     * @return ResponseEntity с обёрнутым списком продуктов, общим количеством продуктов, лимитом и количеством пропущенных элементов.
     */
    @GetMapping("/category/{categoryId}")
    public ResponseEntity<ResponseWrapper<Product>> getProductsByCategoryAndDescendants(
            @PathVariable Long categoryId,
            @RequestParam(defaultValue = "10") int limit,
            @RequestParam(defaultValue = "0") int skip
    ) {
        List<Product> products = productService.getProductsByCategoryAndSubcategories(categoryId);
        ResponseWrapper<Product> response = new ResponseWrapper<>(
                limit == 0
                        ? products.stream().skip(skip).collect(Collectors.toList())
                        : products.stream().skip(skip).limit(limit).collect(Collectors.toList()),
                products.size(),
                limit,
                skip
        );
        return ResponseEntity.ok(response);
    }

    /**
     * Поиск продуктов по имени с поддержкой пагинации.
     *
     * @param name Имя продукта для поиска.
     * @param limit Максимальное количество продуктов, возвращаемых за один запрос (по умолчанию 10).
     * @param skip Количество продуктов, которые нужно пропустить (по умолчанию 0).
     * @return ResponseEntity с обёрнутым списком продуктов, общим количеством продуктов, лимитом и количеством пропущенных элементов.
     */
    @GetMapping("/search")
    public ResponseEntity<ResponseWrapper<Product>> getProductsByName(
            @RequestParam String name,
            @RequestParam(defaultValue = "10") int limit,
            @RequestParam(defaultValue = "0") int skip
    ) {
        List<Product> products = productService.getProductsByNameIgnoreCaseAndSpace(name);
        ResponseWrapper<Product> response = new ResponseWrapper<>(
                limit == 0
                        ? products.stream().skip(skip).collect(Collectors.toList())
                        : products.stream().skip(skip).limit(limit).collect(Collectors.toList()),
                products.size(),
                limit,
                skip
        );
        return ResponseEntity.ok(response);
    }
}
