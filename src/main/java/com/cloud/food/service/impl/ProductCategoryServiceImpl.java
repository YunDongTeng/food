package com.cloud.food.service.impl;

import com.cloud.food.entity.ProductCategory;
import com.cloud.food.repository.ProductCategoryRepository;
import com.cloud.food.service.ProductCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductCategoryServiceImpl implements ProductCategoryService {

    @Autowired
    private ProductCategoryRepository repository;

    @Override
    public ProductCategory findOne(String categoryId) {
        return repository.getOne(categoryId);
    }

    @Override
    public List<ProductCategory> findAll() {
        return repository.findAll();
    }

    @Override
    public ProductCategory save(ProductCategory productCategory) {
        return repository.save(productCategory);
    }

    @Override
    public List<ProductCategory> findProductCategoryTypeIn(List<String> typeList) {
        return repository.findProductCategoryByCategoryTypeIn(typeList);
    }
}
