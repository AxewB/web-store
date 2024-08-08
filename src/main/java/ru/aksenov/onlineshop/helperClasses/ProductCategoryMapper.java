package ru.aksenov.onlineshop.helperClasses;

import java.util.List;

public class ProductCategoryMapper {
    private Long productId;
    private List<Long> categoryIds;

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public List<Long> getCategoryIds() {
        return categoryIds;
    }

    public void setCategoryId(List<Long> categoryIds) {
        this.categoryIds = categoryIds;
    }
}
