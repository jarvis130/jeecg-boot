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
 * @Date:   2020-08-28
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
	@Excel(name = "客户主键", width = 15)
    @ApiModelProperty(value = "客户主键")
    private Integer customerId;
	/**平台账号*/
	@Excel(name = "平台账号", width = 15)
    @ApiModelProperty(value = "平台账号")
    private Integer userId;
	/**客户名称*/
	@Excel(name = "客户名称", width = 15)
    @ApiModelProperty(value = "客户名称")
    private String customerName;
	/**客户类型；0=个人；1=公司*/
	@Excel(name = "客户类型；0=个人；1=公司", width = 15)
    @ApiModelProperty(value = "客户类型；0=个人；1=公司")
    private String customerType;
	/**手机号*/
	@Excel(name = "手机号", width = 15)
    @ApiModelProperty(value = "手机号")
    private String mobile;
	/**公司名称*/
	@Excel(name = "公司名称", width = 15)
    @ApiModelProperty(value = "公司名称")
    private String companyName;
	/**公司地址*/
	@Excel(name = "公司地址", width = 15)
    @ApiModelProperty(value = "公司地址")
    private String companyAddress;
	/**行业类型*/
	@Excel(name = "行业类型", width = 15)
    @ApiModelProperty(value = "行业类型")
    private String industryType;
	/**客户经理编号*/
	@Excel(name = "客户经理编号", width = 15)
    @ApiModelProperty(value = "客户经理编号")
    private Integer handleId;
	/**创建人*/
    @ApiModelProperty(value = "创建人")
    private Integer createBy;
	/**创建时间*/
	@JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern="yyyy-MM-dd")
    @ApiModelProperty(value = "创建时间")
    private Date createTime;
	/**更新人*/
    @ApiModelProperty(value = "更新人")
    private Integer updateBy;
	/**更新时间*/
	@JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern="yyyy-MM-dd")
    @ApiModelProperty(value = "更新时间")
    private Date updateTime;
	/**来源*/
	@Excel(name = "来源", width = 15)
    @ApiModelProperty(value = "来源")
    private String source;
	/**介绍人编号*/
	@Excel(name = "介绍人编号", width = 15)
    @ApiModelProperty(value = "介绍人编号")
    private Integer introducerId;
	/**主体编号*/
	@Excel(name = "主体编号", width = 15)
    @ApiModelProperty(value = "主体编号")
    private Integer subjectId;
}
