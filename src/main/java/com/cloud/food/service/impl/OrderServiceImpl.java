package com.cloud.food.service.impl;

import com.cloud.food.constant.ExceptionEnum;
import com.cloud.food.constant.OrderStatusEnum;
import com.cloud.food.constant.PayStatusEnum;
import com.cloud.food.convert.Order2DTOConvert;
import com.cloud.food.dto.OrderDTO;
import com.cloud.food.dto.ShopCartDTO;
import com.cloud.food.entity.OrderDetail;
import com.cloud.food.entity.OrderMaster;
import com.cloud.food.entity.ProductInfo;
import com.cloud.food.exception.SellException;
import com.cloud.food.repository.OrderDetailRepository;
import com.cloud.food.repository.OrderRepository;
import com.cloud.food.repository.ProductInfoRepository;
import com.cloud.food.service.OrderService;
import com.cloud.food.service.PayService;
import com.cloud.food.service.ProductInfoService;
import com.cloud.food.util.UUIDUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;


@Service
public class OrderServiceImpl implements OrderService {

    private static final Logger logger = LoggerFactory.getLogger(OrderServiceImpl.class);

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private ProductInfoRepository productInfoRepository;

    @Autowired
    private OrderDetailRepository orderDetailRepository;

    @Autowired
    private ProductInfoService productInfoService;

    @Autowired
    private PayService payService;

