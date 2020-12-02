package org.jeecg.modules.order.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import org.jeecg.modules.order.entity.ApiOrderVo;
import org.jeecg.modules.order.entity.ApiQueryOrderVo;
import org.jeecg.modules.order.entity.ApiUpdateOrderVo;
import org.jeecg.modules.order.entity.OrderDto;
import org.jeecg.modules.order.service.IOrderService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Copyright (C), 2019-2020, XXX有限公司
 * FileName: OrderServiceImpl
 * Author:   kehaojian
 * Date:     2020/12/2 21:39
 * Description:
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
@Service
public class OrderServiceImpl implements IOrderService {

    @Transactional(rollbackFor = Exception.class)
    @Override
    public boolean createOrder(ApiOrderVo orderVo) {
        return false;
    }

    @Override
    public IPage<OrderDto> queryOrderList(ApiQueryOrderVo queryOrderVo) {
        return null;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public boolean updateOrder(ApiUpdateOrderVo orderVo) {
        return false;
    }
}