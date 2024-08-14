package ru.aksenov.onlineshop.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import ru.aksenov.onlineshop.models.Category;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import ru.aksenov.onlineshop.service.CategoryService;

import java.util.List;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/category")
public class CategoryController {

    @Autowired
    CategoryService categoryService;

    /**
     * Получение списка всех категорий.
     *
     * @return Список всех категорий.
     */
    @GetMapping
    public List<Category> getAllCategories() {
        return categoryService.getAllCategories();
    }

    /**
     * Получение категории по её идентификатору.
     *
     * @param id Идентификатор категории.
     * @return ResponseEntity с категорией, если она найдена, или статусом "Not Found" в случае ошибки.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Category> getCategoryById(@PathVariable Long id) {
        try {
            Category category = categoryService.getCategoryById(id);
            return ResponseEntity.ok(category);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * Получение списка всех категорий, включая дочерние категории.
     *
     * @return ResponseEntity со списком категорий.
     */
    @GetMapping("/all-with-children")
    public ResponseEntity<List<Category>> getAllCategoriesWithChildren() {
        List<Category> categories = categoryService.getAllCategories();
        return ResponseEntity.ok(categories);
    }

    /**
     * Получение корневого уровня категорий.
     *
     * @return Список корневых категорий.
     */
    @GetMapping("/root")
    public List<Category> getRootLayer() {
        return categoryService.getRootLayer();
    }

    /**
     * Получение категорий нижнего уровня.
     *
     * @return Список категорий нижнего уровня.
     */
    @GetMapping("/bottom")
    public List<Category> getBottomLayer() {
        return categoryService.getBottomLayer();
    }

    /**
     * Получение дочерних категорий для указанной категории.
     *
     * @param id Идентификатор родительской категории.
     * @return Список дочерних категорий.
     */
    @GetMapping("/{id}/children")
    public List<Category> getChildren(@PathVariable Long id) {
        return categoryService.getChildren(id);
    }

    /**
     * Получение пути от корня до указанной категории.
     *
     * @param id Идентификатор категории.
     * @return Список категорий, представляющий путь от корня до указанной категории.
     */
    @GetMapping("/{id}/path")
    public List<Category> getPathFromRoot(@PathVariable Long id) {
        return categoryService.getPathFromRoot(id);
    }

    /**
     * Создание новой категории.
     *
     * @param category Объект категории, который нужно создать.
     * @return ResponseEntity с созданной категорией и статусом "Created".
     */
    @PostMapping("/add")
    @PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
    public ResponseEntity<Category> createCategory(@RequestBody Category category) {
        Category createdCategory = categoryService.addCategory(category);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdCategory);
    }

    /**
     * Обновление существующей категории.
     *
     * @param id       Идентификатор категории, которую нужно обновить.
     * @param category Обновлённый объект категории.
     * @return ResponseEntity с обновлённой категорией или статусом "Not Found" в случае ошибки.
     */
    @PutMapping("/{id}")
    @PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
    public ResponseEntity<Category> updateCategory(@PathVariable Long id, @RequestBody Category category) {
        System.out.println(category);
        try {
            Category updatedCategory = categoryService.updateCategory(id, category);
            return ResponseEntity.ok(updatedCategory);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * Удаление категории по её идентификатору.
     *
     * @param id Идентификатор категории, которую нужно удалить.
     * @return ResponseEntity с пустым телом и статусом "No Content" или статусом "Not Found" в случае ошибки.
     */
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
    public ResponseEntity<Category> deleteCategory(@PathVariable Long id) {
        try {
            categoryService.deleteCategory(id);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }
}
