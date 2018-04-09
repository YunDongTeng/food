package com.cloud.food.dto;

public class ShopCartDTO {

    private String productId;

    private Integer productAmount;

    public ShopCartDTO() {
    }

    public ShopCartDTO(String productId, Integer productAmount) {
        this.productId = productId;
        this.productAmount = productAmount;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public Integer getProductAmount() {
        return productAmount;
    }

    public void setProductAmount(Integer productAmount) {
        this.productAmount = productAmount;
    }
}
