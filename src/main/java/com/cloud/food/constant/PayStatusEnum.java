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

    public static PayStatusEnum getPayStatusEnum(Integer code){
        for(PayStatusEnum payStatusEnum:PayStatusEnum.values()){
            if(payStatusEnum.getCode() == code){
                return payStatusEnum;
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
