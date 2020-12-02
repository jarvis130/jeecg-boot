package org.jeecg.modules.order.entity;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.jeecgframework.poi.excel.annotation.Excel;

/**
 * Copyright (C), 2019-2020, XXX有限公司
 * FileName: ApiOrderVo
 * Author:   kehaojian
 * Date:     2020/12/2 8:56
 * Description:
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
@Data
public class ApiOrderVo {
    private String orderSn;
    /**买家用户id，同ecs_users的user_id*/
    @ApiModelProperty(value = "买家用户id，同ecs_users的user_id")
    private String buyerId;
    /**订单状态。0，未确认；1，已确认；2，已取消；3，无效；4，退货；*/
    @ApiModelProperty(value = "订单状态。0，未确认；1，已确认；2，已取消；3，无效；4，退货；")
    private Integer orderStatus;
    /**商品配送情况，0，未发货；1，已发货；2，已收货；3，备货中*/
    @ApiModelProperty(value = "商品配送情况，0，未发货；1，已发货；2，已收货；3，备货中")
    private Integer shippingStatus;
    /**支付状态；0，未付款；1，付款中；2，已付款*/
    @ApiModelProperty(value = "支付状态；0，未付款；1，付款中；2，已付款")
    private Integer payStatus;
    /**收货人的姓名，用户页面填写，默认取值于表user_address*/
    @ApiModelProperty(value = "收货人的姓名，用户页面填写，默认取值于表user_address")
    private String consignee;

    /**收货人的省份，用户页面填写，默认取值于表user_address，其id对应的值在ecs_region*/
    @ApiModelProperty(value = "收货人的省份，用户页面填写，默认取值于表user_address，其id对应的值在ecs_region")
    private Integer province;
    /**收货人的城市，用户页面填写，默认取值于表user_address，其id对应的值在ecs_region*/
    @ApiModelProperty(value = "收货人的城市，用户页面填写，默认取值于表user_address，其id对应的值在ecs_region")
    private Integer city;
    /**收货人的地区，用户页面填写，默认取值于表user_address，其id对应的值在ecs_region*/
    @ApiModelProperty(value = "收货人的地区，用户页面填写，默认取值于表user_address，其id对应的值在ecs_region")
    private Integer district;
    private String tel;
    /**收货人的手机，用户页面填写，默认取值于表user_address*/
    @ApiModelProperty(value = "收货人的手机，用户页面填写，默认取值于表user_address")
    private String mobile;
    /**用户选择的支付方式的名称，取值表ecs_payment*/
    @Excel(name = "用户选择的支付方式的名称，取值表ecs_payment", width = 15)
    @ApiModelProperty(value = "用户选择的支付方式的名称，取值表ecs_payment")
    private String payName;
}