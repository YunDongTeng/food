package com.cloud.food.service;


import com.cloud.food.dto.OrderDTO;
import com.cloud.food.entity.Order;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface OrderService {

    /**创建订单**/
    Order save(OrderDTO order);

    /**查询单个订单**/
    Order findOne(String id);

    /**查询订单列表**/
    Page<OrderDTO> findList(String buyerOpenId, Pageable pageable);

    /**取消订单**/
   Order cancelOrder(Order order);

    /**完结订单**/
    Order finishOrder(Order order);

    /**支付订单**/
    Order payOrder(Order order);
}
