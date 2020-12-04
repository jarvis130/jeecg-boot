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
 * @Description: order_pay
 * @Author: jeecg-boot
 * @Date:   2020-12-02
 * @Version: V1.0
 */
@Data
@TableName("order_pay")
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@ApiModel(value="order_pay对象", description="order_pay")
public class OrderPay implements Serializable {
    private static final long serialVersionUID = 1L;

	/**主键*/
	@TableId(type = IdType.ASSIGN_ID)
    @ApiModelProperty(value = "主键")
    private java.lang.Integer id;
	/**订单编号*/
	@Excel(name = "订单编号", width = 15)
    @ApiModelProperty(value = "订单编号")
    private java.lang.String orderNo;
	/**买家ID*/
	@Excel(name = "买家ID", width = 15)
    @ApiModelProperty(value = "买家ID")
    private java.lang.String buyerId;
	/**支付方式名称代码*/
	@Excel(name = "支付方式名称代码", width = 15)
    @ApiModelProperty(value = "支付方式名称代码")
    private java.lang.String paymentCode;
	/**支付方式名称*/
	@Excel(name = "支付方式名称", width = 15)
    @ApiModelProperty(value = "支付方式名称")
    private java.lang.String paymentName;
	/**支付金额*/
	@Excel(name = "支付金额", width = 15)
    @ApiModelProperty(value = "支付金额")
    private java.math.BigDecimal amount;
	/**第三方机构返回单号*/
	@Excel(name = "第三方机构返回单号", width = 15)
    @ApiModelProperty(value = "第三方机构返回单号")
    private java.lang.Integer outOrderNo;
	/**第三方机构返回结果信息*/
	@Excel(name = "第三方机构返回结果信息", width = 15)
    @ApiModelProperty(value = "第三方机构返回结果信息")
    private java.lang.String orderDesc;
	/**0默认未支付1支付中2已支付(只有第三方支付接口通知到时才会更改此状态)3支付失败*/
	@Excel(name = "0默认未支付1支付中2已支付(只有第三方支付接口通知到时才会更改此状态)3支付失败", width = 15)
    @ApiModelProperty(value = "0默认未支付1支付中2已支付(只有第三方支付接口通知到时才会更改此状态)3支付失败")
    private java.lang.Integer payState;
	/**创建时间*/
    @ApiModelProperty(value = "创建时间")
    private java.util.Date createTime;
	/**更新时间*/
	@Excel(name = "更新时间", width = 15)
    @ApiModelProperty(value = "更新时间")
    private java.util.Date paymentTime;
}
