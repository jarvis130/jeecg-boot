package org.jeecg.modules.cas.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.jeecg.common.api.vo.Result;
import org.jeecg.modules.cas.service.SmsService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * Copyright (C), 2019-2020, XXX有限公司
 * FileName: AppLoginController
 * Author:   kehaojian
 * Date:     2020/11/26 15:10
 * Description:
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
@RestController
@RequestMapping("/api")
@Api("短信发送接口")
public class SmsController {

    private Logger log = LoggerFactory.getLogger(getClass());

    @Resource
    private SmsService smsService;

    /**
     * 短信发送
     */
    @PostMapping("sendSms")
    @ApiOperation("短信发送")
    public Result<?> sendSms(@RequestBody String mobile) {
        try {
            if ( smsService.sendSms(mobile)){
                return Result.OK("短信已经发送");
            }
        } catch (Exception e) {
            log.error("手机号：{},短信验证码发送失败：{}", mobile, e);
        }
        return Result.error(500, "短信发送失败，请重试");
    }


}