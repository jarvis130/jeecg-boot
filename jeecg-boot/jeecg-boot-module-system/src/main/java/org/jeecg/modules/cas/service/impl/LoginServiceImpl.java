package org.jeecg.modules.cas.service.impl;

import cn.binarywang.wx.miniapp.bean.WxMaJscode2SessionResult;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import org.jeecg.common.util.PasswordUtil;
import org.jeecg.modules.cas.entity.UserLoginVo;
import org.jeecg.modules.cas.service.LoginService;
import org.jeecg.modules.user.entity.ThirdAuth;
import org.jeecg.modules.user.entity.UserAccount;
import org.jeecg.modules.user.service.IThirdAuthService;
import org.jeecg.modules.user.service.IUserAccountService;
import org.jeecg.modules.user.service.IUserService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

    @Resource
    private IThirdAuthService authService;

    @Resource
    private IUserService userService;

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
    @Transactional(rollbackFor = Exception.class)
    @Override
    public UserAccount loginByMini(WxMaJscode2SessionResult sessionResult, String tanentId){
        UserAccount userAccount = new UserAccount();
        if (sessionResult != null ) {
            String openId = sessionResult.getOpenid();
            ThirdAuth temp = authService.getOne(new QueryWrapper<ThirdAuth>().eq("openid", openId));
            ThirdAuth thirdAuth = new ThirdAuth();
            thirdAuth.setAccessToken(sessionResult.getSessionKey());
            if (temp != null) {
                userAccount.setId(temp.getUserId());
                authService.update(thirdAuth, new UpdateWrapper<ThirdAuth>().eq("openid", openId));
            } else {
                userAccount.setUserName(openId);
                thirdAuth.setOpenid(openId);
                thirdAuth.setTanentId(tanentId);
                thirdAuth.setUnionid(sessionResult.getUnionid());
                userService.createUserThird(userAccount, thirdAuth);
            }
        }
        return userAccount;
    }
}