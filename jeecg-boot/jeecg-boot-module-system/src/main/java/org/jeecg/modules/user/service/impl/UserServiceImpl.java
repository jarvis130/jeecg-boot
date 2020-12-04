package org.jeecg.modules.user.service.impl;

import cn.binarywang.wx.miniapp.bean.WxMaUserInfo;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import org.apache.commons.lang.StringUtils;
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
 * FileName: UserServiceImpl
 * Author:   kehaojian
 * Date:     2020/12/3 18:04
 * Description:
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
@Service
public class UserServiceImpl implements IUserService {

    @Resource
    private IUserAccountService accountService;

    @Resource
    private IThirdAuthService authService;

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void createUserThird(UserAccount userAccount, ThirdAuth thirdAuth) {
        accountService.createUserAccount(userAccount);
        thirdAuth.setUserId(userAccount.getId());
        authService.save(thirdAuth);
    }

    @Override
    public boolean updateUser(WxMaUserInfo wxMaUserInfo, String userId) {
        UserAccount userAccount = new UserAccount();
        userAccount.setNickname(wxMaUserInfo.getNickName());
        userAccount.setSex(StringUtils.isNotBlank(wxMaUserInfo.getGender()) ? Integer.parseInt(wxMaUserInfo.getGender()) : 2);
        return accountService.update(userAccount, new UpdateWrapper<UserAccount>().eq("id", userId));
    }

    @Override
    public boolean updateMobileToUser(String mobile, String userId) {
        UserAccount userAccount = new UserAccount();
        userAccount.setMobile(mobile);
        userAccount.setUserName(mobile);
        return accountService.update(userAccount, new UpdateWrapper<UserAccount>().eq("id", userId));
    }
}