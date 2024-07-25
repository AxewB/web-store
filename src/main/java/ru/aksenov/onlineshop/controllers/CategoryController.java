package ru.aksenov.onlineshop.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import ru.aksenov.onlineshop.models.Category;
import ru.aksenov.onlineshop.repository.CategoryRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.web.bind.annotation.*;
import ru.aksenov.onlineshop.service.CategoryService;

import java.util.List;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/category")
public class CategoryController {

    @Autowired
    CategoryService categoryService;

    @GetMapping
    public List<Category> getAllCategories() {
        return categoryService.getAllCategories();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Category> getCategoryById(@PathVariable Long id) {
        try {
            Category category = categoryService.getCategoryById(id);
            return ResponseEntity.ok(category);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/{id}/children")
    public List<Category> getChildren(@PathVariable Long id) {
        return categoryService.getChildren(id);
    }

    @GetMapping("/{id}/path")
    public List<Category> getPathFromRoot(@PathVariable Long id) {
        return categoryService.getPathFromRoot(id);
    }

    @PostMapping
    public ResponseEntity<Category> addCategory(
            @RequestParam String name,
            @RequestParam(required = false) Long parentId
    ) {
        Category category = categoryService.addCategory(name, parentId);
        return ResponseEntity.status(HttpStatus.CREATED).body(category);
    }
}
