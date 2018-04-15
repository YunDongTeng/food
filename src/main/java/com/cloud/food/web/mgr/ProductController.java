package com.cloud.food.web.mgr;


import com.cloud.food.entity.ProductInfo;
import com.cloud.food.service.ProductInfoService;
import com.cloud.food.vo.ProductMangeVO;
import com.cloud.food.vo.ResultVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/seller/product")
public class ProductController {


    @Autowired
    private ProductInfoService productInfoService;

    @RequestMapping("/list")
    public ModelAndView list(@RequestParam(value = "page", defaultValue = "1") Integer page,
                             @RequestParam(value = "size", defaultValue = "5") Integer size) {
        PageRequest pageRequest = new PageRequest(page - 1, size);
        Page<ProductMangeVO> productInfoList = productInfoService.findAll(pageRequest);

        Map<String, Object> map = new HashMap<String, Object>();

        map.put("currentPage", page);

        map.put("productList", productInfoList);
        return new ModelAndView("admin/product/list", map);
    }

    @RequestMapping("/detail/{id}")
    public ModelAndView detail(@PathVariable("id") String id) {
        ProductInfo productInfo = productInfoService.getOne(id);

        Map<String, Object> map = new HashMap<String, Object>();
        map.put("productInfo", productInfo);
        return new ModelAndView("admin/product/detail", map);
    }

    @RequestMapping("/update")
    @ResponseBody
    public ResultVO update(ProductInfo productInfo){
        productInfoService.save(productInfo);
        return ResultVO.success();
    }

    @PostMapping("/updateState")
    @ResponseBody
    public ResultVO updateState(@RequestParam("state") Integer state, @RequestParam("id")String id) {
        productInfoService.updateProductState(state, id);
        return ResultVO.success();
    }
}
