package com.cloud.food.web;


import com.cloud.food.constant.ExceptionEnum;
import com.cloud.food.convert.OrderForm2DTOConvert;
import com.cloud.food.dto.OrderDTO;
import com.cloud.food.entity.OrderMaster;
import com.cloud.food.exception.SellException;
import com.cloud.food.form.OrderForm;
import com.cloud.food.service.BuyerService;
import com.cloud.food.service.OrderService;
import com.cloud.food.util.LogUtils;
import com.cloud.food.vo.ResultVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@RestController
@RequestMapping("/buyer/order")
public class BuyerOrderController {

    private static Logger logger = LoggerFactory.getLogger(BuyerOrderController.class);

    @Autowired
    private OrderService orderService;

    @Autowired
    private BuyerService buyerService;

    //创建订单
    @PostMapping("/create")
    public ResultVO<Map<String, String>> create(@Valid OrderForm orderForm,
                                                BindingResult result) {
        if (result.hasErrors()) {
            logger.error("【创建订单】参数校验错误");
            throw new SellException(ExceptionEnum.FORM_VALID_ERROR.getCode(), result.getFieldError().getDefaultMessage());
        }

        OrderDTO orderDTO = OrderForm2DTOConvert.convert(orderForm);

        if (orderDTO.getDetailList() == null) {
            logger.error("【创建订单】订单详情为空");
            throw new SellException(ExceptionEnum.ORDERDETAIL_NOT_EXIST);
        }

        OrderMaster order = orderService.save(orderDTO);
        Map<String, String> resultMap = new HashMap<String, String>();
        resultMap.put("orderId", order.getOrderId());

        return ResultVO.success(resultMap);

    }

    //订单列表
    @GetMapping("/list")
    public ResultVO<List<OrderDTO>> list(@RequestParam("openid") String openid,
                                         @RequestParam(value = "page", defaultValue = "1") Integer page,
                                         @RequestParam(value = "size", defaultValue = "10") Integer size) {

        if (StringUtils.isEmpty(openid)) {
            logger.error("【订单查询】openid不能为空");
            throw new SellException(ExceptionEnum.OPENID_NOT_EMPTY);
        }

        PageRequest pageRequest = new PageRequest(page, size);

        Page<OrderDTO> pageList = orderService.findList(openid, pageRequest);

        return ResultVO.success(pageList.getContent());

    }

    //订单详情
    @GetMapping("/detail")
    public ResultVO<OrderDTO> detail(@RequestParam("openid") String openid,
                                     @RequestParam("orderId") String orderId) {

        if (StringUtils.isEmpty(openid)) {
            logger.error("【订单查询】openid不能为空");
            throw new SellException(ExceptionEnum.OPENID_NOT_EMPTY);
        }
        if (StringUtils.isEmpty(openid)) {
            logger.error("【订单查询】orderId不能为空");
            throw new SellException(ExceptionEnum.ORDERID_NOT_EMPTY);
        }

        OrderDTO orderDTO = buyerService.detail(openid, orderId);
        return ResultVO.success(orderDTO);
    }

    //取消订单
    @PostMapping("/cancel")
    public ResultVO cancel(@RequestParam("openid") String openid,
                           @RequestParam("orderId") String orderId) {

        if (StringUtils.isEmpty(openid)) {
            logger.error("【订单查询】openid不能为空");
            throw new SellException(ExceptionEnum.OPENID_NOT_EMPTY);
        }

        if (StringUtils.isEmpty(openid)) {
            logger.error("【订单查询】orderId不能为空");
            throw new SellException(ExceptionEnum.ORDERID_NOT_EMPTY);
        }

        OrderDTO orderDTO = buyerService.cancel(openid, orderId);

        if (orderDTO != null) {
            return ResultVO.success();
        }
        return ResultVO.fail();

    }

}
