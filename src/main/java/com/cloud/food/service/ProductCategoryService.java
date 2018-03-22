package com.cloud.food.service;

import com.cloud.food.entity.ProductCategory;

import java.util.List;

public interface ProductCategoryService {

    ProductCategory findOne(String categoryId);

    List<ProductCategory> findAll();

    ProductCategory save(ProductCategory productCategory);

    List<ProductCategory> findProductCategoryTypeIn(List<String> typeList);
}
