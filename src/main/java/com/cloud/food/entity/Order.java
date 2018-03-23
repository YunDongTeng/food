package com.cloud.food.entity;


import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.Entity;
import java.math.BigDecimal;
import java.util.Date;

@Entity
@DynamicUpdate
public class Order {


    private String orderId;
    private String buyerName;
    private String buyerPhone;
    private String buyerAddress;
    private String buyerOpenid;
    private BigDecimal orderPrice;
    private Integer orderStatus;
    private Integer payStatus;

    private Date craeteTime2;


}
