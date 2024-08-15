package ru.aksenov.onlineshop;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.aksenov.onlineshop.models.Category;
import ru.aksenov.onlineshop.models.Product;
import ru.aksenov.onlineshop.repository.CategoryRepository;
import ru.aksenov.onlineshop.repository.ProductRepository;
import ru.aksenov.onlineshop.service.CategoryService;
import ru.aksenov.onlineshop.service.ProductService;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.anySet;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ProductServiceTest {
    @Mock
    private ProductRepository productRepository;

    @Mock
    private CategoryRepository categoryRepository;

    @Mock
    private CategoryService categoryService;

    @InjectMocks
    private ProductService productService;

    Product product1;
    Product product2;
    Category category1;
    Category category2;

    @BeforeEach
    void setUp() {
        category1 = new Category();
        category1.setId(1L);
        category1.setName("category1");

        category2 = new Category();
        category2.setId(2L);
        category2.setName("category2");
        category2.setParent(category1);
        category1.setChildren(List.of(category2));

        product1 = new Product();
        product1.setName("product1");
        product1.setId(1L);
        product1.setCategories(Set.of(category1));

        product2 = new Product();
        product2.setName("product2");
        product2.setId(2L);
        product2.setCategories(Set.of(category1));
    }

    @Test
    void addMultipleProducts() {
        List<Product> products = List.of(product1, product2);

        when(productRepository.save(any(Product.class))).thenAnswer(invocation -> invocation.getArgument(0));

        List<Product> newProducts = productService.addMultipleProducts(products);

        assertNotNull(newProducts);
        assertTrue(newProducts.containsAll(products));
        assertEquals(2, newProducts.size());

        verify(productRepository, times(2)).save(any(Product.class));
    }

    @Test
    void addProduct() {
        when(productRepository.save(any(Product.class))).thenReturn(product1);

        Product newProduct = productService.addProduct(product1);

        assertNotNull(newProduct);
        assertEquals(product1.getName(), newProduct.getName());
        assertEquals(product1.getCategories(), newProduct.getCategories());

        verify(productRepository, times(1)).save(newProduct);
    }

    @Test
    void deleteProduct_success() {
        when(productRepository.findById(1L)).thenReturn(Optional.of(product1));

        productService.deleteProduct(1L);

        verify(productRepository, times(1)).findById(1L);
        verify(productRepository, times(1)).delete(product1);
    }

    @Test
    void deleteProduct_productNotFound() {

        RuntimeException exception = assertThrows(
                RuntimeException.class,
                () -> productService.deleteProduct(1L)
        );

        verify(productRepository, times(1)).findById(1L);
    }

    @Test
    void getAllProducts() {
        List<Product> products = List.of(product1, product2);

        when(productRepository.findAll()).thenReturn(products);

        List<Product> newProducts = productService.getAllProducts();

        assertNotNull(newProducts);
        assertTrue(newProducts.containsAll(products));
        assertEquals(2, newProducts.size());

        verify(productRepository, times(1)).findAll();
    }

    @Test
    void getProductById_success() {
        when(productRepository.findById(1L)).thenReturn(Optional.of(product1));

        Product newProduct = productService.getProductById(1L);

        assertNotNull(newProduct);
        assertEquals(product1.getName(), newProduct.getName());

        verify(productRepository, times(1)).findById(1L);
    }

    void getProductById_productNotFound() {
        when(productRepository.findById(1L)).thenReturn(Optional.empty());

        RuntimeException exception = assertThrows(
                RuntimeException.class,
                () -> productService.getProductById(1L)
        );

        assertEquals("Product not found", exception.getMessage());
    }

    @Test
    void getProductsByCategoryAndSubcategories_withNullCategory() {
        List<Product> allProducts = List.of(product1, product2);

        when(productService.getAllProducts()).thenReturn(allProducts);

        // Act
        List<Product> result = productService.getProductsByCategoryAndSubcategories(null);

        // Assert
        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals("product1", result.get(0).getName());
        assertEquals("product2", result.get(1).getName());

        verify(productRepository, never()).findByCategoriesIdIn(anySet());
    }

    @Test
    void testGetProductsByCategoryAndSubcategories_WithCategoryId() {
        Set<Long> categoryIds = Set.of(category1.getId(), category2.getId());
        when(categoryService.getAllChildCategoryIds(1L)).thenReturn(categoryIds);
        when(productRepository.findByCategoriesIdIn(categoryIds)).thenReturn(List.of(product1, product2));

        // Act
        List<Product> result = productService.getProductsByCategoryAndSubcategories(1L);

        // Assert
        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals("product1", result.get(0).getName());
        assertEquals("product2", result.get(1).getName());

        verify(categoryService, times(1)).getAllChildCategoryIds(1L);
        verify(productRepository, times(1)).findByCategoriesIdIn(categoryIds);
    }

    @Test
    void getProductsByName_withoutName() {
        List<Product> products = List.of(product1, product2);
        when(productRepository.findAll()).thenReturn(products);

        List<Product> newProducts = productService.getProductsByName("");

        assertNotNull(newProducts);
        assertEquals("product1", newProducts.get(0).getName());
        assertEquals("product2", newProducts.get(1).getName());
        assertEquals(2, newProducts.size());

        verify(productRepository, times(1)).findAll();
        verify(productRepository, never()).findByName("");
    }

    @Test
    void getProductsByName_withName() {
        String name = "product1";
        when(productRepository.findByName(name)).thenReturn(List.of(product1));

        List<Product> newProducts = productService.getProductsByName(name);

        assertTrue(newProducts.contains(product1));
        assertFalse(newProducts.contains(product2));
        assertEquals(1, newProducts.size());


        verify(productRepository, times(1)).findByName(name);
        verify(productRepository, never()).findAll();
    }

    @Test
    void getProductsByNameIgnoreCaseAndSpace() {
        String name = "ct1";
        when(productRepository.findByNameIgnoreCaseAndSpaces(name)).thenReturn(List.of(product1));

        List<Product> newProducts = productService.getProductsByNameIgnoreCaseAndSpace(name);

        assertTrue(newProducts.contains(product1));
        assertFalse(newProducts.contains(product2));
        assertEquals(1, newProducts.size());


        verify(productRepository, times(1)).findByNameIgnoreCaseAndSpaces(name);
    }

    @Test
    void updateProduct_success() {
        Product productData = new Product() {{
            setName("newName");
            setCost(200);
            setQuantity(30);
            setCategories(Set.of(category1));
        }};
        when(productRepository.findById(1L)).thenReturn(Optional.of(product1));
        when(categoryRepository.findById(1L)).thenReturn(Optional.of(category1));
        when(productRepository.save(any(Product.class))).thenReturn(product1);

        // Act
        Product updatedProduct = productService.updateProduct(1L, productData);

        // Assert
        assertNotNull(updatedProduct);
        assertEquals("newName", updatedProduct.getName());
        assertEquals(1, updatedProduct.getCategories().size());
        assertTrue(updatedProduct.getCategories().contains(category1));

        verify(productRepository, times(1)).findById(1L);
        verify(categoryRepository, times(1)).findById(1L);
        verify(productRepository, times(1)).save(product1);
    }

    @Test
    void updateProduct_productNotFound() {
        Product productData = new Product() {{
            setName("newName");
            setCost(200);
            setQuantity(30);
        }};
        when(productRepository.findById(1L)).thenReturn(Optional.empty());

        RuntimeException exception = assertThrows(
                RuntimeException.class,
                () -> productService.updateProduct(1L, productData)
        );

        assertEquals("Product not found", exception.getMessage());
    }

    @Test
    void updateProduct_categoryNotFound() {
        Product productData = new Product() {{
            setName("newName");
            setCost(200);
            setQuantity(30);
            setCategories(Set.of(category1, category2));
        }};
        when(productRepository.findById(1L)).thenReturn(Optional.empty());
        when(categoryRepository.findById(1L)).thenReturn(Optional.empty());

        RuntimeException exception = assertThrows(
                RuntimeException.class,
                () -> productService.updateProduct(1L, productData)
        );

        assertEquals("Category not found", exception.getMessage());
    }
}
