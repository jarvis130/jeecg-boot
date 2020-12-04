package org.jeecg.modules.order.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import org.jeecg.modules.order.entity.ApiOrderVo;
import org.jeecg.modules.order.entity.ApiQueryOrderVo;
import org.jeecg.modules.order.entity.ApiUpdateOrderVo;
import org.jeecg.modules.order.entity.GoodsVo;
import org.jeecg.modules.order.entity.OrderDto;
import org.jeecg.modules.order.service.IOrderExtendService;
import org.jeecg.modules.order.service.IOrderGoodsService;
import org.jeecg.modules.order.service.IOrderInfoService;
import org.jeecg.modules.order.service.IOrderPayService;
import org.jeecg.modules.order.service.IOrderService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class OrderServiceImpl implements IOrderService {

    @Resource
    private IOrderInfoService orderInfoService;
    @Resource
    private IOrderGoodsService orderGoodsService;
    @Resource
    private IOrderPayService orderPayService;
    @Resource
    private IOrderExtendService orderExtendService;

    @Transactional(rollbackFor = Exception.class)
    @Override
    public boolean createOrder(ApiOrderVo orderVo) {
        List<String> goodsIds = orderVo.getGoodsVos().stream().map(GoodsVo::getGoodsId).collect(Collectors.toList());
//        QueryWrapper<>
//        orderGoodsService.getBaseMapper().selectList()
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