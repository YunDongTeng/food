package com.cloud.food.exception;


import com.cloud.food.constant.ExceptionEnum;
import com.cloud.food.vo.ResultVO;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

@ControllerAdvice
@ResponseBody
public class GlobalExceptionHandler {


    @ExceptionHandler(value = SellException.class)
    public ResultVO exceptionHandle(ExceptionEnum e) {
        return ResultVO.error(e.getCode(), e.getMsg());
    }

}
