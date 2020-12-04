package org.jeecg.modules.cas.service;

import cn.binarywang.wx.miniapp.bean.WxMaJscode2SessionResult;
import org.jeecg.modules.cas.entity.UserLoginVo;
import org.jeecg.modules.user.entity.UserAccount;

/**
 * Copyright (C), 2019-2020, XXX有限公司
 * FileName: LoginService
 * Author:   kehaojian
 * Date:     2020/11/26 15:59
 * Description:
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
public interface LoginService {

    /**
     * 用户名登录
     *
     * @param userLoginVo
     * @return
     */
    UserAccount loginByUserName(UserLoginVo userLoginVo);

    /**
     * 小程序登录
     *
     * @param sessionResult
     * @param tanentId
     * @return
     */
    UserAccount loginByMini(WxMaJscode2SessionResult sessionResult, String tanentId);


}