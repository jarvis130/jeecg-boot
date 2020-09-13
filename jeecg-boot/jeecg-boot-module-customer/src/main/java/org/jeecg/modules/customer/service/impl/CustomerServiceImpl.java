package org.jeecg.modules.customer.service.impl;

import com.sun.xml.internal.ws.handler.HandlerException;
import lombok.extern.slf4j.Slf4j;
import org.jeecg.common.api.vo.Result;
import org.jeecg.common.exception.JeecgBootException;
import org.jeecg.common.exception.JeecgBootExceptionHandler;
import org.jeecg.common.util.DateUtils;
import org.jeecg.modules.customer.entity.Customer;
import org.jeecg.modules.customer.mapper.CustomerMapper;
import org.jeecg.modules.customer.service.ICustomerService;
import org.jeecg.modules.user.entity.UserAccount;
import org.jeecg.modules.user.mapper.UserAccountMapper;
import org.jeecg.modules.user.service.IUserAccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.transaction.annotation.Transactional;

/**
 * @Description: crm_customer
 * @Author: jeecg-boot
 * @Date:   2020-09-07
 * @Version: V1.0
 */
@Service
@Slf4j
public class CustomerServiceImpl extends ServiceImpl<CustomerMapper, Customer> implements ICustomerService {

    @Autowired
    private IUserAccountService userAccountService;

    /**
     * 保存客户信息
     * @param customer
     */
    @Override
    @Transactional
    public void saveCustomer(Customer customer){

        //手机号校验
        UserAccount account = userAccountService.getUserAccountByMobile(customer.getCustomerMobile());
        if(account != null){
            throw new JeecgBootException("创建客户数据失败，手机号已经存在!");
        }
        //创建会员
        UserAccount userAccount = new UserAccount();
        userAccount.setUserName(customer.getCustomerMobile());
        userAccount.setMobile(customer.getCustomerMobile());
        userAccount.setRealName(customer.getCustomerName());
        userAccount.setStatus(customer.getStatus());
        userAccount.setCreateTime(DateUtils.getDate());
        userAccount.setUpdateTime(DateUtils.getDate());
        userAccountService.createUserAccount(userAccount);
        String userAccountId = userAccount.getId();
        //创建客户数据
        customer.setUserId(userAccountId);
        customer.setCreateTime(DateUtils.getDate());
        customer.setUpdateTime(DateUtils.getDate());
        this.save(customer);
    }

    /**
     * 更新客户信息
     * @param customer
     */
    @Override
    public void updateCustomer(Customer customer){
        UserAccount userAccount = new UserAccount();
        userAccount.setUserName(customer.getCustomerMobile());
        userAccount.setMobile(customer.getCustomerMobile());
        userAccount.setRealName(customer.getCustomerName());
        userAccount.setStatus(customer.getStatus());
        userAccount.setUpdateTime(DateUtils.getDate());
        userAccountService.updateById(userAccount);
        //创建客户数据
        customer.setUpdateTime(DateUtils.getDate());
        this.updateById(customer);
    }
}
