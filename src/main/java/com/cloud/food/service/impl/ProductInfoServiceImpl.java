package com.cloud.food.service.impl;

import com.cloud.food.constant.ProductStatusEnum;
import com.cloud.food.entity.ProductInfo;
import com.cloud.food.repository.ProductInfoRepository;
import com.cloud.food.service.ProductInfoService;
import com.cloud.food.util.UUIDUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductInfoServiceImpl implements ProductInfoService {


    @Autowired
    private ProductInfoRepository repository;

    @Override
    public List<ProductInfo> findByProductStatus(Integer status) {
        return repository.findByProductStatus(status);
    }

    @Override
    public ProductInfo save(ProductInfo productInfo) {

        productInfo.setProductId(UUIDUtils.uuid());

        //默认上架
        productInfo.setProductStatus(ProductStatusEnum.UP.getCode());

        return repository.save(productInfo);
    }

    @Override
    public Page<ProductInfo> findAll(Pageable pageable) {
        return repository.findAll(pageable);
    }

    /**
     * 查询所有上架的商品
     * @return
     */
    @Override
    public List<ProductInfo> findUpAll() {
        return repository.findByProductStatus(ProductStatusEnum.UP.getCode());
    }
}
