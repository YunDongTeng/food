package com.cloud.food.service;


import com.cloud.food.dto.OrderDTO;
import com.cloud.food.entity.OrderMaster;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface OrderService {

    /**创建订单**/
    OrderMaster save(OrderDTO order);

    /**查询单个订单**/
    OrderDTO findOne(String id);

    /**查询订单列表**/
    Page<OrderDTO> findList(String buyerOpenId, Pageable pageable);

    /**取消订单**/
    OrderMaster cancelOrder(OrderMaster order);

    /**完结订单**/
    OrderMaster finishOrder(OrderMaster order);

    /**支付订单**/
    OrderMaster payOrder(OrderMaster order);
}
