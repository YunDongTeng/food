package com.cloud.food.web.mgr;


import com.cloud.food.constant.ExceptionEnum;
import com.cloud.food.dto.OrderDTO;
import com.cloud.food.exception.SellException;
import com.cloud.food.service.OrderService;
import com.cloud.food.vo.ResultVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("/seller/order")
public class OrderController {

    @Autowired
    public OrderService orderService;

    /**
     * 订单列表
     * @param page
     * @param size
     * @return
     */
    @GetMapping("/list")
    public ModelAndView findList(@RequestParam(value = "page", defaultValue = "1") Integer page,
                                 @RequestParam(value = "size", defaultValue = "5") Integer size) {
        PageRequest pageRequest = new PageRequest(page - 1, size);

        Page<OrderDTO> pageList = orderService.findList(pageRequest);

        Map<String, Object> map = new HashMap<String, Object>();
        map.put("orderPageList", pageList);
        map.put("currentPage", page);

        return new ModelAndView("admin/order/list", map);
    }

    /**
     * 订单详情
     * @param id
     * @return
     */
    @GetMapping("/detail/{id}")
    public ModelAndView detail(@PathVariable("id")String id){

        OrderDTO orderDTO = orderService.findOne(id);

        Map<String,Object> map = new HashMap<String,Object>();
        map.put("order",orderDTO);

        return new ModelAndView("admin/order/detail",map);
    }

    /**
     * 取消订单
     * @param orderId
     * @return
     */
    @PostMapping("/cancel")
    @ResponseBody
    public ResultVO cancel(@RequestParam("orderId") String orderId) {

        OrderDTO orderDTO = orderService.findOne(orderId);

        if(orderDTO==null){
            throw new SellException(ExceptionEnum.ORDER_NOT_EXIST);
        }
        orderService.cancelOrder(orderDTO);

        return ResultVO.success();
    }

}
