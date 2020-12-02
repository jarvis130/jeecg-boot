package org.jeecg.modules.order.service.impl;

import org.jeecg.modules.order.entity.OrderInfo;
import org.jeecg.modules.order.mapper.OrderInfoMapper;
import org.jeecg.modules.order.service.IOrderInfoService;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

/**
 * @Description: order_info
 * @Author: jeecg-boot
 * @Date:   2020-12-02
 * @Version: V1.0
 */
@Service
public class OrderInfoServiceImpl extends ServiceImpl<OrderInfoMapper, OrderInfo> implements IOrderInfoService {

}
