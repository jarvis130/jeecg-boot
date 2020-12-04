package org.jeecg.modules.order.entity;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.jeecgframework.poi.excel.annotation.Excel;

import java.util.List;

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
public class ApiOrderVo {
    /**卖家店铺id*/
    @ApiModelProperty(value = "卖家店铺id")
    private java.lang.String tenantId;
    /**卖家店铺名称*/
    @ApiModelProperty(value = "卖家店铺名称")
    private java.lang.String tenantName;
    /**买家id*/
    @ApiModelProperty(value = "买家id")
    private java.lang.String buyerId;
    /**买家姓名*/
    @ApiModelProperty(value = "买家姓名")
    private java.lang.String buyerName;

    /**商品价格*/
    @ApiModelProperty(value = "商品价格")
    private java.math.BigDecimal goodsAmount;
    /**订单总价格*/
    @ApiModelProperty(value = "订单总价格")
    private java.math.BigDecimal orderAmount;
    /**实际支付金额*/
    @ApiModelProperty(value = "实际支付金额")
    private java.math.BigDecimal amount;
    /**运费*/
    @ApiModelProperty(value = "运费")
    private java.math.BigDecimal shippingFee;
    /**订单类型*/
    @ApiModelProperty(value = "订单类型")
    private java.lang.Integer orderType;
    /**评价状态 0未评价，1已评价*/
    @ApiModelProperty(value = "评价状态 0未评价，1已评价")
    private java.lang.Object evaluationState;
    /**订单状态：0(已取消)1 未付款;2：付款中;3已付款4:已发货;5:已收货;*/
    @ApiModelProperty(value = "订单状态：0(已取消)1 未付款;2：付款中;3已付款4:已发货;5:已收货;")
    private java.lang.Object orderState;
    /**退款状态:0是无退款,1是部分退款,2是全部退款*/
    @ApiModelProperty(value = "退款状态:0是无退款,1是部分退款,2是全部退款")
    private java.lang.Integer refundState;
    /**退款金额*/
    @Excel(name = "退款金额", width = 15)
    @ApiModelProperty(value = "退款金额")
    private java.math.BigDecimal refundAmount;
    /**延迟时间,默认为0*/
    @Excel(name = "延迟时间,默认为0", width = 15)
    @ApiModelProperty(value = "延迟时间,默认为0")
    private java.lang.Integer delayTime;
    /**1WEB2mobile*/
    @ApiModelProperty(value = "1WEB2mobile")
    private java.lang.Object orderFrom;
    /**物流单号*/
    @ApiModelProperty(value = "物流单号")
    private java.lang.String shippingCode;

    @ApiModelProperty(value = "商品信息")
    private List<GoodsVo> goodsVos;

}