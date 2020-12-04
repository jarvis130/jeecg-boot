package org.jeecg.modules.order.entity;

import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.math.BigDecimal;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.format.annotation.DateTimeFormat;
import org.jeecgframework.poi.excel.annotation.Excel;
import org.jeecg.common.aspect.annotation.Dict;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * @Description: order_extend
 * @Author: jeecg-boot
 * @Date:   2020-12-02
 * @Version: V1.0
 */
@Data
@TableName("order_extend")
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@ApiModel(value="order_extend对象", description="order_extend")
public class OrderExtend implements Serializable {
    private static final long serialVersionUID = 1L;

	/**id*/
	@TableId(type = IdType.ASSIGN_ID)
    @ApiModelProperty(value = "id")
    private java.lang.Integer id;
	/**订单id*/
	@Excel(name = "订单id", width = 15)
    @ApiModelProperty(value = "订单id")
    private java.lang.String orderId;
	/**卖家ID*/
	@Excel(name = "卖家ID", width = 15)
    @ApiModelProperty(value = "卖家ID")
    private java.lang.String tenantId;
	/**配送时间*/
	@Excel(name = "配送时间", width = 15)
    @ApiModelProperty(value = "配送时间")
    private java.util.Date shippingTime;
	/**配送公司ID*/
	@Excel(name = "配送公司ID", width = 15)
    @ApiModelProperty(value = "配送公司ID")
    private java.lang.Integer shippingExpressId;
	/**评价时间*/
	@Excel(name = "评价时间", width = 15)
    @ApiModelProperty(value = "评价时间")
    private java.util.Date evaluationTime;
	/**卖家是否已评价买家*/
	@Excel(name = "卖家是否已评价买家", width = 15)
    @ApiModelProperty(value = "卖家是否已评价买家")
    private java.lang.Object evalsellerState;
	/**卖家评价买家的时间*/
	@Excel(name = "卖家评价买家的时间", width = 15)
    @ApiModelProperty(value = "卖家评价买家的时间")
    private java.lang.Integer evalsellerTime;
	/**订单留言*/
	@Excel(name = "订单留言", width = 15)
    @ApiModelProperty(value = "订单留言")
    private java.lang.String orderMessage;
	/**订单赠送积分*/
	@Excel(name = "订单赠送积分", width = 15)
    @ApiModelProperty(value = "订单赠送积分")
    private java.lang.Integer orderPointscount;
	/**代金券面额*/
	@Excel(name = "代金券面额", width = 15)
    @ApiModelProperty(value = "代金券面额")
    private java.lang.Integer voucherPrice;
	/**代金券编码*/
	@Excel(name = "代金券编码", width = 15)
    @ApiModelProperty(value = "代金券编码")
    private java.lang.String voucherCode;
	/**发货备注*/
	@Excel(name = "发货备注", width = 15)
    @ApiModelProperty(value = "发货备注")
    private java.lang.Object deliverExplain;
	/**发货地址*/
	@Excel(name = "发货地址", width = 15)
    @ApiModelProperty(value = "发货地址")
    private java.lang.String daddressId;
	/**收货人地址信息ID*/
	@Excel(name = "收货人地址信息ID", width = 15)
    @ApiModelProperty(value = "收货人地址信息ID")
    private java.lang.String reciverAddressId;
	/**发票信息*/
	@Excel(name = "发票信息", width = 15)
    @ApiModelProperty(value = "发票信息")
    private java.lang.String invoiceInfo;
	/**促销信息备注*/
	@Excel(name = "促销信息备注", width = 15)
    @ApiModelProperty(value = "促销信息备注")
    private java.lang.String promotionInfo;
	/**创建时间*/
    @ApiModelProperty(value = "创建时间")
    private java.util.Date createTime;
	/**更新时间*/
    @ApiModelProperty(value = "更新时间")
    private java.util.Date updateTime;
}
