package org.jeecg.modules.cas.service.impl;

import org.jeecg.common.util.PasswordUtil;
import org.jeecg.modules.cas.entity.UserLoginVo;
import org.jeecg.modules.cas.service.LoginService;
import org.jeecg.modules.user.entity.UserAccount;
import org.jeecg.modules.user.service.IUserAccountService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * Copyright (C), 2019-2020, XXX有限公司
 * FileName: LoginServiceImpl
 * Author:   kehaojian
 * Date:     2020/11/26 16:00
 * Description:
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
@Service
public class LoginServiceImpl implements LoginService {
    @Resource
    private IUserAccountService accountService;

    @Override
    public UserAccount loginByUserName(UserLoginVo userLoginVo){
        UserAccount userAccount = accountService.getUserAccountByUserName(userLoginVo.getUserName());
        if (userAccount != null) {
            String userpassword = PasswordUtil.encrypt(userLoginVo.getUserName(), userLoginVo.getPassword(), userAccount.getSalt());
            String syspassword = userAccount.getPassword();
            if (!syspassword.equals(userpassword)) {
                userAccount = null;
            }
        }
        return userAccount;
    }
}