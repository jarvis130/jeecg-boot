package org.jeecg.modules.comment.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import org.jeecg.common.constant.enums.StatusEnum;
import org.jeecg.common.exception.JeecgBootException;
import org.jeecg.modules.comment.entity.GoodsComment;
import org.jeecg.modules.comment.service.ICommentService;
import org.jeecg.modules.comment.service.IGoodsCommentService;
import org.jeecg.modules.order.entity.OrderInfo;
import org.jeecg.modules.order.service.IOrderInfoService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;


@Service
public class CommentSerivceImpl implements ICommentService {

    @Resource
    private IOrderInfoService orderInfoService;

    @Resource
    private IGoodsCommentService goodsCommentService;

    @Transactional(rollbackFor = Exception.class)
    @Override
    public boolean save(GoodsComment goodsComment) {
        OrderInfo orderInfo = orderInfoService.getOne(new QueryWrapper<OrderInfo>().eq("id", goodsComment.getOrderId()));
        if (orderInfo == null) {
            throw new JeecgBootException("订单不存在！");
        }
        if (StatusEnum.SUCCESS.getType() == orderInfo.getEvaluationState()) {
            throw new JeecgBootException("订单已经评价");
        }
        goodsCommentService.save(goodsComment);
        OrderInfo order = new OrderInfo();
        order.setEvaluationState(StatusEnum.SUCCESS.getType());
        return orderInfoService.update(order, new UpdateWrapper<OrderInfo>().eq("id", goodsComment.getOrderId()));
    }
}