package com.cloud.food.web.mgr;


import com.cloud.food.entity.ProductCategory;
import com.cloud.food.service.ProductCategoryService;
import com.cloud.food.vo.ResultVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
@RequestMapping("/seller/category")
public class ProductCategoryController {


    @Autowired
    private ProductCategoryService productCategoryService;

    @RequestMapping("/findAll")
    @ResponseBody
    public ResultVO<List<ProductCategory>> findAll() {
        return ResultVO.success(productCategoryService.findAll());
    }


}
