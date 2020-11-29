package org.jeecg.modules.user.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.jeecg.common.util.DateUtils;
import org.jeecg.common.util.RedisUtil;
import org.jeecg.modules.user.entity.UserAccount;
import org.jeecg.modules.user.mapper.UserAccountMapper;
import org.jeecg.modules.user.service.IUserAccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import java.util.Date;

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

    @Autowired
    private RedisUtil redisUtil;

    private String gKey = "USER_ACCOUNT_CODE";

    /**
     * 生成用户编号
     * @return
     */
    private long createUserAccountCode(){
        long value = redisUtil.incr(gKey, 1L);
        return value;
    }

    /**
     * 创建会员账号
     * @param userAccount
     */
    @Override
    public void createUserAccount(UserAccount userAccount){
        try {
            userAccount.setUserName(userAccount.getMobile());
            userAccount.setCreateTime(DateUtils.getDate());
            userAccount.setUpdateTime(DateUtils.getDate());
            userAccountMapper.insert(userAccount);
        } catch (Exception e) {
            throw e;
        }
    }

    /**
     *  根据手机号获取会员账号信息
     * @return
     */
    @Override
    public UserAccount getUserAccountByMobile(String mobile){
        return userAccountMapper.selectOne(new QueryWrapper<UserAccount>().eq("mobile", mobile));
    }

    @Override
    public UserAccount getUserAccountByUserName(String userName) {
        return userAccountMapper.selectOne(new QueryWrapper<UserAccount>().eq("user_name", userName));
    }
}
