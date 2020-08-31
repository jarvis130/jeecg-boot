package org.jeecg.modules.user.service.impl;

import org.jeecg.modules.user.entity.UserAccount;
import org.jeecg.modules.user.mapper.UserAccountMapper;
import org.jeecg.modules.user.service.IUserAccountService;
import org.springframework.beans.factory.annotation.Autowired;
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

    @Autowired
    private UserAccountMapper userAccountMapper;

    /**
     * 创建会员账号
     * @param userAccount
     */
    @Override
    public void createUserAccount(UserAccount userAccount) {
        userAccountMapper.insert(userAccount);
    }
}
