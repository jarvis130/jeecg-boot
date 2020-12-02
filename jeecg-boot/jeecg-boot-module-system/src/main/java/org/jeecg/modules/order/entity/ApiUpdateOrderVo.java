package org.jeecg.modules.order.entity;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * Copyright (C), 2019-2020, XXX有限公司
 * FileName: ApiOrderVo
 * Author:   kehaojian
 * Date:     2020/12/2 8:56
 * Description:
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
@Data
public class ApiUpdateOrderVo {
    @ApiModelProperty(value = "订单号", required = true)
    private String orderNo;

    /**评价状态 0未评价，1已评价*/
    @ApiModelProperty(value = "评价状态 0未评价，1已评价")
    private Object evaluationState;
    /**订单状态：0(已取消)1 未付款;2：付款中;3已付款4:已发货;5:已收货;*/
    @ApiModelProperty(value = "订单状态：0(已取消)1 未付款;2：付款中;3已付款4:已发货;5:已收货;")
    private Object orderState;
    /**退款状态:0是无退款,1是部分退款,2是全部退款*/
    @ApiModelProperty(value = "退款状态:0是无退款,1是部分退款,2是全部退款")
    private Integer refundState;
    /**物流单号*/
    @ApiModelProperty(value = "物流单号")
    private String shippingCode;
}