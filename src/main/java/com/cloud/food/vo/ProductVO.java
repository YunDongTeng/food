package com.cloud.food.vo;

import com.cloud.food.entity.ProductInfo;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class ProductVO {


    @JsonProperty("name")
    private String categoryName;

    @JsonProperty("type")
    private String categoryType;

    @JsonProperty("foods")
    private List<ProductInfoVO> foods;


    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public String getCategoryType() {
        return categoryType;
    }

    public void setCategoryType(String categoryType) {
        this.categoryType = categoryType;
    }

    public List<ProductInfoVO> getFoods() {
        return foods;
    }

    public void setFoods(List<ProductInfoVO> foods) {
        this.foods = foods;
    }
}
