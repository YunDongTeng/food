package com.cloud.food.service.impl;

import com.cloud.food.constant.ExceptionEnum;
import com.cloud.food.dto.OrderDTO;
import com.cloud.food.entity.OrderMaster;
import com.cloud.food.exception.SellException;
import com.cloud.food.service.BuyerService;
import com.cloud.food.service.OrderService;
import com.cloud.food.util.LogUtils;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class BuyerServiceImpl implements BuyerService {

    private Logger logger = LogUtils.getLogger(BuyerServiceImpl.class);

    @Autowired
    private OrderService orderService;

    @Override
    public OrderDTO cancel(String openid, String orderId) {

        OrderDTO orderDTO = checkOwn(openid,orderId);

        orderService.cancelOrder(orderDTO);

        return orderDTO;
    }

    @Override
    public OrderDTO detail(String openid, String orderId) {
        return checkOwn(openid,orderId);
    }

    public OrderDTO checkOwn(String openid, String orderId) {
        OrderDTO orderDTO = orderService.findOne(orderId);
        if (orderDTO == null) {

            logger.error("【订单检查】订单不存在");
            throw new SellException(ExceptionEnum.ORDER_NOT_EXIST);
        }

        if (!orderDTO.getBuyerOpenid().equalsIgnoreCase(openid)) {
            logger.error("【订单检查】openid与orderId不一致");
            throw new SellException(ExceptionEnum.ORDER_NOT_OWN);
        }

        return orderDTO;
    }
}
