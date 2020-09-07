package org.jeecg.modules.user.service;

import org.jeecg.modules.user.entity.UserAccount;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * @Description: user_account
 * @Author: jeecg-boot
 * @Date:   2020-08-30
 * @Version: V1.0
 */
public interface IUserAccountService extends IService<UserAccount> {

    /**
     * 创建会员账号
     * @param userAccount
     */
    public void createUserAccount(UserAccount userAccount);

    /**
     *  根据手机号获取会员账号信息
     * @return
     */
    public UserAccount getUserAccountByMobile(String mobile);
}
