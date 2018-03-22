package com.cloud.food.repository;

import com.cloud.food.entity.ProductCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductCategoryRepository extends JpaRepository<ProductCategory,String> {

    List<ProductCategory> findProductCategoryByCategoryTypeIn(List<String> typeList);

}
