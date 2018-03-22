package com.cloud.food.web;


import com.cloud.food.entity.ProductCategory;
import com.cloud.food.entity.ProductInfo;
import com.cloud.food.service.ProductCategoryService;
import com.cloud.food.service.ProductInfoService;
import com.cloud.food.vo.ProductInfoVO;
import com.cloud.food.vo.ProductVO;
import com.cloud.food.vo.ResultVO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/buyer")
public class BuyerProductController {

    @Autowired
    private ProductInfoService productInfoService;

    @Autowired
    private ProductCategoryService productCategoryService;

    @GetMapping("/list")
    public ResultVO list() {

        //1.获取所有上架的商品
        List<ProductInfo> productInfoList = productInfoService.findUpAll();

        List<String> typeList = productInfoList.stream().map(e -> e.getCategoryType()).collect(Collectors.toList());

        //2.获取到所有的类目
        List<ProductCategory> productCategoryList = productCategoryService.findProductCategoryTypeIn(typeList);

        //3.封装数据
        List<ProductVO> productVOList = new ArrayList<>();
        for (ProductCategory productCategory : productCategoryList) {
            ProductVO productVO = new ProductVO();
            productVO.setCategoryName(productCategory.getCategoryName());
            productVO.setCategoryType(productCategory.getCategoryType());

            List<ProductInfoVO> tempProductInfoVOList = new ArrayList<>();

            productInfoList.stream().forEach(productInfo -> {
                ProductInfoVO productInfoVO = null;
                if (productInfo.getCategoryType().equals(productCategory.getCategoryType())) {
                    productInfoVO = new ProductInfoVO();
                  //  BeanUtils.copyProperties(productInfo, productInfoVO);
                    productInfoVO.setProductName(productInfo.getProductName());
                    productInfoVO.setProductImg(productInfo.getProductImg());
                    productInfoVO.setProductPrice(productInfo.getProductPrice());
                    productInfoVO.setProductDesc(productInfo.getProductDesc());
                    productInfoVO.setProductType(productInfo.getCategoryType());
                    tempProductInfoVOList.add(productInfoVO);
                }

            });
            productVO.setFoods(tempProductInfoVOList);
            productVOList.add(productVO);
        }
        return ResultVO.success(productVOList);
    }

}
