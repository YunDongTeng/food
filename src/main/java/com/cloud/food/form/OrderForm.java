package com.cloud.food.form;

import javax.validation.constraints.NotEmpty;

public class OrderForm {

    @NotEmpty(message = "买家姓名不能为空")
    private String buyerName;

    @NotEmpty(message = "买家手机号不能为空")
    private String buyerPhone;

    @NotEmpty(message = "收货地址不能为空")
    private String address;

    @NotEmpty(message = "微信openid不能为空")
    private String openid;

    private String items;

    public String getBuyerName() {
        return buyerName;
    }

    public void setBuyerName(String buyerName) {
        this.buyerName = buyerName;
    }

    public String getBuyerPhone() {
        return buyerPhone;
    }

    public void setBuyerPhone(String buyerPhone) {
        this.buyerPhone = buyerPhone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getOpenid() {
        return openid;
    }

    public void setOpenid(String openid) {
        this.openid = openid;
    }

    public String getItems() {
        return items;
    }

    public void setItems(String items) {
        this.items = items;
    }
}
