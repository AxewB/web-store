package ru.aksenov.onlineshop.controllers;

import ru.aksenov.onlineshop.models.Category;
import ru.aksenov.onlineshop.repository.CategoryRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/category")
public class CategoryController {
    CategoryRepository categoryRepository;

    public CategoryController(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @GetMapping
    public List<Category> list() {
        return categoryRepository.findAll();
    }

    @PostMapping
    public Category create(@PathVariable("id") Category category) {
        return categoryRepository.save(category);
    }

    @PutMapping("{id}")
    public Category update(@PathVariable("id") Category categoryFromDb,
                           @RequestBody Category category) {
        BeanUtils.copyProperties(categoryFromDb, category, "id");
        return categoryRepository.save(category);
    }

    @DeleteMapping("{id}")
    public void delete(@PathVariable("id") Category category) {
        categoryRepository.delete(category);
    }
}
