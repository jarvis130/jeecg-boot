package org.jeecg.modules.workorder.service.impl;

import org.jeecg.common.constant.FillRuleConstant;
import org.jeecg.common.util.FillRuleUtil;
import org.jeecg.modules.workorder.constant.WorkOrderStatusConstant;
import org.jeecg.modules.workorder.entity.WorkOrder;
import org.jeecg.modules.workorder.entity.WorkOrderReply;
import org.jeecg.modules.workorder.mapper.WorkOrderMapper;
import org.jeecg.modules.workorder.mapper.WorkOrderReplyMapper;
import org.jeecg.modules.workorder.service.IWorkOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.transaction.annotation.Transactional;

/**
 * @Description: work_order
 * @Author: jeecg-boot
 * @Date:   2020-09-30
 * @Version: V1.0
 */
@Service
public class WorkOrderServiceImpl extends ServiceImpl<WorkOrderMapper, WorkOrder> implements IWorkOrderService {

    @Autowired
    private WorkOrderMapper workOrderMapper;

    @Autowired
    private WorkOrderReplyMapper workOrderReplyMapper;

    @Override
    @Transactional
    public void addWorkOrder(WorkOrder workOrder) {

        String code = (String) FillRuleUtil.executeRule(FillRuleConstant.WORK_ORDER, null);
        workOrder.setWorkCode(code);
        workOrder.setStatus(WorkOrderStatusConstant.ASSIGNED);
        workOrderMapper.insert(workOrder);

        WorkOrderReply workOrderReply = new WorkOrderReply();
        workOrderReply.setWorkOrderId(workOrder.getId());
        String content = "将工单分配给" + workOrder.getHandleMaster();
        workOrderReply.setContent(content);
        workOrderReplyMapper.insert(workOrderReply);

        return;
    }
}
