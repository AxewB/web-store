package ru.aksenov.onlineshop.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import ru.aksenov.onlineshop.helperClasses.ResponseWrapper;
import ru.aksenov.onlineshop.models.Product;
import ru.aksenov.onlineshop.repository.ProductRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/products")
public class ProductController {

    @Autowired
    ProductRepository productRepository;

    public ProductController(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @GetMapping
    public ResponseWrapper<Product> list(
            @RequestParam(defaultValue = "10") int limit,
            @RequestParam(defaultValue = "0") int skip) {
        List<Product> data = productRepository.findAll();
        int size = data.size();
        return new ResponseWrapper<>(
                data.subList(skip, Math.min(skip + limit, size)),
                size,
                limit,
                skip
        );
    }

    @GetMapping("{id}")
    public Product getById(@PathVariable("id") Product product) {
        return product;
    }

    @GetMapping("/search/{searchText}")
    public ResponseWrapper<Product> searchProducts(
            @PathVariable String searchText,
            @RequestParam(defaultValue = "10") int limit,
            @RequestParam(defaultValue = "0") int skip) {

        List<Product> data = productRepository.findByName(searchText);
        int size = data.size();
        return new ResponseWrapper<>(
                data.subList(skip, Math.min(skip + limit, size)),
                size,
                limit,
                skip
        );
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public Product create(@RequestBody Product product) {
        return productRepository.save(product);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("{id}")
    public Product update(@PathVariable("id") Product productFromDb,
                          @RequestBody Product product) {
        BeanUtils.copyProperties(product, productFromDb, "id");
        return productRepository.save(productFromDb);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("{id}")
    public void delete(@PathVariable("id") Product product) {
        productRepository.delete(product);
    }
}