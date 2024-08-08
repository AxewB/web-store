package ru.aksenov.onlineshop;


import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import ru.aksenov.onlineshop.controllers.ProductController;
import ru.aksenov.onlineshop.models.Product;
import ru.aksenov.onlineshop.service.ProductService;

import java.util.ArrayList;
import java.util.List;

@ExtendWith(MockitoExtension.class)
public class ProductControllerTest {
    private String API_URL = "/api/products";

    @InjectMocks
    private ProductController productController;

    private MockMvc mockMvc;

    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(productController).build();
        objectMapper = new ObjectMapper();
    }

    @Test
    void allProducts() throws Exception {
        mockMvc.perform(get(API_URL)).andExpect(status().isOk());
    }

    @Test
    void singleProduct() throws Exception {
        mockMvc.perform(get(API_URL + "/{id}", 1L))
                .andExpect(status().isOk());
    }

    @Test
    void getProductsByCategory() throws Exception {
        mockMvc.perform(get(API_URL + "/category/{categoryId}", 1L))
                .andExpect(status().isOk());
    }

    @Test
    void getProductsByName() throws Exception {
        mockMvc.perform(get(API_URL + "/search?name={name}", "mac"))
                .andExpect(status().isOk());
    }

    // TODO: ADD BODY
    @Test
    void addProduct() throws Exception {
        Product product = new Product(
                1L,
                100,
                10,
                new ArrayList<String>() {{
                    add("https://external-content.duckduckgo.com/iu/?u=https%3A%2F%2Fmy.alfred.edu%2Fzoom%2F_images%2Ffoster-lake.jpg&f=1&nofb=1&ipt=a066b3682257b788dcdf92091a3e5a347f2062cb4de797b3964317a9b1921739&ipo=images");
                    add("https://external-content.duckduckgo.com/iu/?u=https%3A%2F%2Fmy.alfred.edu%2Fzoom%2F_images%2Ffoster-lake.jpg&f=1&nofb=1&ipt=a066b3682257b788dcdf92091a3e5a347f2062cb4de797b3964317a9b1921739&ipo=images");
                    add("https://external-content.duckduckgo.com/iu/?u=https%3A%2F%2Fmy.alfred.edu%2Fzoom%2F_images%2Ffoster-lake.jpg&f=1&nofb=1&ipt=a066b3682257b788dcdf92091a3e5a347f2062cb4de797b3964317a9b1921739&ipo=images");
                }},
                "https://external-content.duckduckgo.com/iu/?u=https%3A%2F%2Fmy.alfred.edu%2Fzoom%2F_images%2Ffoster-lake.jpg&f=1&nofb=1&ipt=a066b3682257b788dcdf92091a3e5a347f2062cb4de797b3964317a9b1921739&ipo=images",
                "someDescription",
                "someName"
        );
        String productJson = objectMapper.writeValueAsString(product);

        mockMvc.perform(post(API_URL + "/add")
                    .contentType(MediaType.APPLICATION_JSON)
                        .content(productJson))
                .andExpect(status().isCreated());
    }

    // TODO: ADD BODY
    @Test
    void updateProduct() throws Exception {


        Product newProduct = new Product(
                1L,
                123,
                5643,
                new ArrayList<String>() {{
                    add("https://external-content.duckduckgo.com/iu/?u=https%3A%2F%2Fmy.alfred.edu%2Fzoom%2F_images%2Ffoster-lake.jpg&f=1&nofb=1&ipt=a066b3682257b788dcdf92091a3e5a347f2062cb4de797b3964317a9b1921739&ipo=images");
                    add("https://external-content.duckduckgo.com/iu/?u=https%3A%2F%2Fmy.alfred.edu%2Fzoom%2F_images%2Ffoster-lake.jpg&f=1&nofb=1&ipt=a066b3682257b788dcdf92091a3e5a347f2062cb4de797b3964317a9b1921739&ipo=images");
                    add("https://external-content.duckduckgo.com/iu/?u=https%3A%2F%2Fmy.alfred.edu%2Fzoom%2F_images%2Ffoster-lake.jpg&f=1&nofb=1&ipt=a066b3682257b788dcdf92091a3e5a347f2062cb4de797b3964317a9b1921739&ipo=images");
                }},
                "https://external-content.duckduckgo.com/iu/?u=https%3A%2F%2Fmy.alfred.edu%2Fzoom%2F_images%2Ffoster-lake.jpg&f=1&nofb=1&ipt=a066b3682257b788dcdf92091a3e5a347f2062cb4de797b3964317a9b1921739&ipo=images",
                "someDescription",
                "someName"
        );
        String newProductJson = objectMapper.writeValueAsString(newProduct);

        mockMvc.perform(put(API_URL + "/{id}", 1L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(newProductJson))
                .andExpect(status().isOk());
    }

    @Test
    void deleteProduct() throws Exception {
        Product product = new Product(
                1L,
                100,
                10,
                new ArrayList<String>() {{
                    add("https://external-content.duckduckgo.com/iu/?u=https%3A%2F%2Fmy.alfred.edu%2Fzoom%2F_images%2Ffoster-lake.jpg&f=1&nofb=1&ipt=a066b3682257b788dcdf92091a3e5a347f2062cb4de797b3964317a9b1921739&ipo=images");
                    add("https://external-content.duckduckgo.com/iu/?u=https%3A%2F%2Fmy.alfred.edu%2Fzoom%2F_images%2Ffoster-lake.jpg&f=1&nofb=1&ipt=a066b3682257b788dcdf92091a3e5a347f2062cb4de797b3964317a9b1921739&ipo=images");
                    add("https://external-content.duckduckgo.com/iu/?u=https%3A%2F%2Fmy.alfred.edu%2Fzoom%2F_images%2Ffoster-lake.jpg&f=1&nofb=1&ipt=a066b3682257b788dcdf92091a3e5a347f2062cb4de797b3964317a9b1921739&ipo=images");
                }},
                "https://external-content.duckduckgo.com/iu/?u=https%3A%2F%2Fmy.alfred.edu%2Fzoom%2F_images%2Ffoster-lake.jpg&f=1&nofb=1&ipt=a066b3682257b788dcdf92091a3e5a347f2062cb4de797b3964317a9b1921739&ipo=images",
                "someDescription",
                "someName"
        );

        mockMvc.perform(delete(API_URL + "/{id}", 1L))
                .andExpect(status().isNoContent());
    }
}
