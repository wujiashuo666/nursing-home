package com.neusoft.service.Impl;

import java.util.List;
import java.util.Map;

import com.neusoft.dao.CustomerNurseItemDao;
import com.neusoft.dao.impl.CustomerNurseItemDaoImpl;
import com.neusoft.entity.CustomerNurseItem;
import com.neusoft.service.ICustomerNurseItemService;

public class ICustomerNurseItemServiceImpl implements ICustomerNurseItemService {

    private CustomerNurseItemDao customerNurseItemDao = new CustomerNurseItemDaoImpl();

    @Override
    public List<Map<String, Object>> list() {
        return customerNurseItemDao.list();
    }

    @Override
    public List<Map<String, Object>> getByCustomerId(Integer customerId) {
        return customerNurseItemDao.getByCustomerId(customerId);
    }

    @Override
    public boolean add(CustomerNurseItem customerNurseItem) {
        return customerNurseItemDao.add(customerNurseItem);
    }

    @Override
    public boolean update(CustomerNurseItem customerNurseItem) {
        return customerNurseItemDao.update(customerNurseItem);
    }

    @Override
    public boolean delete(Long id) {
        return customerNurseItemDao.delete(id);
    }

    @Override
    public CustomerNurseItem getById(Long id) {
        return customerNurseItemDao.getById(id);
    }
}
