package com.cloud.food.vo;

import com.fasterxml.jackson.annotation.JsonInclude;

import javax.persistence.Entity;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class ResultVO<T> {


    /**
     * 错误码
     */
    private Integer code;

    /**
     * 提示信息
     */
    private String msg;

    /**
     * 数据
     */
    private T data;

    public static <T> ResultVO<T> success(T data) {
        return new ResultVO<T>(200, "返回成功", data);
    }

    public static ResultVO success() {
        return new ResultVO(200, "操作成功");
    }

    public static ResultVO fail(){
        return new ResultVO(500,"操作失败");
    }

    public static <T> ResultVO<T> error(Integer code, String msg) {
        return new ResultVO<T>(code, msg);
    }

    public ResultVO(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public ResultVO(Integer code, String msg, T data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
    }

    public ResultVO() {
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
