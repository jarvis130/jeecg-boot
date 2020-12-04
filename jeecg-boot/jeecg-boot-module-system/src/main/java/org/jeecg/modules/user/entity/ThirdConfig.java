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
 * @Description: third_config
 * @Author: jeecg-boot
 * @Date:   2020-12-03
 * @Version: V1.0
 */
@Data
@TableName("third_config")
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@ApiModel(value="third_config对象", description="third_config")
public class ThirdConfig implements Serializable {
    private static final long serialVersionUID = 1L;

	/**id*/
	@TableId(type = IdType.ASSIGN_ID)
    @ApiModelProperty(value = "id")
    private java.lang.Integer id;
	/**appid*/
	@Excel(name = "appid", width = 15)
    @ApiModelProperty(value = "appid")
    private java.lang.String appid;
	/**appsecret*/
	@Excel(name = "appsecret", width = 15)
    @ApiModelProperty(value = "appsecret")
    private java.lang.String secret;
	/**租户id*/
	@Excel(name = "租户id", width = 15)
    @ApiModelProperty(value = "租户id")
    private java.lang.String tanentaId;
	/**用户授权的作用域，使用逗号（,）分隔*/
	@Excel(name = "用户授权的作用域，使用逗号（,）分隔", width = 15)
    @ApiModelProperty(value = "用户授权的作用域，使用逗号（,）分隔")
    private java.lang.String scope;
	/**类型 1微信 2支付宝 3微博 */
	@Excel(name = "类型 1微信 2支付宝 3微博 ", width = 15)
    @ApiModelProperty(value = "类型 1微信 2支付宝 3微博 ")
    private java.lang.Integer type;
	/**0删除1正常*/
	@Excel(name = "0删除1正常", width = 15)
    @ApiModelProperty(value = "0删除1正常")
    private java.lang.Integer state;
	/**创建时间*/
    @ApiModelProperty(value = "创建时间")
    private java.util.Date createTime;
	/**更新时间*/
    @ApiModelProperty(value = "更新时间")
    private java.util.Date updateTime;
}
