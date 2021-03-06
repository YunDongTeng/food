package com.cloud.food.service;

import com.cloud.food.dto.ShopCartDTO;
import com.cloud.food.entity.ProductInfo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ProductInfoService {

    List<ProductInfo> findByProductStatus(Integer status);

    ProductInfo save(ProductInfo productInfo);

    Page<ProductInfo> findAll(Pageable pageable);

    ProductInfo getOne(String id);

    //查询所有上架的商品
    List<ProductInfo> findUpAll();


    //加库存
    void incrStock(List<ShopCartDTO> list);

    //减库存
    void decrStock(List<ShopCartDTO> list);

}
