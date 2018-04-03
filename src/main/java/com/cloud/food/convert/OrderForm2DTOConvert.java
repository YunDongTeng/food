package com.cloud.food.convert;

import com.cloud.food.dto.OrderDTO;
import com.cloud.food.entity.Order;
import com.cloud.food.entity.OrderDetail;
import com.cloud.food.form.OrderForm;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.List;

public class OrderForm2DTOConvert {

    public static OrderDTO convert(OrderForm orderForm) {

        OrderDTO orderDTO = new OrderDTO();

        orderDTO.setBuyerName(orderForm.getBuyerName());
        orderDTO.setBuyerPhone(orderForm.getBuyerPhone());
        orderDTO.setBuyerAddress(orderForm.getAddress());
        orderDTO.setBuyerOpenid(orderForm.getOpenid());

        List<OrderDetail> detailList = new Gson().fromJson(orderForm.getItems(), new TypeToken<List<OrderDetail>>() {}.getType());
        orderDTO.setDetailList(detailList);

        return orderDTO;
    }
}
