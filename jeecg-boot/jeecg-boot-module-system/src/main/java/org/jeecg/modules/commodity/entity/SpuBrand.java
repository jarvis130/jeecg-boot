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
 * @Description: spu_brand
 * @Author: jeecg-boot
 * @Date:   2020-11-27
 * @Version: V1.0
 */
@Data
@TableName("spu_brand")
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@ApiModel(value="spu_brand对象", description="spu_brand")
public class SpuBrand implements Serializable {
    private static final long serialVersionUID = 1L;

	/**品牌主键*/
	@TableId(type = IdType.ASSIGN_ID)
    @ApiModelProperty(value = "品牌主键")
    private java.lang.String id;
	/**品牌名称*/
	@Excel(name = "品牌名称", width = 15)
    @ApiModelProperty(value = "品牌名称")
    private java.lang.String brandName;
	/**logo地址*/
	@Excel(name = "品牌logo", width = 15)
    @ApiModelProperty(value = "品牌logo")
    private java.lang.String brandLogo;
	/**品牌备注*/
	@Excel(name = "品牌备注", width = 15)
    @ApiModelProperty(value = "品牌备注")
    private java.lang.String brandDesc;
	/**官方网址*/
	@Excel(name = "官方网址", width = 15)
    @ApiModelProperty(value = "官方网址")
    private java.lang.String siteUrl;
	/**排序*/
	@Excel(name = "排序", width = 15)
    @ApiModelProperty(value = "排序")
    private java.lang.Integer sortOrder;
	/**状态（0正常 1停用）*/
	@Excel(name = "状态（0正常 1停用）", width = 15)
    @ApiModelProperty(value = "状态（0正常 1停用）")
    @Dict(dicCode = "status")
    private java.lang.Integer status;
	/**租户编号*/
	@Excel(name = "租户编号", width = 15)
    @ApiModelProperty(value = "租户编号")
    private java.lang.String tennantId;
	/**创建者*/
    @ApiModelProperty(value = "创建者")
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
	/**删除标志（0代表存在 2代表删除）*/
	@Excel(name = "删除标志（0代表存在 2代表删除）", width = 15)
    @ApiModelProperty(value = "删除标志（0代表存在 2代表删除）")
    private java.lang.String delFlag;
}
