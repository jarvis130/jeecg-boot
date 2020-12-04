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

/**
 * @Description: third_auth
 * @Author: jeecg-boot
 * @Date:   2020-12-03
 * @Version: V1.0
 */
@Data
@TableName("third_auth")
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@ApiModel(value="third_auth对象", description="third_auth")
public class ThirdAuth implements Serializable {
    private static final long serialVersionUID = 1L;

	/**id*/
	@TableId(type = IdType.ASSIGN_ID)
    @ApiModelProperty(value = "id")
    private java.lang.String id;
	/**用户ID*/
	@Excel(name = "用户ID", width = 15)
    @ApiModelProperty(value = "用户ID")
    private java.lang.String userId;
	/**接口调用凭证*/
	@Excel(name = "接口调用凭证", width = 15)
    @ApiModelProperty(value = "接口调用凭证")
    private java.lang.String accessToken;
	/**access_token接口调用凭证超时时间，单位（秒）*/
	@Excel(name = "access_token接口调用凭证超时时间，单位（秒）", width = 15)
    @ApiModelProperty(value = "access_token接口调用凭证超时时间，单位（秒）")
    private java.lang.Integer expiresIn;
	/**用户刷新access_token*/
	@Excel(name = "用户刷新access_token", width = 15)
    @ApiModelProperty(value = "用户刷新access_token")
    private java.lang.String refreshToken;
	/**授权用户唯一标识*/
	@Excel(name = "授权用户唯一标识", width = 15)
    @ApiModelProperty(value = "授权用户唯一标识")
    private java.lang.String openid;
	/**用户授权的作用域，使用逗号（,）分隔*/
	@Excel(name = "用户授权的作用域，使用逗号（,）分隔", width = 15)
    @ApiModelProperty(value = "用户授权的作用域，使用逗号（,）分隔")
    private java.lang.String scope;
	/**类型 1微信 2微博*/
	@Excel(name = "类型 1微信 2微博", width = 15)
    @ApiModelProperty(value = "类型 1微信 2微博")
    private java.lang.Integer type;
	/**当且仅当该网站应用已获得该用户的userinfo授权时，才会出现该字段。*/
	@Excel(name = "当且仅当该网站应用已获得该用户的userinfo授权时，才会出现该字段。", width = 15)
    @ApiModelProperty(value = "当且仅当该网站应用已获得该用户的userinfo授权时，才会出现该字段。")
    private java.lang.String unionid;
	/**租户ID*/
	@Excel(name = "租户ID", width = 15)
    @ApiModelProperty(value = "租户ID")
    private java.lang.String tanentId;
	/**创建时间*/
    @ApiModelProperty(value = "创建时间")
    private java.util.Date createTime;
	/**更新时间*/
    @ApiModelProperty(value = "更新时间")
    private java.util.Date updateTime;
}
