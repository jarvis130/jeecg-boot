package org.jeecg.modules.cas.service;

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

    UserAccount loginByUserName(UserLoginVo userLoginVo);


}