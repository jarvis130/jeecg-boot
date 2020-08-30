package org.jeecg.modules.user.service.impl;

import org.jeecg.modules.user.entity.UserAccount;
import org.jeecg.modules.user.mapper.UserAccountMapper;
import org.jeecg.modules.user.service.IUserAccountService;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

/**
 * @Description: user_account
 * @Author: jeecg-boot
 * @Date:   2020-08-30
 * @Version: V1.0
 */
@Service
public class UserAccountServiceImpl extends ServiceImpl<UserAccountMapper, UserAccount> implements IUserAccountService {

}
