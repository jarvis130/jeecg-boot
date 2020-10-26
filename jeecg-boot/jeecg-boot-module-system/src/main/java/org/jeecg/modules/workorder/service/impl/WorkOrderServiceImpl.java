package org.jeecg.modules.workorder.service.impl;

import org.apache.shiro.SecurityUtils;
import org.jeecg.common.constant.FillRuleConstant;
import org.jeecg.common.system.api.ISysBaseAPI;
import org.jeecg.common.system.vo.LoginUser;
import org.jeecg.common.system.vo.SysUserCacheInfo;
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
    private ISysBaseAPI sysBaseAPI;

    @Autowired
    private WorkOrderMapper workOrderMapper;

    @Autowired
    private WorkOrderReplyMapper workOrderReplyMapper;

    @Override
    @Transactional
    public void addWorkOrder(WorkOrder workOrder) {

        LoginUser sysUser = (LoginUser) SecurityUtils.getSubject().getPrincipal();//当前操作用户
        String orgId = sysUser.getDepartIds();
        String code = (String) FillRuleUtil.executeRule(FillRuleConstant.WORK_ORDER, null);
        workOrder.setWorkCode(code);
        workOrder.setHandleDept(orgId);
        workOrder.setStatus(WorkOrderStatusConstant.ASSIGNED);
        workOrderMapper.insert(workOrder);
        //插入回复内容
        String content = "";

        SysUserCacheInfo userCacheInfo = sysBaseAPI.getCacheUser(workOrder.getHandleMaster());//工单master信息
        if(userCacheInfo != null){
            String masterName = userCacheInfo.getSysUserName();
            String orgName = userCacheInfo.getSysOrgCode();
            content = sysUser.getRealname() + "将工单分配给" + orgName + "," + masterName;
        }
        WorkOrderReply workOrderReply = new WorkOrderReply();
        workOrderReply.setWorkOrderId(workOrder.getId());
        workOrderReply.setContent(content);
        workOrderReplyMapper.insert(workOrderReply);

        return;
    }
}
