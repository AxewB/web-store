package ru.aksenov.onlineshop.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.aksenov.onlineshop.models.Category;
import ru.aksenov.onlineshop.repository.CategoryRepository;

import java.util.ArrayList;
import java.util.List;

@Service
public class CategoryService {

    @Autowired
    CategoryRepository categoryRepository;

    public Category addCategory(String name, Long parentId) {
        Category parent = null;
        if (parentId != null) {
            parent = categoryRepository.findById(parentId).orElseThrow(() -> new RuntimeException("Parent category not found"));
        }
        Category category = new Category(name, parent);
        return categoryRepository.save(category);
    }

    public List<Category> getAllCategories() {
        return categoryRepository.findAll();
    }

    public Category getCategoryById(Long id) {
        return categoryRepository.findById(id).orElseThrow(() -> new RuntimeException("Category not found"));
    }

    public List<Category> getChildren(Long parentId) {
        return categoryRepository.findByParentId(parentId);
    }

    public List<Category> getPathFromRoot(Long categoryId) {
        List<Category> path = new ArrayList<>();
        Category category = categoryRepository.findById(categoryId).orElseThrow(() -> new RuntimeException("Category not found"));
        while (category.getParent() != null) {
            path.add(0, category);
            category = category.getParent();
        }
        return path;
    }
}
