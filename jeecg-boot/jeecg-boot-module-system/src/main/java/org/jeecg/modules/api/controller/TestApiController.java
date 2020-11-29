package org.jeecg.modules.api.controller;

import lombok.extern.slf4j.Slf4j;
import org.jeecg.common.api.vo.Result;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Copyright (C), 2019-2020, XXX有限公司
 * FileName: TestApiController
 * Author:   kehaojian
 * Date:     2020/11/26 14:46
 * Description:
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
@RestController
@RequestMapping("/api")
@Slf4j
public class TestApiController {

    @GetMapping(value = "/test")
    public Result<?> queryPageList() {
        return Result.OK();
    }
}