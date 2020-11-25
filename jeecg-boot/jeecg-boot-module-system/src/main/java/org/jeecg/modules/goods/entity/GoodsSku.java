package org.jeecg.modules.goods.entity;

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
 * @Description: goods_sku
 * @Author: jeecg-boot
 * @Date:   2020-11-01
 * @Version: V1.0
 */
@Data
@TableName("goods_sku")
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@ApiModel(value="goods_sku对象", description="goods_sku")
public class GoodsSku implements Serializable {
    private static final long serialVersionUID = 1L;

	/**规格主键*/
//	@TableId(type = IdType.ASSIGN_ID)
    @ApiModelProperty(value = "规格主键")
    private java.lang.String id;
	/**组合key标签，用于展示*/
	@Excel(name = "组合key标签，用于展示", width = 15)
    @ApiModelProperty(value = "组合key标签，用于展示")
    private java.lang.String skuKey;
	/**平台价格*/
	@Excel(name = "平台价格", width = 15)
    @ApiModelProperty(value = "平台价格")
    private java.math.BigDecimal salePrice;
    /**库存*/
    @Excel(name = "库存", width = 15)
    @ApiModelProperty(value = "库存")
    private java.lang.Integer stock;
	/**商品主键*/
	@Excel(name = "商品主键", width = 15)
    @ApiModelProperty(value = "商品主键")
    private java.lang.String goodsId;
	/**创建人*/
    @ApiModelProperty(value = "创建人")
    private java.lang.String createBy;
	/**创建时间*/
	@JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern="yyyy-MM-dd")
    @ApiModelProperty(value = "创建时间")
    private java.util.Date createTime;
	/**更新者*/
    @ApiModelProperty(value = "更新者")
    private java.lang.String updateBy;
	/**更新时间*/
	@JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern="yyyy-MM-dd")
    @ApiModelProperty(value = "更新时间")
    private java.util.Date updateTime;
	/**删除标志（1代表存在 2代表删除）*/
	@Excel(name = "删除标志（1代表存在 2代表删除）", width = 15)
    @ApiModelProperty(value = "删除标志（1代表存在 2代表删除）")
    private java.lang.String delFlag;
}
