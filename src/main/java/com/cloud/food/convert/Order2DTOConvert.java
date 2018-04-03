package com.cloud.food.convert;

import com.cloud.food.dto.OrderDTO;
import com.cloud.food.entity.Order;
import org.springframework.beans.BeanUtils;

import java.util.List;
import java.util.stream.Collectors;

public class Order2DTOConvert {

    public static OrderDTO convert(Order order) {

        OrderDTO orderDTO = new OrderDTO();

        BeanUtils.copyProperties(order, orderDTO);

        return orderDTO;
    }

    public static List<OrderDTO> convert(List<Order> orderList) {
        return orderList.stream().map(e -> {
            return convert(e);
        }).collect(Collectors.toList());
    }

    ;

}
