package ru.aksenov.onlineshop.helperClasses;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import ru.aksenov.onlineshop.models.*;
import ru.aksenov.onlineshop.repository.CategoryRepository;
import ru.aksenov.onlineshop.repository.ProductRepository;
import ru.aksenov.onlineshop.repository.RoleRepository;
import ru.aksenov.onlineshop.repository.UserRepository;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Component
public class DataInitializer implements CommandLineRunner {
    @Autowired
    RoleRepository roleRepository;

    @Autowired
    UserRepository userRepository;
    @Autowired
    PasswordEncoder encoder;

    @Autowired
    ProductRepository productRepository;

    @Autowired
    CategoryRepository categoryRepository;

    @Autowired
    DataLoader dataLoader;

    @Override
    public void run(String... args) throws Exception {
        // Проверка и создание ролей, если они отсутствуют
        if (roleRepository.findAll().isEmpty()) {
            Role userRole = new Role(ERole.ROLE_USER);
            Role adminRole = new Role(ERole.ROLE_ADMIN);
            roleRepository.save(userRole);
            roleRepository.save(adminRole);
        }

        // Создание пользователя с ролью администратора, если он не существует
        Role adminRole = roleRepository.findByName(ERole.ROLE_ADMIN).orElseThrow(() -> new RuntimeException("There is no such role"));
        if (!userRepository.existsByRoles(adminRole)) {
            Set<Role> roles = new HashSet<>();
            roles.add(adminRole);
            User adminUser = new User();
            adminUser.setUsername("admin");
            adminUser.setPassword(encoder.encode("admin12345"));
            adminUser.setEmail("admin@onlineshop.com");
            adminUser.setRoles(roles);

            userRepository.save(adminUser);
        }

        // Загрузка и сохранение категорий, если они отсутствуют
        if (categoryRepository.findAll().isEmpty()) {
            List<CategoryJsonInfo> categoriesJson = dataLoader.loadCategories("src/data/categories.json");

            for (CategoryJsonInfo categoryJsonInfo : categoriesJson) {
                Category category = new Category();
                category.setId(categoryJsonInfo.getId());
                category.setName(categoryJsonInfo.getName());


                if (categoryJsonInfo.getParent() != null) {
                    Long parentId = categoryJsonInfo.getParent();
                    Category parent = categoryRepository.findById(parentId).orElseThrow(() -> new RuntimeException("Category not found"));
                    category.setParent(parent);
                }


                categoryRepository.save(category);
            }
        }

        // Загрузка и сохранение продуктов и их категорий, если они отсутствуют
        if (productRepository.findAll().isEmpty()) {
            List<Product> products = dataLoader.loadProducts("src/data/products.json");
            productRepository.saveAll(products);

            List<ProductCategoryMapper> productCategoryMappers = dataLoader.loadProductCategories("src/data/product_categories.json");
            for (ProductCategoryMapper mapper : productCategoryMappers) {
                Product product = productRepository.findById(mapper.getProductId()).orElseThrow(() -> new RuntimeException("Product not found"));
                Set<Category> productCategories = new HashSet<>();
                for (Long categoryId : mapper.getCategoryIds()) {
                    Category category = categoryRepository.findById(categoryId).orElseThrow(() -> new RuntimeException("Category not found"));
                    productCategories.add(category);
                }
                product.setCategories(productCategories);
                productRepository.save(product);
            }
        }
    }
}
