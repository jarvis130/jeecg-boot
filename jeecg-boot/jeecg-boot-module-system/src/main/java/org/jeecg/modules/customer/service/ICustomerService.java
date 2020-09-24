package org.jeecg.modules.customer.service;

import org.jeecg.modules.customer.entity.Customer;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * @Description: crm_customer
 * @Author: jeecg-boot
 * @Date:   2020-09-07
 * @Version: V1.0
 */
public interface ICustomerService extends IService<Customer> {

    /**
     * 保存客户信息
     * @param customer
     */
    public void saveCustomer(Customer customer);

    /**
     * 更新客户信息
     * @param customer
     */
    public void updateCustomer(Customer customer);
}
