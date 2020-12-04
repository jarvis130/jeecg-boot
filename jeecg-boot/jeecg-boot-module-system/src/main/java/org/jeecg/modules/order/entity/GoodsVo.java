package org.jeecg.modules.order.entity;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * Copyright (C), 2019-2020, XXX有限公司
 * FileName: GoodsVo
 * Author:   kehaojian
 * Date:     2020/12/3 10:20
 * Description:
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
@Data
public class GoodsVo {
    @ApiModelProperty(value = "商品id")
    private java.lang.String goodsId;

    /**商品数量*/
    @ApiModelProperty(value = "商品数量")
    private java.lang.Integer goodsCount;
}