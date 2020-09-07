package org.jeecg.modules.user.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Param;
import org.jeecg.modules.user.entity.UserAccount;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
 * @Description: user_account
 * @Author: jeecg-boot
 * @Date:   2020-08-30
 * @Version: V1.0
 */
public interface UserAccountMapper extends BaseMapper<UserAccount> {

    /**
     *  获取会员账号最大值
     * @return
     */
    public Integer getUserMaxAccountId();

    /**
     *  根据手机号获取会员账号信息
     * @return
     */
    public UserAccount selectUserAccountByMobile(String mobile);
}
