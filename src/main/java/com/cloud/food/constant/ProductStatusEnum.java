package com.cloud.food.constant;

public enum  ProductStatusEnum {

    UP(0,"上架"),
    DOWN(1,"下架");

    private Integer code;
    private String msg;

   ProductStatusEnum(Integer code,String msg){
        this.code = code;
        this.msg = msg;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }
}
