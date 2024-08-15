package ru.aksenov.onlineshop;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.aksenov.onlineshop.models.Category;
import ru.aksenov.onlineshop.repository.CategoryRepository;
import ru.aksenov.onlineshop.service.CategoryService;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class CategoryServiceTest {
    @Mock
    private CategoryRepository categoryRepository;

    @InjectMocks
    private CategoryService categoryService;

    private Category category;
    private Category parentCategory;

    @BeforeEach
    void setUp() {
    }

    @Test
    void testAddCategory_withParent() {
        Category parent = new Category();
        parent.setId(1L);

        when(categoryRepository.findById(1L)).thenReturn(Optional.of(parent));
        when(categoryRepository.save(any(Category.class))).thenAnswer(invocation -> invocation.getArgument(0));

        Category newCategory = categoryService.addCategory("child", 1L);

        assertNotNull(newCategory);
        assertEquals("child", newCategory.getName());
        assertEquals(newCategory.getParent(), parent);

        verify(categoryRepository, times(1)).findById(1L);
        verify(categoryRepository, times(1)).save(newCategory);
    }

    @Test
    void testAddCategory_withoutParent() {
        when(categoryRepository.save(any(Category.class))).thenAnswer(invocation -> invocation.getArgument(0));

        Category newCategory = categoryService.addCategory("category", null);

        assertNotNull(newCategory);
        assertEquals("category", newCategory.getName());
        assertNull(newCategory.getParent());

        verify(categoryRepository, times(1)).save(newCategory);
    }

    @Test
    void testAddCategory() {
        Category sentCategory = new Category();
        sentCategory.setName("new category");

        when(categoryRepository.save(any(Category.class))).thenAnswer(invocation -> invocation.getArgument(0));

        Category newCategory = categoryService.addCategory(sentCategory);

        assertNotNull(newCategory);

        verify(categoryRepository, times(1)).save(newCategory);
    }

    @Test
    void testDeleteCategory() {
        Category categoryToDelete = new Category();
        categoryToDelete.setId(3L);

        when(categoryRepository.findById(3L)).thenReturn(Optional.of(categoryToDelete));

        categoryService.deleteCategory(3L);

        verify(categoryRepository, times(1)).findById(3L);
        verify(categoryRepository, times(1)).delete(categoryToDelete);
    }

    @Test
    void testGetAllCategories() {
        List<Category> categoryList = List.of(
                new Category("cat 1", null),
                new Category("cat 2", null)
        );

        when(categoryRepository.findAll()).thenReturn(categoryList);

        List<Category> list = categoryService.getAllCategories();

        assertNotNull(categoryList);
        assertEquals(2, categoryList.size());
        assertTrue(list.containsAll(categoryList));

        verify(categoryRepository, times(1)).findAll();
    }

    @Test
    void testGetAllChildCategoryIds() {
        Category parent = new Category() {{
            setId(1L);
        }};
        Category child1 = new Category() {{
            setId(2L);
            setParent(parent);
        }};
        Category child2 = new Category() {{
            setId(3L);
            setParent(parent);
        }};

        when(categoryRepository.findByParentId(1L)).thenReturn(List.of(child1, child2));
        when(categoryRepository.findByParentId(2L)).thenReturn(List.of());
        when(categoryRepository.findByParentId(3L)).thenReturn(List.of());

        Set<Long> children = categoryService.getAllChildCategoryIds(1L);

        assertNotNull(children);
        assertTrue(children.contains(child1.getId()));
        assertTrue(children.contains(child2.getId()));
        assertEquals(2, children.size());

        verify(categoryRepository, times(1)).findByParentId(1L);
        verify(categoryRepository, times(1)).findByParentId(2L);
        verify(categoryRepository, times(1)).findByParentId(3L);

    }

    @Test
    void testGetBottomLayer() {
        Category parent = new Category() {{
            setId(1L);
            ;
        }};
        Category child1 = new Category() {{
            setId(2L);
            setParent(parent);
        }};
        Category child2 = new Category() {{
            setId(3L);
            setParent(parent);
        }};
        parent.setChildren(List.of(child1, child2));

        List<Category> allCategories = List.of(parent, child1, child2);

        when(categoryRepository.findAll()).thenReturn(allCategories);

        List<Category> bottomLayer = categoryService.getBottomLayer();

        assertNotNull(bottomLayer);
        assertEquals(2, bottomLayer.size());
        assertTrue(allCategories.containsAll(bottomLayer));
        assertTrue(bottomLayer.contains(child1));
        assertTrue(bottomLayer.contains(child2));
        assertFalse(bottomLayer.contains(parent));

        verify(categoryRepository, times(1)).findAll();
    }

    @Test
    void testGetCategoryById_success() {
        Category category = new Category() {{
            setId(1L);
        }};

        when(categoryRepository.findById(1L)).thenReturn(Optional.of(category));

        Category newCategory = categoryService.getCategoryById(1L);

        assertNotNull(newCategory);
        assertEquals(1L, newCategory.getId());

        verify(categoryRepository, times(1)).findById(1L);
    }

    @Test
    void testGetCategoryById_categoryNotFound() {
        when(categoryRepository.findById(1L)).thenReturn(Optional.empty());

        RuntimeException exception = assertThrows(
                RuntimeException.class,
                () -> categoryService.getCategoryById(1L)
        );
        assertEquals("Category not found", exception.getMessage());

        verify(categoryRepository, times(1)).findById(1L);
    }

    @Test
    void testGetChildren() {
        Category parent = new Category() {{
            setId(1L);
            ;
        }};
        Category child1 = new Category() {{
            setId(2L);
            setParent(parent);
        }};
        Category child2 = new Category() {{
            setId(3L);
            setParent(parent);
        }};
        parent.setChildren(List.of(child1, child2));

        when(categoryRepository.findByParentId(1L)).thenReturn(List.of(child1, child2));

        List<Category> children = categoryService.getChildren(1L);

        assertNotNull(children);
        assertEquals(2, children.size());
        assertTrue(children.contains(child1));
        assertTrue(children.contains(child2));
        assertFalse(children.contains(parent));

        verify(categoryRepository, times(1)).findByParentId(1L);
    }

    @Test
    void testGetPathFromRoot_success() {
        Category root1 = new Category("root1", null) {{
            setId(1L);
        }};
        Category root2 = new Category("root2", null) {{
            setId(2L);
        }};
        Category category1 = new Category("category1", root1) {{
            setId(3L);
        }};
        Category category2 = new Category("category2", root1) {{
            setId(4L);
        }};
        Category category3 = new Category("category3", category2) {{
            setId(5L);
        }};


        when(categoryRepository.findById(5L)).thenReturn(Optional.of(category3));

        List<Category> path = categoryService.getPathFromRoot(5L);

        assertNotNull(path);
        assertEquals(3, path.size());
        assertTrue(path.containsAll(List.of(category3, category2, root1)));
        assertEquals(category3, path.get(path.size() - 1));
        assertEquals(root1, path.get(0));

        verify(categoryRepository, times(1)).findById(5L);
    }

    @Test
    void testGetPathFromRoot_categoryNotFound() {
        when(categoryRepository.findById(1L)).thenReturn(Optional.empty());

        RuntimeException exception = assertThrows(
                RuntimeException.class,
                () -> categoryService.getPathFromRoot(1L)
        );

        assertEquals("Category not found", exception.getMessage());
        verify(categoryRepository, times(1)).findById(1L);
    }

    @Test
    void testGetRootLayer() {
        Category root1 = new Category() {{
            setId(1L);
        }};
        Category root2 = new Category() {{
            setId(1L);
        }};
        Category child1 = new Category() {{
            setId(2L);
            setParent(root1);
        }};
        Category child2 = new Category() {{
            setId(3L);
            setParent(root2);
        }};
        root1.setChildren(List.of(child1));
        root2.setChildren(List.of(child2));

        when(categoryRepository.findByParentIdIsNull()).thenReturn(
                List.of(root1, root2)
        );

        List<Category> roots = categoryService.getRootLayer();

        assertNotNull(roots);
        assertEquals(2, roots.size());
        assertTrue(roots.containsAll(List.of(root1, root2)));
        assertFalse(roots.containsAll(List.of(child1, child2)));

        verify(categoryRepository, times(1)).findByParentIdIsNull();
    }

    @Test
    void testUpdateCategory_success() {
        Category parent = new Category() {{
            setId(1L);
        }};
        Category category = new Category() {{
            setId(2L);
            setName("oldName");
            setParent(null);
        }};
        Category newCategoryData = new Category() {{
            setName("newName");
            setParent(parent);
        }};

        when(categoryRepository.findById(2L)).thenReturn(Optional.of(category));
        when(categoryRepository.save(any(Category.class))).thenAnswer(invocation -> invocation.getArgument(0));

        Category updatedCategory = categoryService.updateCategory(2L, newCategoryData);

        assertNotNull(updatedCategory);
        assertEquals("newName", updatedCategory.getName());
        assertEquals(2L, updatedCategory.getId());
        assertEquals(parent, updatedCategory.getParent());

        verify(categoryRepository, times(1)).findById(2L);
        verify(categoryRepository, times(1)).save(updatedCategory);
    }

    @Test
    void testUpdateCategory_categoryNotFound() {
        Category parent = new Category() {{
            setId(1L);
        }};
        Category newCategoryData = new Category() {{
            setName("newName");
            setParent(parent);
        }};

        when(categoryRepository.findById(2L)).thenReturn(Optional.empty());

        RuntimeException exception = assertThrows(
                RuntimeException.class,
                () -> categoryService.updateCategory(2L, newCategoryData)
        );

        assertEquals("Category not found", exception.getMessage());
    }
}
