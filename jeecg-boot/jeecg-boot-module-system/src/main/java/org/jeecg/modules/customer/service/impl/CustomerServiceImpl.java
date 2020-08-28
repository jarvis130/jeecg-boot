package org.jeecg.modules.customer.service.impl;

import org.jeecg.modules.customer.entity.Customer;
import org.jeecg.modules.customer.mapper.CustomerMapper;
import org.jeecg.modules.customer.service.ICustomerService;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

/**
 * @Description: crm_customer
 * @Author: jeecg-boot
 * @Date:   2020-08-28
 * @Version: V1.0
 */
@Service
public class CustomerServiceImpl extends ServiceImpl<CustomerMapper, Customer> implements ICustomerService {

}
