package ru.aksenov.webshop.objects;

import org.codehaus.jackson.JsonProcessingException;
import org.codehaus.jackson.map.ObjectMapper;

import java.io.IOException;
import java.text.MessageFormat;

public class Product {
    private String name;
    private int quantity;
    private String image;

    public Product(String name, int quantity) {
        this.name = name;
        this.quantity = quantity;
        this.image = "IMAGE TEST";
    }

    public String getName() {
        return this.name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public int getQuantity() {
        return this.quantity;
    }
    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
    public String getImage() {
        return image;
    }
    public void setImage(String image) {
        this.image = image;
    }


    @Override
    public String toString() {
        ObjectMapper om = new ObjectMapper();
        String json;
        try {
            json = om.writeValueAsString(this);
            System.out.println(json);
        }catch (IOException e) {
            return e.getMessage();
        }
        return json;
    }
}
