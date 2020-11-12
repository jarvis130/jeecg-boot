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
 * @Description: spec_group
 * @Author: jeecg-boot
 * @Date:   2020-11-10
 * @Version: V1.0
 */
@Data
@TableName("spec_group")
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@ApiModel(value="spec_group对象", description="spec_group")
public class SpecGroup implements Serializable {
    private static final long serialVersionUID = 1L;

	/**id*/
	@TableId(type = IdType.ASSIGN_ID)
    @ApiModelProperty(value = "id")
    private java.lang.String id;
	/**商品分类id,一个分类下有多个规格组*/
	@Excel(name = "商品分类id,一个分类下有多个规格组", width = 15)
    @ApiModelProperty(value = "商品分类id,一个分类下有多个规格组")
    @Dict(dicCode = "id",dictTable="sys_category",dicText="name")
    private java.lang.String cid;
	/**规格组的名称*/
	@Excel(name = "规格组的名称", width = 15)
    @ApiModelProperty(value = "规格组的名称")
    private java.lang.String name;
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

    /**创建人*/
    @ApiModelProperty(value = "创建人")
    private java.lang.String createBy;

    /**创建时间*/
    @JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern="yyyy-MM-dd")
    @ApiModelProperty(value = "创建时间")
    private java.util.Date createTime;

    /**更新人*/
    @ApiModelProperty(value = "更新人")
    private java.lang.String updateBy;

    /**更新时间*/
    @JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern="yyyy-MM-dd")
    @ApiModelProperty(value = "更新时间")
    private java.util.Date updateTime;
}
