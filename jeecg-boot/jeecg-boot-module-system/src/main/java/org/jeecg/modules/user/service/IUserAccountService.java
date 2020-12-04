package org.jeecg.modules.user.service;

import org.jeecg.modules.commodity.entity.SpuDetail;
import org.jeecg.modules.user.entity.UserAccount;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;
import java.util.Map;

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
   void createUserAccount(UserAccount userAccount);

    /**
     *  根据手机号获取会员账号信息
     * @return
     */
    UserAccount getUserAccountByMobile(String mobile);

    UserAccount getUserAccountByUserName(String userName);

    public Map<String, UserAccount> getUserAccountByIds(List<String> ids);

}
