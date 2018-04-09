package com.cloud.food.web;


import com.cloud.food.constant.ExceptionEnum;
import com.cloud.food.dto.OrderDTO;
import com.cloud.food.exception.SellException;
import com.cloud.food.service.OrderService;
import com.cloud.food.service.PayService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/pay")
public class PayController {

    private Logger logger = LoggerFactory.getLogger(PayController.class);

    @Autowired
    private OrderService orderService;

    @Autowired
    private PayService payService;

    @RequestMapping("/create")
    public String create(@RequestParam("orderId")String orderId,
                         @RequestParam("returnUrl")String returnUrl,
                         Model model){

        OrderDTO orderDTO = orderService.findOne(orderId);

        if(orderDTO==null){
            logger.error("【微信发起支付】订单不存在:orderId="+orderId);
            throw new SellException(ExceptionEnum.ORDER_NOT_EXIST);
        }

        payService.pay(orderDTO);

        model.addAttribute("","");

        return "/pay/pay.html";
    }


}
