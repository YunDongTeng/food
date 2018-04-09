package com.cloud.food.service;

import com.cloud.food.dto.OrderDTO;

public interface PayService {

    OrderDTO pay(OrderDTO orderDTO);
}
