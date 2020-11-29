package org.jeecg.modules.cas.service.impl;

import cn.hutool.core.util.RandomUtil;
import com.alibaba.fastjson.JSONObject;
import com.aliyuncs.exceptions.ClientException;
import org.apache.commons.lang.StringUtils;
import org.jeecg.common.exception.JeecgBootException;
import org.jeecg.common.util.DateNewUtil;
import org.jeecg.common.util.DySmsEnum;
import org.jeecg.common.util.DySmsHelper;
import org.jeecg.common.util.RedisUtil;
import org.jeecg.modules.cas.service.SmsService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * Copyright (C), 2019-2020, XXX有限公司
 * FileName: SmsServiceImpl
 * Author:   kehaojian
 * Date:     2020/11/29 9:08
 * Description:
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
@Service
public class SmsServiceImpl implements SmsService {
    private Logger log = LoggerFactory.getLogger(getClass());
    @Resource
    private RedisUtil redisUtil;

    @Override
    public Boolean sendSms(String mobile) throws Exception {
        String captcha = RandomUtil.randomNumbers(6);
        JSONObject obj = new JSONObject();
        obj.put("code", captcha);
        try {
            if (redisUtil.get(mobile) != null) {
                String tmp = redisUtil.get(mobile).toString();
                if (StringUtils.isNotBlank(tmp)) {
                    String[] tmpArray = tmp.split(":");
                    if (!DateNewUtil.compareTime(tmpArray[1], 60L)) {
                        throw new JeecgBootException("发送短信验证码过于频繁！");
                    }
                }
            }
            DySmsHelper.sendSms(mobile, obj, DySmsEnum.LOGIN_TEMPLATE_CODE);
            redisUtil.setEx(mobile, captcha + ":"+ System.currentTimeMillis(), 600);
            return true;
        } catch (ClientException e) {
            log.error("短信发送失败，请重试{}", e);
        }
        return false;
    }
}