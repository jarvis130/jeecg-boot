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
 * @Description: order_goods
 * @Author: jeecg-boot
 * @Date:   2020-12-02
 * @Version: V1.0
 */
@Data
@TableName("order_goods")
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@ApiModel(value="order_goods对象", description="order_goods")
public class OrderGoods implements Serializable {
    private static final long serialVersionUID = 1L;

	/**id*/
	@TableId(type = IdType.ASSIGN_ID)
    @ApiModelProperty(value = "id")
    private java.lang.Integer id;
	/**订单id*/
	@Excel(name = "订单id", width = 15)
    @ApiModelProperty(value = "订单id")
    private java.lang.String orderId;
	/**商品id*/
	@Excel(name = "商品id", width = 15)
    @ApiModelProperty(value = "商品id")
    private java.lang.String goodsId;
	/**商品名称*/
	@Excel(name = "商品名称", width = 15)
    @ApiModelProperty(value = "商品名称")
    private java.lang.String goodsName;
	/**商品价格*/
	@Excel(name = "商品价格", width = 15)
    @ApiModelProperty(value = "商品价格")
    private java.math.BigDecimal goodsPrice;
	/**商品数量*/
	@Excel(name = "商品数量", width = 15)
    @ApiModelProperty(value = "商品数量")
    private java.lang.Integer goodsNum;
	/**商品图片*/
	@Excel(name = "商品图片", width = 15)
    @ApiModelProperty(value = "商品图片")
    private java.lang.String goodsImage;
	/**商品实际成交价*/
	@Excel(name = "商品实际成交价", width = 15)
    @ApiModelProperty(value = "商品实际成交价")
    private java.math.BigDecimal goodsPayPrice;
	/**卖家ID*/
	@Excel(name = "卖家ID", width = 15)
    @ApiModelProperty(value = "卖家ID")
    private java.lang.String tenantId;
	/**买家ID*/
	@Excel(name = "买家ID", width = 15)
    @ApiModelProperty(value = "买家ID")
    private java.lang.String buyerId;
	/**1默认2团购商品3限时折扣商品4组合套装5赠品*/
	@Excel(name = "1默认2团购商品3限时折扣商品4组合套装5赠品", width = 15)
    @ApiModelProperty(value = "1默认2团购商品3限时折扣商品4组合套装5赠品")
    private java.lang.Integer goodsType;
	/**促销活动ID（团购ID/限时折扣ID/优惠套装ID）与goods_type搭配使用*/
	@Excel(name = "促销活动ID（团购ID/限时折扣ID/优惠套装ID）与goods_type搭配使用", width = 15)
    @ApiModelProperty(value = "促销活动ID（团购ID/限时折扣ID/优惠套装ID）与goods_type搭配使用")
    private java.lang.Integer promotionsId;
	/**佣金比例*/
	@Excel(name = "佣金比例", width = 15)
    @ApiModelProperty(value = "佣金比例")
    private java.lang.Integer commisRate;
	/**创建时间*/
    @ApiModelProperty(value = "创建时间")
    private java.util.Date createTime;
	/**更新时间*/
    @ApiModelProperty(value = "更新时间")
    private java.util.Date updateTime;
}
