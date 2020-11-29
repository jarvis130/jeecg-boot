package org.jeecg.modules.cas.service;

/**
 * Copyright (C), 2019-2020, XXX有限公司
 * FileName: SmsService
 * Author:   kehaojian
 * Date:     2020/11/29 9:07
 * Description:
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
public interface SmsService {
    Boolean sendSms(String mobile) throws Exception;
}