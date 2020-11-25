package org.jeecg.modules.commodity.entity;

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
 * @Description: spec_param
 * @Author: jeecg-boot
 * @Date:   2020-11-10
 * @Version: V1.0
 */
@Data
@TableName("spec_param")
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@ApiModel(value="spec_param对象", description="spec_param")
public class SpecParam implements Serializable {
    private static final long serialVersionUID = 1L;

	/**主键*/
	@TableId(type = IdType.ASSIGN_ID)
    @ApiModelProperty(value = "主键")
    private java.lang.String id;
	/**商品分类ID*/
	@Excel(name = "商品分类ID", width = 15)
    @ApiModelProperty(value = "商品分类ID")
    @Dict(dicCode = "id",dictTable="sys_category",dicText="name")
    private java.lang.String cid;
	/**groupId*/
	@Excel(name = "groupId", width = 15)
    @ApiModelProperty(value = "groupId")
    private java.lang.String groupId;
	/**参数名*/
	@Excel(name = "参数名", width = 15)
    @ApiModelProperty(value = "参数名")
    private java.lang.String name;
	/**是否数字型参数，true或false*/
	@Excel(name = "是否数字型参数，true或false", width = 15)
    @ApiModelProperty(value = "是否数字型参数，true或false")
    private java.lang.Integer isNumeric;
	/**数字类型参数的单位，非数字可以为空*/
	@Excel(name = "数字类型参数的单位，非数字可以为空", width = 15)
    @ApiModelProperty(value = "数字类型参数的单位，非数字可以为空")
    private java.lang.String unit;
	/**是否是sku通用属性，true或false*/
	@Excel(name = "是否是sku通用属性，true或false", width = 15)
    @ApiModelProperty(value = "是否是sku通用属性，true或false")
    private java.lang.Integer generic;
	/**是否用于搜索过滤，true或false*/
	@Excel(name = "是否用于搜索过滤，true或false", width = 15)
    @ApiModelProperty(value = "是否用于搜索过滤，true或false")
    private java.lang.Integer searching;
	/**数值类型参数，如果需要搜索，则添加分段间隔值，如CPU频率间隔:0.5-1.0*/
	@Excel(name = "数值类型参数，如果需要搜索，则添加分段间隔值，如CPU频率间隔:0.5-1.0", width = 15)
    @ApiModelProperty(value = "数值类型参数，如果需要搜索，则添加分段间隔值，如CPU频率间隔:0.5-1.0")
    private java.lang.String segments;
	/**租户编号*/
	@Excel(name = "租户编号", width = 15)
    @ApiModelProperty(value = "租户编号")
    private java.lang.String tennantId;
	/**删除标志（1代表存在 0表删除）*/
	@Excel(name = "删除标志（1代表存在 0表删除）", width = 15)
    @ApiModelProperty(value = "删除标志（1代表存在 0表删除）")
    private java.lang.Integer delFlag;

    /**状态*/
    @Excel(name = "状态", width = 15)
    @Dict(dicCode = "enable_status")
    @ApiModelProperty(value = "状态")
    private java.lang.Integer status;

    /**排序*/
    @Excel(name = "排序", width = 15)
    @ApiModelProperty(value = "排序")
    private java.lang.Integer sortNo;
}
