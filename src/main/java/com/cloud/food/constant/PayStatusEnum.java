package com.cloud.food.constant;

public enum PayStatusEnum {

    NOT_PAY(1,"未支付"),
    HAS_PAY(2,"已支付");

    private int code;
    private String msg;

    PayStatusEnum(int code, String msg) {
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
