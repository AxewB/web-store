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

    /**
     * Добавление новой категории с указанным именем и родительской категорией.
     *
     * @param name     Имя новой категории.
     * @param parentId Идентификатор родительской категории (может быть null).
     * @return Созданная категория.
     * @throws RuntimeException Если родительская категория не найдена (если задан parentId).
     */
    public Category addCategory(String name, Long parentId) {
        Category parent = null;
        if (parentId != null) {
            parent = categoryRepository.findById(parentId).orElseThrow(() -> new RuntimeException("Parent category not found"));
        }
        Category category = new Category(name, parent);
        return categoryRepository.save(category);
    }

    /**
     * Добавление новой категории.
     *
     * @param category Категория для добавления.
     * @return Созданная категория.
     */
    public Category addCategory(Category category) {
        return categoryRepository.save(category);
    }

    /**
     * Получение списка всех категорий.
     *
     * @return Список всех категорий.
     */
    public List<Category> getAllCategories() {
        return categoryRepository.findAll();
    }

    /**
     * Получение категории по её идентификатору.
     *
     * @param id Идентификатор категории.
     * @return Категория с указанным идентификатором.
     * @throws RuntimeException Если категория не найдена.
     */
    public Category getCategoryById(Long id) {
        return categoryRepository.findById(id).orElseThrow(() -> new RuntimeException("Category not found"));
    }

    /**
     * Получение всех дочерних категорий для указанной категории.
     *
     * @param parentId Идентификатор родительской категории.
     * @return Список дочерних категорий.
     */
    public List<Category> getChildren(Long parentId) {
        return categoryRepository.findByParentId(parentId);
    }

    /**
     * Получение всех корневых категорий (без родительских категорий).
     *
     * @return Список корневых категорий.
     */
    public List<Category> getRootLayer() {
        return categoryRepository.findByParentIdIsNull();
    }

    /**
     * Получение всех категорий, не имеющих дочерних категорий.
     *
     * @return Список категорий нижнего уровня.
     */
    public List<Category> getBottomLayer() {
        return categoryRepository.findAll().stream().filter(cat -> cat.getChildren().isEmpty()).collect(Collectors.toList());
    }

    /**
     * Получение пути от корневой категории до указанной категории.
     *
     * @param categoryId Идентификатор категории.
     * @return Список категорий от корня до указанной категории.
     * @throws RuntimeException Если категория не найдена.
     */
    public List<Category> getPathFromRoot(Long categoryId) {
        List<Category> path = new ArrayList<>();
        Category category = categoryRepository.findById(categoryId).orElseThrow(() -> new RuntimeException("Category not found"));
        do {
            path.add(0, category);
            category = category.getParent();
        } while (category != null);
        return path;
    }

    /**
     * Получение всех идентификаторов дочерних категорий для указанной категории.
     *
     * @param parentId Идентификатор родительской категории.
     * @return Множество идентификаторов дочерних категорий.
     */
    public Set<Long> getAllChildCategoryIds(Long parentId) {
        Set<Long> categoryIds = new HashSet<>();
        collectChildCategoryIds(parentId, categoryIds);
        return categoryIds;
    }

    /**
     * Рекурсивный сбор идентификаторов дочерних категорий для указанной категории.
     *
     * @param parentId    Идентификатор родительской категории.
     * @param categoryIds Множество для хранения идентификаторов дочерних категорий.
     */
    private void collectChildCategoryIds(Long parentId, Set<Long> categoryIds) {
        List<Category> childCategories = categoryRepository.findByParentId(parentId);
        for (Category child : childCategories) {
            categoryIds.add(child.getId());
            collectChildCategoryIds(child.getId(), categoryIds);
        }
    }

    /**
     * Обновление существующей категории.
     *
     * @param id            Идентификатор категории для обновления.
     * @param categoryDetails Объект с новыми данными для обновления.
     * @return Обновленная категория.
     * @throws RuntimeException Если категория не найдена.
     */
    public Category updateCategory(Long id, Category categoryDetails) {
        Category category = categoryRepository.findById(id).orElseThrow(() -> new RuntimeException("Category not found"));
        BeanUtils.copyProperties(categoryDetails, category, "id");
        return categoryRepository.save(category);
    }

    /**
     * Удаление категории по её идентификатору.
     *
     * @param id Идентификатор категории для удаления.
     * @throws RuntimeException Если категория не найдена.
     */
    public void deleteCategory(Long id) {
        Category category = categoryRepository.findById(id).orElseThrow(() -> new RuntimeException("Category not found"));
        categoryRepository.delete(category);
    }
}
