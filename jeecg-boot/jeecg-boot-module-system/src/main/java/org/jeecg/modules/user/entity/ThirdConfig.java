package org.jeecg.modules.user.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import org.jeecgframework.poi.excel.annotation.Excel;

import java.io.Serializable;
import java.util.Date;

/**
 * @Description: third_config
 * @Author: jeecg-boot
 * @Date:   2020-12-04
 * @Version: V1.0
 */
@Data
@TableName("third_config")
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@ApiModel(value="third_config对象", description="third_config")
public class ThirdConfig implements Serializable {
    private static final long serialVersionUID = 1L;

	/**主键*/
	@TableId(type = IdType.ASSIGN_ID)
    @ApiModelProperty(value = "主键")
    private String id;
	/**appid*/
	@Excel(name = "appid", width = 15)
    @ApiModelProperty(value = "appid")
    private String appid;
	/**appsecret*/
	@Excel(name = "appsecret", width = 15)
    @ApiModelProperty(value = "appsecret")
    private String secret;
	/**租户id*/
	@Excel(name = "租户id", width = 15)
    @ApiModelProperty(value = "租户id")
    private String tanentaId;
	/**商户ID*/
	@Excel(name = "商户ID", width = 15)
    @ApiModelProperty(value = "商户ID")
    private String mchId;
	/**支付密钥*/
	@Excel(name = "支付密钥", width = 15)
    @ApiModelProperty(value = "支付密钥")
    private String mchKey;
	/**用户授权的作用域，使用逗号（,）分隔*/
	@Excel(name = "用户授权的作用域，使用逗号（,）分隔", width = 15)
    @ApiModelProperty(value = "用户授权的作用域，使用逗号（,）分隔")
    private String scope;
	/**类型 1微信 2支付宝 3微博 */
	@Excel(name = "类型 1微信 2支付宝 3微博 ", width = 15)
    @ApiModelProperty(value = "类型 1微信 2支付宝 3微博 ")
    private Integer type;
	/**p12证书文件的绝对路径或者以classpath:开头的类路径*/
	@Excel(name = "p12证书文件的绝对路径或者以classpath:开头的类路径", width = 15)
    @ApiModelProperty(value = "p12证书文件的绝对路径或者以classpath:开头的类路径")
    private String keypath;
	/**apiclient_key.pem证书文件的绝对路径或者以classpath:开头的类路径*/
	@Excel(name = "apiclient_key.pem证书文件的绝对路径或者以classpath:开头的类路径", width = 15)
    @ApiModelProperty(value = "apiclient_key.pem证书文件的绝对路径或者以classpath:开头的类路径")
    private String privateKeyPath;
	/**apiclient_cert.pem证书文件的绝对路径或者以classpath:开头的类路径*/
	@Excel(name = "apiclient_cert.pem证书文件的绝对路径或者以classpath:开头的类路径", width = 15)
    @ApiModelProperty(value = "apiclient_cert.pem证书文件的绝对路径或者以classpath:开头的类路径")
    private String privateKertPath;
	/**异步通知url*/
	@Excel(name = "异步通知url", width = 15)
    @ApiModelProperty(value = "异步通知url")
    private String notifyUrl;
	/**状态：0删除1正常*/
	@Excel(name = "状态：0删除1正常", width = 15)
    @ApiModelProperty(value = "状态：0删除1正常")
    private Integer state;
	/**创建时间*/
    @ApiModelProperty(value = "创建时间")
    private Date createTime;
	/**更新时间*/
    @ApiModelProperty(value = "更新时间")
    private Date updateTime;
}
