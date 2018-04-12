package com.cloud.food.constant;

public enum OrderStatusEnum {

    NOT_PAY(1, "待付款"),
    HAS_PAY(2, "已付款"),
    NOT_EVAL(3, "待评价"),
    HAS_FINISH(4, "已完成"),
    HAS_CANCEL(5, "已取消");

    private int code;
    private String msg;

    OrderStatusEnum(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public static OrderStatusEnum getOrderStatuEnum(Integer code) {

        for (OrderStatusEnum orderStatusEnum : OrderStatusEnum.values()) {
            if (orderStatusEnum.getCode() == code) {
                return orderStatusEnum;
            }
        }
        return null;
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
