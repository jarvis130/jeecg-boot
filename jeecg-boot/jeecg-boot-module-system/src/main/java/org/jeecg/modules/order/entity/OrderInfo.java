package org.jeecg.modules.order.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import org.jeecgframework.poi.excel.annotation.Excel;

import java.io.Serializable;

/**
 * @Description: order_info
 * @Author: jeecg-boot
 * @Date:   2020-12-02
 * @Version: V1.0
 */
@Data
@TableName("order_info")
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@ApiModel(value="order_info对象", description="order_info")
public class OrderInfo implements Serializable {
    private static final long serialVersionUID = 1L;

	/**订单索引id*/
	@TableId(type = IdType.ASSIGN_ID)
    @ApiModelProperty(value = "订单索引id")
    private java.lang.Integer id;
	/**支付单号*/
	@Excel(name = "支付单号", width = 15)
    @ApiModelProperty(value = "支付单号")
    private java.lang.Integer payNo;
	/**卖家店铺id*/
	@Excel(name = "卖家店铺id", width = 15)
    @ApiModelProperty(value = "卖家店铺id")
    private java.lang.String tenantId;
	/**卖家店铺名称*/
	@Excel(name = "卖家店铺名称", width = 15)
    @ApiModelProperty(value = "卖家店铺名称")
    private java.lang.String tenantName;
	/**买家id*/
	@Excel(name = "买家id", width = 15)
    @ApiModelProperty(value = "买家id")
    private java.lang.String buyerId;
	/**买家姓名*/
	@Excel(name = "买家姓名", width = 15)
    @ApiModelProperty(value = "买家姓名")
    private java.lang.String buyerName;
	/**商品数量*/
	@Excel(name = "商品数量", width = 15)
    @ApiModelProperty(value = "商品数量")
    private java.lang.Integer goodsCount;
	/**商品价格*/
	@Excel(name = "商品价格", width = 15)
    @ApiModelProperty(value = "商品价格")
    private java.math.BigDecimal goodsAmount;
	/**订单总价格*/
	@Excel(name = "订单总价格", width = 15)
    @ApiModelProperty(value = "订单总价格")
    private java.math.BigDecimal orderAmount;
	/**实际支付金额*/
	@Excel(name = "实际支付金额", width = 15)
    @ApiModelProperty(value = "实际支付金额")
    private java.math.BigDecimal amount;
	/**运费*/
	@Excel(name = "运费", width = 15)
    @ApiModelProperty(value = "运费")
    private java.math.BigDecimal shippingFee;
	/**订单类型*/
	@Excel(name = "订单类型", width = 15)
    @ApiModelProperty(value = "订单类型")
    private java.lang.Integer orderType;
	/**评价状态 0未评价，1已评价*/
	@Excel(name = "评价状态 0未评价，1已评价", width = 15)
    @ApiModelProperty(value = "评价状态 0未评价，1已评价")
    private java.lang.Integer evaluationState;
	/**订单状态：0(已取消)1 未付款;2：付款中;3已付款4:已发货;5:已收货;*/
	@Excel(name = "订单状态：0(已取消)1 未付款;2：付款中;3已付款4:已发货;5:已收货;", width = 15)
    @ApiModelProperty(value = "订单状态：0(已取消)1 未付款;2：付款中;3已付款4:已发货;5:已收货;")
    private java.lang.Integer orderState;
	/**退款状态:0是无退款,1是部分退款,2是全部退款*/
	@Excel(name = "退款状态:0是无退款,1是部分退款,2是全部退款", width = 15)
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
	@Excel(name = "1WEB2mobile", width = 15)
    @ApiModelProperty(value = "1WEB2mobile")
    private java.lang.Integer orderFrom;
	/**物流单号*/
	@Excel(name = "物流单号", width = 15)
    @ApiModelProperty(value = "物流单号")
    private java.lang.Integer shippingCode;
	/**创建时间*/
    @ApiModelProperty(value = "创建时间")
    private java.util.Date createTime;
	/**更新时间*/
    @ApiModelProperty(value = "更新时间")
    private java.util.Date updateTime;
}
