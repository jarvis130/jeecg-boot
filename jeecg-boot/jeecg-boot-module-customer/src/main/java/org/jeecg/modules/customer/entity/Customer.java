package org.jeecg.modules.customer.entity;

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
 * @Description: crm_customer
 * @Author: jeecg-boot
 * @Date:   2020-09-07
 * @Version: V1.0
 */
@Data
@TableName("crm_customer")
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@ApiModel(value="crm_customer对象", description="crm_customer")
public class Customer implements Serializable {
    private static final long serialVersionUID = 1L;

	/**客户主键*/
	@TableId(type = IdType.ASSIGN_ID)
    @ApiModelProperty(value = "客户主键")
    private java.lang.String id;

	/**会员账号*/
	@Excel(name = "会员账号", width = 15)
    @ApiModelProperty(value = "会员账号")
    private java.lang.String userId;

	/**客户名称*/
	@Excel(name = "客户名称", width = 15)
    @ApiModelProperty(value = "客户名称")
    private java.lang.String customerName;

	/**客户类型；0=个人；1=公司*/
	@Excel(name = "客户类型；0=个人；1=公司", width = 15)
    @ApiModelProperty(value = "客户类型；1=个人；2=公司")
    @Dict(dicCode = "customer_type")
    private java.lang.String customerType;

	/**手机号*/
	@Excel(name = "手机号", width = 15)
    @ApiModelProperty(value = "手机号")
    private java.lang.String customerMobile;

	/**公司名称*/
	@Excel(name = "公司名称", width = 15)
    @ApiModelProperty(value = "公司名称")
    private java.lang.String companyName;

	/**公司地址*/
	@Excel(name = "公司地址", width = 15)
    @ApiModelProperty(value = "公司地址")
    private java.lang.String companyAddress;

	/**行业类型*/
	@Excel(name = "行业类型", width = 15)
    @ApiModelProperty(value = "行业类型")
    @Dict(dicCode = "industry_type")
    private java.lang.String industryType;

	/**客户经理编号*/
	@Excel(name = "客户经理", width = 20)
    @ApiModelProperty(value = "客户经理")
    private java.lang.String customerManager;

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

	/**来源*/
	@Excel(name = "客户来源", width = 15)
    @ApiModelProperty(value = "客户来源")
    @Dict(dicCode = "customer_source")
    private java.lang.String customerSource;

	/**介绍人姓名*/
	@Excel(name = "介绍人会员账号", width = 15)
    @ApiModelProperty(value = "介绍人会员账号")
    private java.lang.String introducerName;

    /**介绍人电话*/
    @Excel(name = "介绍人会员账号", width = 15)
    @ApiModelProperty(value = "介绍人会员账号")
    private java.lang.String introducerMobile;

	/**主体编号*/
	@Excel(name = "主体编号", width = 15)
    @ApiModelProperty(value = "主体编号")
    private java.lang.Integer subjectId;

    /**
     * 状态(1：正常  2：冻结 ）
     */
    @Excel(name = "状态", width = 15,dicCode="user_status")
    @Dict(dicCode = "user_status")
    private java.lang.Integer status;
}
