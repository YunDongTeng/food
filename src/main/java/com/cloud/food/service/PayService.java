package com.cloud.food.service;

import com.cloud.food.dto.OrderDTO;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

public interface PayService {

    Map<String,String> pay(OrderDTO orderDTO, HttpServletRequest request);

    void notify(String notifyData);

    Map<String,String>  refund(String orderId,String reason);

    void refundNotify(String notifyData);
}
