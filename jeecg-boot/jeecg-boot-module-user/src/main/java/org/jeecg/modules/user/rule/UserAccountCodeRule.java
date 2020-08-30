package org.jeecg.modules.user.rule;

import com.alibaba.fastjson.JSONObject;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.math.RandomUtils;
import org.jeecg.common.handler.IFillRuleHandler;
import org.jeecg.common.util.RedisUtil;
import org.jeecg.modules.user.mapper.UserAccountMapper;
import org.springframework.beans.factory.annotation.Autowired;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 用户编号生成规则
 * 从100000开始计数，优先从redis中取值（key=USER_ACCOUNT_CODE），redis不存在就从数据库中取最大值放到redis中
 */
public class UserAccountCodeRule implements IFillRuleHandler {

    @Autowired
    private RedisUtil redisUtil;

    @Autowired
    private UserAccountMapper userAccountMapper;

    private String gKey = "USER_ACCOUNT_CODE";

    @Override
    public Object execute(JSONObject params, JSONObject formData) {
        String prefix = "CN";
        //订单前缀默认为CN 如果规则参数不为空，则取自定义前缀
        if (params != null) {
            Object obj = params.get("prefix");
            if (obj != null) prefix = obj.toString();
        }
        SimpleDateFormat format = new SimpleDateFormat("yyyyMMddHHmmss");
        int random = RandomUtils.nextInt(90) + 10;
        String value = prefix + format.format(new Date()) + random;
        // 根据formData的值的不同，生成不同的订单号
        String name = formData.getString("name");
        if (!StringUtils.isEmpty(name)) {
            value += name;
        }
        return value;
    }

    /**
     * 生成用户编号
     * @return
     */
    public Integer createUserAccountCode(){
        Integer value = (Integer)redisUtil.get(gKey);
        if(value != null){
            value = value + 1;
        }else{
            //从数据库中获取最大值
            Integer iValue = userAccountMapper.getUserMaxId();
            value = iValue + 1;
            redisUtil.set(gKey, value);
        }
        return value;
    }
}
