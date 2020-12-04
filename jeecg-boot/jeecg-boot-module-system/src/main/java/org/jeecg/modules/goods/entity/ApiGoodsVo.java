package org.jeecg.modules.goods.entity;

import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * Copyright (C), 2019-2020, XXX有限公司
 * FileName: ApiGoodsVo
 * Author:   kehaojian
 * Date:     2020/12/1 13:42
 * Description:
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
@Data
public class ApiGoodsVo {
    @NotNull
    private String appId;
    @NotNull
    private String type;

    private Integer pageSize = 10 ;

    private Integer pageNum = 1;

}