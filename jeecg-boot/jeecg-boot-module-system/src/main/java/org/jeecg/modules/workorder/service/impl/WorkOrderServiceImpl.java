package org.jeecg.modules.workorder.service.impl;

import org.jeecg.common.constant.FillRuleConstant;
import org.jeecg.common.util.FillRuleUtil;
import org.jeecg.modules.workorder.entity.WorkOrder;
import org.jeecg.modules.workorder.mapper.WorkOrderMapper;
import org.jeecg.modules.workorder.service.IWorkOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

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

    @Override
    public void addWorkOrder(WorkOrder workOrder) {

        String code = (String) FillRuleUtil.executeRule(FillRuleConstant.WORK_ORDER, null);
        workOrder.setWorkCode(code);
        workOrderMapper.insert(workOrder);
    }
}
