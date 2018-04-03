package com.cloud.food.service.impl;

import com.cloud.food.constant.ExceptionEnum;
import com.cloud.food.constant.OrderStatusEnum;
import com.cloud.food.constant.PayStatusEnum;
import com.cloud.food.convert.Order2DTOConvert;
import com.cloud.food.dto.OrderDTO;
import com.cloud.food.dto.ShopCartDTO;
import com.cloud.food.entity.Order;
import com.cloud.food.entity.OrderDetail;
import com.cloud.food.entity.ProductInfo;
import com.cloud.food.exception.SellException;
import com.cloud.food.repository.OrderDetailRepository;
import com.cloud.food.repository.OrderRepository;
import com.cloud.food.repository.ProductInfoRepository;
import com.cloud.food.service.OrderService;
import com.cloud.food.service.ProductInfoService;
import com.cloud.food.util.UUIDUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;


@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private ProductInfoRepository productInfoRepository;

    @Autowired
    private OrderDetailRepository orderDetailRepository;


    @Autowired
    private ProductInfoService productInfoService;

    @Transactional
    @Override
    public Order save(OrderDTO order) {

        List<OrderDetail> detailList = order.getDetailList();


        String orderId = UUIDUtils.uuid();

        BigDecimal orderTotalFee = new BigDecimal(0);
        //查询价格
        for (OrderDetail detail : detailList) {
            ProductInfo productInfo = productInfoRepository.getOne(detail.getProductId());

            if (productInfo == null) {
                throw new SellException(ExceptionEnum.PRODUCT_NOT_EXIST);
            }

            //计算总价
            orderTotalFee = productInfo.getProductPrice().multiply(new BigDecimal(detail.getProductAmount())).add(orderTotalFee);

            detail.setOrderId(orderId);
            detail.setDetailId(UUIDUtils.uuid());
            detail.setProductName(productInfo.getProductName());
            detail.setProductPrice(productInfo.getProductPrice());
            detail.setProductImg(productInfo.getProductImg());
            //写入订单详情
            orderDetailRepository.save(detail);

        }

        //写入订单信息
        Order saveOrder = new Order();
        saveOrder.setOrderId(orderId);
        saveOrder.setOrderPrice(orderTotalFee);
        BeanUtils.copyProperties(order, saveOrder);
        saveOrder.setOrderStatus(OrderStatusEnum.NOT_PAY.getCode());
        saveOrder.setPayStatus(PayStatusEnum.NOT_PAY.getCode());
        orderRepository.save(saveOrder);

        //减库存

        List<ShopCartDTO> cartList = new ArrayList<>();
        detailList.stream().forEach(e -> {
            cartList.add(new ShopCartDTO(e.getProductId(), e.getProductAmount()));
        });
        productInfoService.decrStock(cartList);

        return saveOrder;
    }

    @Override
    public OrderDTO findOne(String id) {

        Order order = orderRepository.getOne(id);

        if (order == null) {
            throw new SellException(ExceptionEnum.ORDER_NOT_EXIST);
        }

        List<OrderDetail> detailList = orderDetailRepository.findByOrderId(order.getOrderId());
        if (detailList == null || detailList.size() == 0) {
            throw new SellException(ExceptionEnum.ORDERDETAIL_NOT_EXIST);
        }

        OrderDTO orderDTO = new OrderDTO();
        BeanUtils.copyProperties(order, orderDTO);
        orderDTO.setDetailList(detailList);

        return orderDTO;
    }

    @Override
    public Page<OrderDTO> findList(String buyerOpenId, Pageable pageable) {

        Page<Order> orderList = orderRepository.findByBuyerOpenid(buyerOpenId, pageable);
        List<OrderDTO> dtoList = Order2DTOConvert.convert(orderList.getContent());
        Page<OrderDTO> result = new PageImpl<>(dtoList,pageable,dtoList.size());

        return result;
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
