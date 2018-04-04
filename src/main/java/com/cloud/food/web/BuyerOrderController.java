package com.cloud.food.web;


import com.cloud.food.constant.ExceptionEnum;
import com.cloud.food.convert.OrderForm2DTOConvert;
import com.cloud.food.dto.OrderDTO;
import com.cloud.food.entity.OrderMaster;
import com.cloud.food.exception.SellException;
import com.cloud.food.form.OrderForm;
import com.cloud.food.service.OrderService;
import com.cloud.food.vo.ResultVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;


@RestController
@RequestMapping("/buyer/order")
public class BuyerOrderController {

    private static Logger logger = LoggerFactory.getLogger(BuyerOrderController.class);

    @Autowired
    private OrderService orderService;

    //创建订单
    @PostMapping("/create")
    public ResultVO<Map<String, String>> create(@Valid OrderForm orderForm,
                                                BindingResult result) {
        if (result.hasErrors()) {
            logger.error("【创建订单】参数校验错误");
            throw new SellException(ExceptionEnum.FORM_VALID_ERROR.getCode(), result.getFieldError().getDefaultMessage());
        }

        OrderDTO orderDTO = OrderForm2DTOConvert.convert(orderForm);

        if (orderDTO.getDetailList() == null) {
            logger.error("【创建订单】订单详情为空");
            throw new SellException(ExceptionEnum.ORDERDETAIL_NOT_EXIST);
        }

        OrderMaster order = orderService.save(orderDTO);
        Map<String, String> resultMap = new HashMap<String, String>();
        resultMap.put("orderId", order.getOrderId());

        return ResultVO.success(resultMap);

    }

    //订单列表

    //订单详情

    //取消订单


}
