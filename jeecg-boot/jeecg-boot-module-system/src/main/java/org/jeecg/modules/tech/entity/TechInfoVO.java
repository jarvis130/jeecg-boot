package org.jeecg.modules.tech.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import org.jeecg.common.aspect.annotation.Dict;
import org.jeecgframework.poi.excel.annotation.Excel;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;

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
public class TechInfoVO implements Serializable {
    private static final long serialVersionUID = 1L;

	/**主键,user_account编号*/
	@TableId(type = IdType.ASSIGN_ID)
    @ApiModelProperty(value = "主键,user_account编号")
    private String id;
	/**工号*/
	@Excel(name = "工号", width = 15)
    @ApiModelProperty(value = "工号")
    private String code;
	/**简介*/
	@Excel(name = "简介", width = 15)
    @ApiModelProperty(value = "简介")
    private String brief;
	/**详情*/
	@Excel(name = "详情", width = 15)
    @ApiModelProperty(value = "详情")
    private String desc;
	/**状态*/
	@Excel(name = "状态", width = 15)
    @ApiModelProperty(value = "状态")
    private Integer status;
	/**通用属性*/
	@Excel(name = "通用属性", width = 15)
    @ApiModelProperty(value = "通用属性")
    private String generalSpec;
	/**照片*/
	@Excel(name = "照片", width = 15)
    @ApiModelProperty(value = "照片")
    private String images;
	/**删除标识*/
	@Excel(name = "删除标识", width = 15)
    @ApiModelProperty(value = "删除标识")
    private Integer delFlag;

    /**真实姓名*/
    @Excel(name = "真实姓名", width = 15)
    @ApiModelProperty(value = "真实姓名")
    private java.lang.String realName;

    /**昵称*/
    @Excel(name = "昵称", width = 15)
    @ApiModelProperty(value = "昵称")
    private java.lang.String nickName;

    /**手机*/
    @Excel(name = "手机", width = 15)
    @ApiModelProperty(value = "手机")
    private java.lang.String mobile;

    /**会员邮箱*/
    @Excel(name = "会员邮箱", width = 15)
    @ApiModelProperty(value = "会员邮箱")
    private java.lang.String email;

    /**证件类型*/
    @Excel(name = "证件类型", width = 15)
    @ApiModelProperty(value = "证件类型")
    private java.lang.Integer idType;

    /**证件号*/
    @Excel(name = "证件号", width = 15)
    @ApiModelProperty(value = "证件号")
    private java.lang.String idCode;

    /**
     * 性别（1：男 2：女）
     */
    @Excel(name = "性别", width = 15,dicCode="sex")
    @Dict(dicCode = "sex")
    @ApiModelProperty(value = "性别（1：男 2：女）")
    private java.lang.Integer sex;

    /**生日日期*/
    @Excel(name = "生日日期", width = 15, format = "yyyy-MM-dd")
    @JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern="yyyy-MM-dd")
    @ApiModelProperty(value = "生日日期")
    private java.util.Date birthday;

    /**紧急联系人*/
    @Excel(name = "紧急联系人", width = 15)
    @ApiModelProperty(value = "紧急联系人")
    private java.lang.String linkName;

    /**紧急联系人电话*/
    @Excel(name = "紧急联系人电话", width = 15)
    @ApiModelProperty(value = "紧急联系人电话")
    private java.lang.String linkMobile;


}
