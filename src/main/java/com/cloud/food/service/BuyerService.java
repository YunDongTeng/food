package com.cloud.food.service;

import com.cloud.food.dto.OrderDTO;

public interface BuyerService {


    OrderDTO cancel(String openid, String orderId);

    OrderDTO detail(String openid, String orderId);
}
