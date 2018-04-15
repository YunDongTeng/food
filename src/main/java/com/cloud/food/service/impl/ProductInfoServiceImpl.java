package com.cloud.food.service.impl;

import com.cloud.food.constant.ExceptionEnum;
import com.cloud.food.constant.ProductStatusEnum;
import com.cloud.food.dto.ShopCartDTO;
import com.cloud.food.entity.ProductCategory;
import com.cloud.food.entity.ProductInfo;
import com.cloud.food.exception.SellException;
import com.cloud.food.repository.ProductCategoryRepository;
import com.cloud.food.repository.ProductInfoRepository;
import com.cloud.food.service.ProductInfoService;
import com.cloud.food.util.UUIDUtils;
import com.cloud.food.vo.ProductMangeVO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

import static com.cloud.food.constant.ExceptionEnum.PRODUCT_STOCK_NOT_ENOUGH;

@Service
public class ProductInfoServiceImpl implements ProductInfoService {


    @Autowired
    private ProductInfoRepository repository;

    @Autowired
    private ProductCategoryRepository productCategoryRepository;

    @Override
    public List<ProductInfo> findByProductStatus(Integer status) {
        return repository.findByProductStatus(status);
    }

    @Override
    public ProductInfo save(ProductInfo productInfo) {
        productInfo.setProductId(UUIDUtils.uuid());
        return repository.save(productInfo);
    }

    @Override
    public Page<ProductMangeVO> findAll(Pageable pageable) {

        Page<ProductInfo> list = repository.findAll(pageable);


        List<ProductMangeVO> mangeVOList = new ArrayList<>();

        list.stream().forEach(e -> {
            ProductCategory productCategory = productCategoryRepository.findProductCategoryByCategoryType(e.getCategoryType());
            if (productCategory != null) {
                ProductMangeVO productMangeVO = new ProductMangeVO();
                mangeVOList.add(productMangeVO);

                BeanUtils.copyProperties(e, productMangeVO);

                productMangeVO.setCategoryName(productCategory.getCategoryName());
            }
        });

        Page<ProductMangeVO> resultList = new PageImpl<ProductMangeVO>(mangeVOList, pageable, list.getTotalElements());

        return resultList;
    }

    /**
     * 查询所有上架的商品
     *
     * @return
     */
    @Override
    public List<ProductInfo> findUpAll() {
        return repository.findByProductStatus(ProductStatusEnum.UP.getCode());
    }

    @Override
    public void decrStock(List<ShopCartDTO> list) {
        list.stream().forEach(e -> {
            ProductInfo productInfo = repository.getOne(e.getProductId());
            if (productInfo == null) {
                throw new SellException(ExceptionEnum.PRODUCT_NOT_EXIST);
            }
            Integer result = productInfo.getProductStock() - e.getProductAmount();
            if (result < 0) {
                throw new SellException(PRODUCT_STOCK_NOT_ENOUGH);
            }
            productInfo.setProductStock(result);
            repository.save(productInfo);
        });
    }

    @Override
    public void incrStock(List<ShopCartDTO> list) {
        list.stream().forEach(e -> {
            ProductInfo productInfo = repository.getOne(e.getProductId());
            if (productInfo == null) {
                throw new SellException(ExceptionEnum.PRODUCT_NOT_EXIST);
            }
            Integer result = productInfo.getProductStock() + e.getProductAmount();

            productInfo.setProductStock(result);
            repository.save(productInfo);
        });
    }

    @Override
    public ProductInfo getOne(String id) {
        return repository.getOne(id);
    }


    @Override
    public void updateProductState(int state, String id) {

        ProductInfo productInfo = repository.getOne(id);

        if(productInfo == null){
            throw new SellException(ExceptionEnum.PRODUCT_NOT_EXIST);
        }

        if (state == 0) {
            productInfo.setProductStatus(ProductStatusEnum.DOWN.getCode());
        } else {
            productInfo.setProductStatus(ProductStatusEnum.UP.getCode());
        }

        ProductInfo result = repository.save(productInfo);

        if(result == null){
            throw new SellException(ExceptionEnum.PRODUCT_UPDATE_STATUS_FAIL);
        }

    }
}
