package org.jeecg.modules.user.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.jeecg.modules.user.entity.UserAccount;

/**
 * @Description: user_account
 * @Author: jeecg-boot
 * @Date:   2020-11-27
 * @Version: V1.0
 */
public interface UserAccountMapper extends BaseMapper<UserAccount> {

    String getUserMaxAccountId();
}
