package org.jeecg.modules.tech.entity;

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
 * @Description: tech_info
 * @Author: jeecg-boot
 * @Date:   2020-12-02
 * @Version: V1.0
 */
@Data
@TableName("tech_info")
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@ApiModel(value="tech_info对象", description="tech_info")
public class TechInfo implements Serializable {
    private static final long serialVersionUID = 1L;

	/**主键,user_account编号*/
	@TableId(type = IdType.ASSIGN_ID)
    @ApiModelProperty(value = "主键,user_account编号")
    private java.lang.String id;
	/**工号*/
	@Excel(name = "工号", width = 15)
    @ApiModelProperty(value = "工号")
    private java.lang.String code;
	/**简介*/
	@Excel(name = "简介", width = 15)
    @ApiModelProperty(value = "简介")
    private java.lang.String brief;
	/**详情*/
	@Excel(name = "详情", width = 15)
    @ApiModelProperty(value = "详情")
    private java.lang.String description;
	/**状态*/
	@Excel(name = "状态", width = 15)
    @ApiModelProperty(value = "状态")
    private java.lang.Integer status;
	/**通用属性*/
	@Excel(name = "通用属性", width = 15)
    @ApiModelProperty(value = "通用属性")
    private java.lang.String genericSpec;
	/**照片*/
	@Excel(name = "照片", width = 15)
    @ApiModelProperty(value = "照片")
    private java.lang.String thumbs;
	/**删除标识*/
	@Excel(name = "删除标识", width = 15)
    @ApiModelProperty(value = "删除标识")
    private java.lang.Integer delFlag;

    /**添加时间*/
    @JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern="yyyy-MM-dd")
    @ApiModelProperty(value = "添加时间")
    private java.util.Date createTime;

    /**最后修改时间*/
    @JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern="yyyy-MM-dd")
    @ApiModelProperty(value = "最后修改时间")
    private java.util.Date updateTime;

    /**更新者*/
    @ApiModelProperty(value = "更新者")
    private java.lang.String updateBy;

    /**创建者*/
    @ApiModelProperty(value = "创建者")
    private java.lang.String createBy;

    /**紧急联系人*/
    @Excel(name = "紧急联系人", width = 15)
    @ApiModelProperty(value = "紧急联系人")
    private java.lang.String linkName;

    /**紧急联系人电话*/
    @Excel(name = "紧急联系人电话", width = 15)
    @ApiModelProperty(value = "紧急联系人电话")
    private java.lang.String linkMobile;

    private java.lang.String linkData;

    private java.lang.String cid;
}
