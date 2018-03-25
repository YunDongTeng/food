package com.cloud.food.service.impl;

import com.cloud.food.dto.OrderDTO;
import com.cloud.food.entity.Order;
import com.cloud.food.service.OrderService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
public class OrderServiceImpl implements OrderService {


    @Transactional
    @Override
    public Order save(OrderDTO order) {
        return null;
    }

    @Override
    public Order findOne(String id) {
        return null;
    }

    @Override
    public Page<OrderDTO> findList(String buyerOpenId, Pageable pageable) {
        return null;
    }

    @Override
    public Order cancelOrder(Order order) {
        return null;
    }

    @Override
    public Order finishOrder(Order order) {
        return null;
    }

    @Override
    public Order payOrder(Order order) {
        return null;
    }
}
