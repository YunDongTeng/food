package com.cloud.food.web;


import com.alibaba.fastjson.JSON;
import com.cloud.food.constant.ExceptionEnum;
import com.cloud.food.dto.OrderDTO;
import com.cloud.food.exception.SellException;
import com.cloud.food.service.OrderService;
import com.cloud.food.service.PayService;
import com.cloud.food.weixin.PayConfig;
import com.github.wxpay.sdk.WXPay;
import com.github.wxpay.sdk.WXPayUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;


/**
 * 微信支付相关处理类
 */
@Controller
@RequestMapping("/pay")
public class PayController {

    private Logger logger = LoggerFactory.getLogger(PayController.class);

    @Autowired
    private OrderService orderService;

    @Autowired
    private PayService payService;


    /**
     * 微信发起支付请求
     *
     * @param orderId
     * @param returnUrl
     * @param request
     * @return
     */
    @RequestMapping("/create")
    public ModelAndView create(@RequestParam("orderId") String orderId,
                               @RequestParam("returnUrl") String returnUrl,
                               HttpServletRequest request) {

        OrderDTO orderDTO = orderService.findOne(orderId);

        if (orderDTO == null) {
            logger.error("【微信发起支付】订单不存在:orderId=" + orderId);
            throw new SellException(ExceptionEnum.ORDER_NOT_EXIST);
        }

        Map<String, String> response = payService.pay(orderDTO, request);

        logger.info("【发起微信支付】返回结果：{}", JSON.toJSON(response));

        Map<String, Object> result = new HashMap<String, Object>();

        result.put("response", response);
        result.put("returnUrl", returnUrl);

        return new ModelAndView("/createPay", result);
    }

    /**
     * 微信支付异步回调
     * @param notifyData
     * @return
     */
    @GetMapping("/notify")
    public ModelAndView notify(@RequestParam("notifyData") String notifyData) {

        payService.notify(notifyData);
        return new ModelAndView("/notifySuccess");
    }

    /**
     * 微信退款
     * @param orderId
     * @return
     */
    @GetMapping("/refund")
    public Map<String,String> refund(@RequestParam("orderId")String orderId,
                                      @RequestParam("reason")String reason){
        return payService.refund(orderId,reason);
    }

    @GetMapping("/refundNotify")
    public ModelAndView refundNotify(@RequestParam("notifyData")String notifyData){
        payService.refundNotify(notifyData);
        return new ModelAndView("/notifyData");
    }


}
