package ru.aksenov.onlineshop.service;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.aksenov.onlineshop.models.Category;
import ru.aksenov.onlineshop.repository.CategoryRepository;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

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

    public Category addCategory(Category category) {
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

    public List<Category> getRootLayer() {
        return categoryRepository.findByParentIdIsNull();
    }

    public List<Category> getBottomLayer() {
        return categoryRepository.findAll().stream().filter(cat -> cat.getChildren().isEmpty()).collect(Collectors.toList());
    }

    public List<Category> getPathFromRoot(Long categoryId) {
        List<Category> path = new ArrayList<>();
        Category category = categoryRepository.findById(categoryId).orElseThrow(() -> new RuntimeException("Category not found"));
        do {
            path.add(0, category);
            category = category.getParent();
        } while (category != null);
        return path;
    }

    public Set<Long> getAllChildCategoryIds(Long parentId) {
        Set<Long> categoryIds = new HashSet<>();
        collectChildCategoryIds(parentId, categoryIds);
        return categoryIds;
    }

    private void collectChildCategoryIds(Long parentId, Set<Long> categoryIds) {
        List<Category> childCategories = categoryRepository.findByParentId(parentId);
        for (Category child : childCategories) {
            categoryIds.add(child.getId());
            collectChildCategoryIds(child.getId(), categoryIds);
        }
    }

    public Category updateCategory(Long id, Category categoryDetails) {
        Category category = categoryRepository.findById(id).orElseThrow(() -> new RuntimeException("Category not found"));
        BeanUtils.copyProperties(categoryDetails, category, "id");
        return categoryRepository.save(category);
    }

    public void deleteCategory(Long id) {
        Category category = categoryRepository.findById(id).orElseThrow(() -> new RuntimeException("Category not found"));
        categoryRepository.delete(category);
    }
}
