package ru.aksenov.webshop.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.aksenov.webshop.objects.Product;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/product")
public class ProductController {
    private List<Product> productList = new ArrayList<Product>() {{
        add(new Product("123", 123));
        add(new Product("3", 12));
        add(new Product("12", 2));
    }};
//    private List<Map<String, String>> productList = new ArrayList<Map<String, String>>() {{
//        add(new HashMap<String, String>() {{ put("id", "1"); put("text", (new Product("p1", 123)).toString()); }});
//        add(new HashMap<String, String>() {{ put("id", "2"); put("text", (new Product("p2", 321)).toString()); }});
//        add(new HashMap<String, String>() {{ put("id", "3"); put("text", (new Product("p3", 56)).toString()); }});
//    }};

    @GetMapping
    public String index() {
        return "index";
    }
    @GetMapping("/list")
    public List<Product> list() {
//        this.productList.add(new Product("123", 123));
//        this.productList.add(new Product("fgjke", 654));
//        this.productList.add(new Product("fgjh", 798));
        System.out.println(this.productList);
        return this.productList;
    }
}
