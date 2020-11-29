package org.jeecg.modules.cas.entity;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
 * Copyright (C), 2019-2020, XXX有限公司
 * FileName: UserInfoDto
 * Author:   kehaojian
 * Date:     2020/11/26 15:35
 * Description:
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */

@Data
public class UserInfoDto {
    private String id;
    
    private String userName;

    private String realName;

    private String nickname;

    private String mobile;

    private String email;

    private Integer sex;

    private Date birthday;

    private BigDecimal userMoney;

    private BigDecimal frozenMoney;

    private Integer payPoints;

    private Integer rankPoints;

    private Integer addressId;

    private java.util.Date lastLogin;

    private String lastIp;

    private Integer loginCount;

    private Integer userRank;

    private Integer status;

    private Date createTime;
    
    private String token;

    private Long expireTime;
}