    @Transactional
    @Override
    public OrderMaster save(OrderDTO orderDTO) {

        List<OrderDetail> detailList = orderDTO.getDetailList();
        String orderId = UUIDUtils.uuid();

        orderDTO.setOrderId(orderId);

        BigDecimal orderTotalFee = new BigDecimal(0);
        //查询价格
        for (OrderDetail detail : detailList) {
            ProductInfo productInfo = productInfoRepository.getProductInfoByProductId(detail.getProductId());

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
        OrderMaster saveOrder = new OrderMaster();
        BeanUtils.copyProperties(orderDTO, saveOrder);
        saveOrder.setOrderId(orderId);
        saveOrder.setOrderAmount(orderTotalFee);

        saveOrder.setOrderStatus(OrderStatusEnum.NOT_PAY.getCode());
        saveOrder.setPayStatus(PayStatusEnum.NOT_PAY.getCode());
        Date now = new Date();
        saveOrder.setCreateTime(now);
        saveOrder.setUpdateTime(now);

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

        OrderMaster order = orderRepository.getOne(id);

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

        Page<OrderMaster> orderList = orderRepository.findByBuyerOpenid(buyerOpenId, pageable);
        List<OrderDTO> dtoList = Order2DTOConvert.convert(orderList.getContent());
        Page<OrderDTO> result = new PageImpl<>(dtoList, pageable, dtoList.size());

        return result;
    }

    @Transactional
    @Override
    public OrderMaster cancelOrder(OrderDTO orderDTO) {

        if (orderDTO == null) {
            logger.error("【订单取消】:订单不存在");
            throw new SellException(ExceptionEnum.ORDER_NOT_EXIST);
        }
        //判断订单状态
        OrderMaster existOrder = orderRepository.getOne(orderDTO.getOrderId());
        if (existOrder == null) {
            logger.error("【订单取消】订单不存在：" + orderDTO.getOrderId());
            throw new SellException(ExceptionEnum.ORDER_NOT_EXIST);
        }
        if (existOrder.getOrderStatus().equals(OrderStatusEnum.HAS_CANCEL)
                || existOrder.getOrderStatus().equals(OrderStatusEnum.NOT_EVAL)
                || existOrder.getOrderStatus().equals(OrderStatusEnum.HAS_FINISH)) {
            throw new SellException(ExceptionEnum.ORDER_NOT_CANCEL);
        }

        //修改状态
        existOrder.setOrderStatus(OrderStatusEnum.HAS_CANCEL.getCode());
        OrderMaster result = orderRepository.save(existOrder);
        if (result == null) {
            logger.error("【订单取消】订单修改状态失败:" + existOrder.getOrderId());
            throw new SellException(ExceptionEnum.ORDER_CANCEL_FAIL);
        }


        //返回库存
        List<OrderDetail> detailList = orderDetailRepository.findByOrderId(orderDTO.getOrderId());
        if (CollectionUtils.isEmpty(detailList)) {
            logger.error("【订单取消】该订单没有订单详情:" + existOrder.getOrderId());
            throw new SellException(ExceptionEnum.ORDER_NO_DETAIL);
        }
        List<ShopCartDTO> cartList = new ArrayList<>();
        detailList.stream().forEach(e -> {
            cartList.add(new ShopCartDTO(e.getProductId(), e.getProductAmount()));
        });
        productInfoService.incrStock(cartList);

        //如果已经支付，退款
        if (existOrder.getOrderStatus().equals(OrderStatusEnum.HAS_PAY) && existOrder.getPayStatus().equals(PayStatusEnum.HAS_PAY)) {
            payService.refund(existOrder.getOrderId(), "");
        }

        return result;
    }

    @Override
    public OrderMaster finishOrder(OrderDTO orderDTO) {

        if (orderDTO == null) {
            logger.error("【订单完成】:订单不存在");
            throw new SellException(ExceptionEnum.ORDER_NOT_EXIST);
        }

        OrderMaster existOrder = orderRepository.getOne(orderDTO.getOrderId());
        //判断订单状态
        if (existOrder.getOrderStatus().equals(OrderStatusEnum.HAS_FINISH)
                || existOrder.getOrderStatus().equals(OrderStatusEnum.HAS_CANCEL)
                || existOrder.getOrderStatus().equals(OrderStatusEnum.NOT_PAY)) {
            logger.error("【订单完成】:订单状态错误，修改失败");

            throw new SellException(ExceptionEnum.ORDER_NOT_FINISH);
        }

        //修改订单状态
        existOrder.setOrderStatus(OrderStatusEnum.HAS_FINISH.getCode());
        OrderMaster result = orderRepository.save(existOrder);
        if (result == null) {
            logger.error("【订单完成】订单修改状态失败:" + existOrder.getOrderId());
            throw new SellException(ExceptionEnum.ORDER_FINISH_FAIL);
        }

        return result;
    }

    //修改订单支付状态
    @Override
    public OrderMaster payOrder(OrderDTO orderDTO) {

        if (orderDTO == null) {
            logger.error("【修改订单支付状态】:订单不存在");
            throw new SellException(ExceptionEnum.ORDER_NOT_EXIST);
        }

        OrderMaster existOrder = orderRepository.getOne(orderDTO.getOrderId());
        if (existOrder == null) {
            logger.error("【修改订单支付状态】:订单不存在");
            throw new SellException(ExceptionEnum.ORDER_NOT_EXIST);
        }
        //判断订单状态
        if (!existOrder.getOrderStatus().equals(OrderStatusEnum.NOT_PAY)) {
            logger.error("【修改订单支付状态】:订单状态错误，不可修改支付:" + existOrder.getOrderId());
            throw new SellException(ExceptionEnum.ORDER_NOT_PAY);
        }
        //判断支付状态
        if (existOrder.getPayStatus().equals(PayStatusEnum.HAS_PAY)) {
            logger.error("【修改订单支付状态】:订单已经支付:" + existOrder.getOrderId());
            throw new SellException(ExceptionEnum.ORDER_HAS_PAY);
        }
        //修改订单状态
        existOrder.setOrderStatus(OrderStatusEnum.HAS_PAY.getCode());

        OrderMaster result = orderRepository.save(existOrder);

        if (orderDTO == null) {
            logger.error("【修改订单支付状态】:订单修改支付状态失败:" + existOrder.getOrderId());
            throw new SellException(ExceptionEnum.ORDER_PAYSTATUS_FAIL);
        }

        return result;
    }

    @Override
    public Page<OrderDTO> findList(Pageable pageable) {
        Page<OrderMaster> orderList = orderRepository.findAll(pageable);

        List<OrderDTO> dtoList = Order2DTOConvert.convert(orderList.getContent());

        dtoList.stream().forEach(e -> {
            e.setOrderStatusEnum(OrderStatusEnum.getOrderStatuEnum(e.getOrderStatus()));
            e.setPayStatusEnum(PayStatusEnum.getPayStatusEnum(e.getPayStatus()));
        });

        Page<OrderDTO> result = new PageImpl<>(dtoList, pageable, orderList.getTotalElements()
        );

        return result;
    }
}
