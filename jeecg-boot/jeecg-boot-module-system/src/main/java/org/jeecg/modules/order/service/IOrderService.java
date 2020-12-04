package org.jeecg.modules.order.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import org.jeecg.modules.order.entity.ApiOrderVo;
import org.jeecg.modules.order.entity.ApiQueryOrderVo;
import org.jeecg.modules.order.entity.ApiUpdateOrderVo;
import org.jeecg.modules.order.entity.OrderDto;

/**
 * Copyright (C), 2019-2020, XXX有限公司
 * FileName: IOrderService
 * Author:   kehaojian
 * Date:     2020/12/2 21:33
 * Description:
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
public interface IOrderService {

    /**
     * 创建订单
     *
     * @param orderVo
     * @return
     */
    boolean createOrder(ApiOrderVo orderVo);

    IPage<OrderDto> queryOrderList(ApiQueryOrderVo orderVo);

    boolean updateOrder(ApiUpdateOrderVo orderVo);
}