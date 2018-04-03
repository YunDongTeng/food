package com.cloud.food.constant;

public enum ExceptionEnum {


    PRODUCT_NOT_EXIST(401, "商品不存在"),

    PRODUCT_STOCK_NOT_ENOUGH(402, "商品库存不足"),

    ORDER_NOT_EXIST(404, "订单不存在"),

    ORDERDETAIL_NOT_EXIST(405, "订单详情不存在");

    int code;
    String msg;

    ExceptionEnum(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
