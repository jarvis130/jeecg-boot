package org.jeecg.modules.workorder.service;

import org.jeecg.modules.workorder.entity.WorkOrder;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * @Description: work_order
 * @Author: jeecg-boot
 * @Date:   2020-09-30
 * @Version: V1.0
 */
public interface IWorkOrderService extends IService<WorkOrder> {

    public void addWorkOrder(WorkOrder workOrder);
}
