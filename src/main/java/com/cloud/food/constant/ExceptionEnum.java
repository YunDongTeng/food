package com.cloud.food.constant;

public enum ExceptionEnum {


    FORM_VALID_ERROR(420, "表单验证失败"),

    PRODUCT_NOT_EXIST(401, "商品不存在"),

    PRODUCT_STOCK_NOT_ENOUGH(402, "商品库存不足"),

    ORDER_NOT_EXIST(404, "订单不存在"),

    ORDERDETAIL_NOT_EXIST(405, "订单详情不存在"),

    ORDER_NOT_CANCEL(406, "订单状态不可取消"),

    ORDER_CANCEL_FAIL(407, "订单取消失败"),
    ORDER_FINISH_FAIL(410, "订单取消失败"),
    ORDER_PAYSTATUS_FAIL(413, "订单修改支付状态失败"),
    ORDER_NO_DETAIL(408, "订单没有详情"),

    ORDER_NOT_FINISH(409, "订单不可完结"),
    ORDER_NOT_PAY(411, "订单状态不可支付"),
    ORDER_HAS_PAY(412, "订单已经支付"),
    OPENID_NOT_EMPTY(414, "openid不能为空"),
    ORDERID_NOT_EMPTY(415, "orderId不能为空"),
    ORDER_NOT_OWN(416, "当前订单不属于该用户"),
    ORDERFEE_NOT_EQ_PAY(417, "订单金额与支付金额不一致"),
    PRODUCT_UPDATE_STATUS_FAIL(418,"更新上下架状态失败");

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
