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

    @GetMapping("/root")
    public List<Category> getRootLayer() {
        return categoryService.getRootLayer();
    }

    @GetMapping("/bottom")
    public List<Category> getBottomLayer() {
        return categoryService.getBottomLayer();
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
    public ResponseEntity<Category> addCategory(@RequestBody Category category) {
        System.out.println(category);
        categoryService.addCategory(category);
        return ResponseEntity.status(HttpStatus.CREATED).body(category);
    }

    @PutMapping
    public ResponseEntity<Category> updateCategory(@RequestParam Long id, @RequestBody Category category) {
        try {
            Category updatedCategory = categoryService.updateCategory(id, category);
            return ResponseEntity.ok(updatedCategory);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping
    public ResponseEntity<Category> deleteCategory(@RequestParam Long id) {
        try {
            categoryService.deleteCategory(id);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }
}
