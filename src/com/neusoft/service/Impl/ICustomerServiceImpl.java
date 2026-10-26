package com.neusoft.service.Impl;

import java.util.List;

import com.neusoft.dao.CustomerDao;
import com.neusoft.dao.impl.CustomerDaoImpl;
import com.neusoft.entity.Customer;
import com.neusoft.service.ICustomerService;

public class ICustomerServiceImpl implements ICustomerService {

    private CustomerDao customerDao = new CustomerDaoImpl();

    @Override
    public List<Customer> listServedByGuard(int guardId, String customerName) {
        return customerDao.listServedByGuard(guardId, customerName);
    }

    @Override
    public Customer getById(Long id) {
        return customerDao.getById(id);
    }

    @Override
    public String getGuardNickname(int userId) {
        return customerDao.getGuardNickname(userId);
    }

    // ==================== 管理端方法实现 ====================

    @Override
    public boolean addCustomer(Customer customer) {
        return customerDao.addCustomer(customer) > 0;
    }

    @Override
    public boolean updateCustomer(Customer customer) {
        return customerDao.updateCustomer(customer) > 0;
    }

    @Override
    public boolean logicDeleteCustomer(Long id) {
        return customerDao.logicDeleteCustomer(id) > 0;
    }

    @Override
    public List<Customer> findCustomerPage(String customerName, Integer levelId, int pageIndex, int pageSize) {
        return customerDao.findCustomerPage(customerName, levelId, pageIndex, pageSize);
    }

    @Override
    public long getCustomerTotal(String customerName, Integer levelId) {
        return customerDao.getCustomerTotal(customerName, levelId);
    }
}
