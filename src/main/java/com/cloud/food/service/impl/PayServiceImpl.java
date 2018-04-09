package com.cloud.food.service.impl;


import com.cloud.food.dto.OrderDTO;
import com.cloud.food.service.PayService;
import com.cloud.food.weixin.PayConfig;
import com.github.wxpay.sdk.WXPay;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class PayServiceImpl implements PayService {


    @Override
    public OrderDTO pay(OrderDTO orderDTO) {

        //String payUrl = "https://api.mch.weixin.qq.com/pay/unifiedorder";

        PayConfig payConfig = new PayConfig();

        WXPay wxPay = new WXPay(payConfig);

        Map<String, String> payMap = new HashMap<String, String>();

        payMap.put("out_trade_no",orderDTO.getOrderId());
        payMap.put("body","微信订餐系统支付");
        payMap.put("fee_type","CNY");
        payMap.put("total_fee", String.valueOf(orderDTO.getOrderAmount()));
        payMap.put("spbill_create_ip","192.168.101.151");
        payMap.put("trade_type","JSAPI ");
        payMap.put("notify_url","http://haomai.com/pay/notify");

        try {
            Map<String, String> response = wxPay.unifiedOrder(payMap);

            System.out.println(response);

        } catch (Exception e) {
            e.printStackTrace();
        }

        return orderDTO;
    }
}
