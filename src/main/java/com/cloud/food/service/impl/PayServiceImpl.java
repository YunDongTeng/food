package com.cloud.food.service.impl;


import com.cloud.food.constant.ExceptionEnum;
import com.cloud.food.dto.OrderDTO;
import com.cloud.food.exception.SellException;
import com.cloud.food.service.OrderService;
import com.cloud.food.service.PayService;
import com.cloud.food.util.UUIDUtils;
import com.cloud.food.weixin.MathUtil;
import com.cloud.food.weixin.PayConfig;
import com.github.wxpay.sdk.WXPay;
import com.github.wxpay.sdk.WXPayUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

@Service
public class PayServiceImpl implements PayService {

    private Logger logger = LoggerFactory.getLogger(PayServiceImpl.class);

    @Autowired
    private PayConfig payConfig;

    @Autowired
    private OrderService orderService;

    //微信发起支付的方法
    @Override
    public Map<String, String> pay(OrderDTO orderDTO, HttpServletRequest request) {
        //  PayConfig payConfig = new PayConfig();

        WXPay wxPay = new WXPay(payConfig);

        Map<String, String> payMap = new HashMap<String, String>();
        payMap.put("out_trade_no", orderDTO.getOrderId());
        payMap.put("body", "微信订餐系统支付");
        payMap.put("fee_type", "CNY");
        payMap.put("total_fee", String.valueOf(orderDTO.getOrderAmount()));
        payMap.put("spbill_create_ip", request.getRequestURI());
        payMap.put("trade_type", "JSAPI ");
        payMap.put("notify_url", payConfig.getPayNotifyUrl());


        Map<String, String> response = null;
        try {
            response = wxPay.unifiedOrder(payMap);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return response;
    }

    //微信支付异步回调处理
    @Override
    public void notify(String notifyData) {
        WXPay wXpay = new WXPay(payConfig);
        try {
            Map<String, String> notifyMap = WXPayUtil.xmlToMap(notifyData);
            //验证签名
            if (!wXpay.isPayResultNotifySignatureValid(notifyMap)) {
                logger.error("【微信支付回调】签名验证失败");
            }
            //验证支付状态

            //查询订单
            String orderId = notifyMap.get("out_trade_no");
            OrderDTO orderDTO = orderService.findOne(orderId);
            if (orderDTO == null) {
                throw new SellException(ExceptionEnum.ORDER_NOT_EXIST);
            }

            //验证金额
            Double orderFee = Double.parseDouble(notifyMap.get("total_fee"));
            if (!MathUtil.compare(orderFee, orderDTO.getOrderAmount().doubleValue())) {
                throw new SellException(ExceptionEnum.ORDERFEE_NOT_EQ_PAY);
            }

            //修改订单状态
            orderService.payOrder(orderDTO);

        } catch (Exception e) {
            logger.error("【微信支付回调】支付回调处理异常");
            e.printStackTrace();
        }
    }

    @Override
    public Map<String, String> refund(String orderId, String reason) {

        WXPay wXpay = new WXPay(payConfig);

        OrderDTO orderDTO = orderService.findOne(orderId);
        if (orderDTO == null) {
            throw new SellException(ExceptionEnum.ORDER_NOT_EXIST);
        }

        Map<String, String> refundMap = new HashMap<String, String>();

        //订单号
        refundMap.put("out_trade_no", orderDTO.getOrderId());

        //退单号
        refundMap.put("out_refund_no", "refundNO-" + UUIDUtils.uuid());
        refundMap.put("total_fee", orderDTO.getOrderAmount().toString());
        refundMap.put("refund_fee", orderDTO.getOrderAmount().toString());

        refundMap.put("refund_fee_type", "CNY");

        refundMap.put("refund_desc", reason);

        //退款异步回调地址
        refundMap.put("notify_url", payConfig.getRefundNotifyUrl());


        Map<String, String> resultMap = null;
        try {
            resultMap = wXpay.refund(refundMap);
        } catch (Exception e) {
            e.printStackTrace();
        }


        return resultMap;
    }

    @Override
    public void refundNotify(String notifyData) {
        WXPay wXpay = new WXPay(payConfig);
        Map<String, String> notifyMap = null;
        try {
            notifyMap = WXPayUtil.xmlToMap(notifyData);
            //验证签名
            if (!wXpay.isPayResultNotifySignatureValid(notifyMap)) {
                logger.error("【微信支付回调】签名验证失败");
            }

            //查询订单
            String orderId = notifyMap.get("out_trade_no");
            OrderDTO orderDTO = orderService.findOne(orderId);
            if (orderDTO == null) {
                throw new SellException(ExceptionEnum.ORDER_NOT_EXIST);
            }

            //验证金额
            Double orderFee = Double.parseDouble(notifyMap.get("total_fee"));
            if (!MathUtil.compare(orderFee, orderDTO.getOrderAmount().doubleValue())) {
                throw new SellException(ExceptionEnum.ORDERFEE_NOT_EQ_PAY);
            }

            //取消订单
            orderService.cancelOrder(orderDTO);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
