package org.jeecg.modules.user.service;

import cn.binarywang.wx.miniapp.bean.WxMaUserInfo;
import org.jeecg.modules.user.entity.ThirdAuth;
import org.jeecg.modules.user.entity.UserAccount;

public interface IUserService {

    /**
     * 创建会员账号he第三方信息
     * @param userAccount
     */
   void createUserThird(UserAccount userAccount, ThirdAuth thirdAuth);


    /**
     * 更新微信用户信息到user_account表
     *
     * @param wxMaUserInfo
     * @param userId
     * @return
     */
    boolean updateUser(WxMaUserInfo wxMaUserInfo, String userId);

    /**
     * 更新手机信息到user_account表
     *
     * @param mobile
     * @param userId
     * @return
     */
    boolean updateMobileToUser(String mobile, String userId);
}
