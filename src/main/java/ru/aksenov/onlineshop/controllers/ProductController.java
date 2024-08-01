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

    @GetMapping("{id}")
    public ResponseEntity<Product> getById(@PathVariable("id") Long id) {
        try {
            Product product = productService.getProductById(id);
            return ResponseEntity.ok(product);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/add")
    @PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
    public ResponseEntity<Product> addProduct(@RequestBody Product product) {
        Product newProduct = productService.addProduct(product);
        return ResponseEntity.status(HttpStatus.CREATED).body(newProduct);
    }

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

    @DeleteMapping("/{id}")
//    @PreAuthorize("hasRole('ADMIN')")
    @PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
    public ResponseEntity<Void> deleteProduct(@PathVariable("id") Long id) {

        try {
            productService.deleteProduct(id);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/category/{categoryId}")
    public ResponseEntity<List<Product>> getProductsByCategoryAndDescendants(@PathVariable Long categoryId) {
        List<Product> products = productService.getProductsByCategoryAndSubcategories(categoryId);
        return ResponseEntity.ok(products);
    }

    @GetMapping("/search/{name}")
    public ResponseEntity<List<Product>> getProductsByName(@PathVariable String name) {
        List<Product> products = productService.getProductsByName(name);
        return ResponseEntity.ok(products);
    }

    @GetMapping("/search")
    public ResponseEntity<List<Product>> searchProductsByName(@RequestParam String name) {
        List<Product> products = productService.getProductsByNameIgnoreCaseAndSpace(name);
        return ResponseEntity.ok(products);
    }
}
