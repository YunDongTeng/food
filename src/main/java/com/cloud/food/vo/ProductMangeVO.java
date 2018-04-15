package com.cloud.food.vo;

import com.cloud.food.entity.ProductInfo;

public class ProductMangeVO extends ProductInfo {

    private String categoryName;

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }
}
