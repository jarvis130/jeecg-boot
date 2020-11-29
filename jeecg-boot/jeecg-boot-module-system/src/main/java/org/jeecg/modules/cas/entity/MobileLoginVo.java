/**
 * Copyright (c) 2016-2019 学嘉科技 All rights reserved.
 *
 * https://www.xuejiashopping.com
 *
 * 版权所有，侵权必究！
 */

package org.jeecg.modules.cas.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * 登录表单
 *
 * @author Mark sunlightcs@gmail.com
 */
@Data
@ApiModel(value = "登录表单")
public class MobileLoginVo {
    @ApiModelProperty(value = "手机号")
    @NotBlank(message="手机号不能为空")
    private String mobile;

    @ApiModelProperty(value = "验证码")
    @NotBlank(message="验证码不能为空")
    private String code;

}
