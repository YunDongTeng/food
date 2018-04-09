package com.cloud.food.exception;


import com.cloud.food.constant.ExceptionEnum;
import com.cloud.food.util.LogUtils;
import com.cloud.food.vo.ResultVO;
import org.slf4j.Logger;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

@ControllerAdvice
@ResponseBody
public class GlobalExceptionHandler {

    private Logger logger = LogUtils.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(value = SellException.class)
    public ResultVO exceptionHandle(SellException e) {

        logger.error(e.getMessage());
        e.printStackTrace();

        return ResultVO.error(e.getCode(),e.getMessage());
    }

}
