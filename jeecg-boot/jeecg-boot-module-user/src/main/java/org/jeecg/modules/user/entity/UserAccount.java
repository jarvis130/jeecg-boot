package org.jeecg.modules.user.entity;

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
 * @Description: user_account
 * @Author: jeecg-boot
 * @Date:   2020-08-30
 * @Version: V1.0
 */
@Data
@TableName("user_account")
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@ApiModel(value="user_account对象", description="user_account")
public class UserAccount implements Serializable {
    private static final long serialVersionUID = 1L;

	/**会员编号*/
	@Excel(name = "会员编号", width = 15)
    @ApiModelProperty(value = "会员编号")
    private java.lang.Long userId;
	/**用户名*/
	@Excel(name = "用户名", width = 15)
    @ApiModelProperty(value = "用户名")
    private java.lang.String userName;
	/**用户密码*/
	@Excel(name = "用户密码", width = 15)
    @ApiModelProperty(value = "用户密码")
    private java.lang.String password;
	/**salt*/
	@Excel(name = "salt", width = 15)
    @ApiModelProperty(value = "salt")
    private java.lang.String salt;
	/**昵称*/
	@Excel(name = "昵称", width = 15)
    @ApiModelProperty(value = "昵称")
    private java.lang.String nickname;
	/**手机*/
	@Excel(name = "手机", width = 15)
    @ApiModelProperty(value = "手机")
    private java.lang.String mobile;
	/**会员邮箱*/
	@Excel(name = "会员邮箱", width = 15)
    @ApiModelProperty(value = "会员邮箱")
    private java.lang.String email;
	/**证件类型 1：身份证*/
	@Excel(name = "证件类型 1：身份证", width = 15)
    @ApiModelProperty(value = "证件类型 1：身份证")
    private java.lang.Integer idType;
	/**证件号*/
	@Excel(name = "证件号", width = 15)
    @ApiModelProperty(value = "证件号")
    private java.lang.String idCode;
	/**性别，0，保密；1，男；2，女*/
	@Excel(name = "性别，0，保密；1，男；2，女", width = 15)
    @ApiModelProperty(value = "性别，0，保密；1，男；2，女")
    private java.lang.Integer sex;
	/**生日日期*/
	@Excel(name = "生日日期", width = 15, format = "yyyy-MM-dd")
	@JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern="yyyy-MM-dd")
    @ApiModelProperty(value = "生日日期")
    private java.util.Date birthday;
	/**用户现有资金*/
	@Excel(name = "用户现有资金", width = 15)
    @ApiModelProperty(value = "用户现有资金")
    private java.math.BigDecimal userMoney;
	/**用户冻结资金*/
	@Excel(name = "用户冻结资金", width = 15)
    @ApiModelProperty(value = "用户冻结资金")
    private java.math.BigDecimal frozenMoney;
	/**消费积分*/
	@Excel(name = "消费积分", width = 15)
    @ApiModelProperty(value = "消费积分")
    private java.lang.Integer payPoints;
	/**会员等级积分*/
	@Excel(name = "会员等级积分", width = 15)
    @ApiModelProperty(value = "会员等级积分")
    private java.lang.Integer rankPoints;
	/**收货信息id，取值表user_address */
	@Excel(name = "收货信息id，取值表user_address ", width = 15)
    @ApiModelProperty(value = "收货信息id，取值表user_address ")
    private java.lang.Integer addressId;
	/**最后一次登录时间*/
	@Excel(name = "最后一次登录时间", width = 15, format = "yyyy-MM-dd")
	@JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern="yyyy-MM-dd")
    @ApiModelProperty(value = "最后一次登录时间")
    private java.util.Date lastLogin;
	/**最后一次登录ip*/
	@Excel(name = "最后一次登录ip", width = 15)
    @ApiModelProperty(value = "最后一次登录ip")
    private java.lang.String lastIp;
	/**登录次数*/
	@Excel(name = "登录次数", width = 15)
    @ApiModelProperty(value = "登录次数")
    private java.lang.Integer loginCount;
	/**会员等级id，取值user_rank*/
	@Excel(name = "会员等级id，取值user_rank", width = 15)
    @ApiModelProperty(value = "会员等级id，取值user_rank")
    private java.lang.Integer userRank;
	/**状态（0停用 1正常）*/
	@Excel(name = "状态（0停用 1正常）", width = 15)
    @ApiModelProperty(value = "状态（0停用 1正常）")
    private java.lang.String status;
	/**创建时间*/
	@JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern="yyyy-MM-dd")
    @ApiModelProperty(value = "创建时间")
    private java.util.Date createTime;
	/**创建人*/
    @ApiModelProperty(value = "创建人")
    private java.lang.String createBy;
	/**更新时间*/
	@JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern="yyyy-MM-dd")
    @ApiModelProperty(value = "更新时间")
    private java.util.Date updateTime;
	/**更新人*/
    @ApiModelProperty(value = "更新人")
    private java.lang.String updateBy;
}
