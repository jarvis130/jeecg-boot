package org.jeecg.modules.order.controller;

import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Copyright (C), 2019-2020, XXX有限公司
 * FileName: ApiPayController
 * Author:   kehaojian
 * Date:     2020/12/4 17:01
 * Description:
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
@Api(tags="api")
@RestController
@RequestMapping("/api/pay")
@Slf4j
public class ApiPayController {
    /**
     * 统一下单(详见https://pay.weixin.qq.com/wiki/doc/api/jsapi.php?chapter=9_1)
     * 在发起微信支付前，需要调用统一下单接口，获取"预支付交易会话标识"
     * 接口地址：https://api.mch.weixin.qq.com/pay/unifiedorder
     *
     * @param request 请求对象，注意一些参数如appid、mchid等不用设置，方法内会自动从配置对象中获取到（前提是对应配置中已经设置）
     */
//    @ApiOperation(value = "原生的统一下单接口")
//    @PostMapping("/unifiedOrder")
//    public WxPayUnifiedOrderResult unifiedOrder(@RequestBody WxPayUnifiedOrderRequest request) throws WxPayException {
//        return this.wxService.unifiedOrder(request);
//    }
}