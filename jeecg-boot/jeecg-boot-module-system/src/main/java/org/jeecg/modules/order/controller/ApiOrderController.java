package org.jeecg.modules.order.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.jeecg.common.api.vo.Result;
import org.jeecg.modules.order.entity.ApiOrderVo;
import org.jeecg.modules.order.entity.ApiQueryOrderVo;
import org.jeecg.modules.order.entity.ApiUpdateOrderVo;
import org.jeecg.modules.order.entity.OrderDto;
import org.jeecg.modules.order.service.IOrderService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@Api(tags="api")
@RestController
@RequestMapping("/api/order")
@Slf4j
public class ApiOrderController {

    @Resource
    private IOrderService orderService;

    /**
     * 创建订单
     */
    @PostMapping("createOrder")
    @ApiOperation("创建订单")
    public Result<?> create(@RequestBody ApiOrderVo orderVo){
        boolean result = orderService.createOrder(orderVo);
        if (result) {
            return Result.OK("订单创建成功！");
        }
        return Result.error(500, "订单创建失败");
    }

    /**
     * 创建订单
     */
    @PostMapping("queryOrderList")
    @ApiOperation("查询订单列表")
    public Result<?> queryOrder(@RequestBody ApiQueryOrderVo queryOrderVo){
        IPage<OrderDto> result = orderService.queryOrderList(queryOrderVo);
        return Result.OK(result);
    }

    /**
     * 创建订单
     */
    @PostMapping("updateOrder")
    @ApiOperation("创建订单")
    public Result<?> update(@RequestBody ApiUpdateOrderVo updateOrderVo){
        boolean result = orderService.updateOrder(updateOrderVo);
        if (result) {
            return Result.OK("订单创建成功！");
        }
        return Result.error(500, "订单创建失败");
    }

}