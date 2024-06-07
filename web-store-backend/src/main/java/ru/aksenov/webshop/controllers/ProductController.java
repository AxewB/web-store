package ru.aksenov.webshop.controllers;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.aksenov.webshop.objects.Product;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/product")
@CrossOrigin
public class ProductController {
    private List<Product> productList = new ArrayList<Product>() {{
        add(new Product("123", 123));
        add(new Product("3", 12));
        add(new Product("12", 2));
    }};

    @GetMapping
    public String index() {
        return "index";
    }
    @GetMapping("/list")
    public List<Product> list() {
        System.out.println(this.productList);
        return this.productList;
    }
}
