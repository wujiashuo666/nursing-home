package com.neusoft.controller;

import java.util.List;
import java.util.Map;

import com.neusoft.entity.CustomerNurseItem;
import com.neusoft.service.ICustomerNurseItemService;
import com.neusoft.service.Impl.ICustomerNurseItemServiceImpl;

/**
 * 客户护理项目控制层
 */
public class CustomerNurseItemController {
    private ICustomerNurseItemService customerNurseItemService = new ICustomerNurseItemServiceImpl();

    /** 
     * 查询全部客户护理项目 
    */
    public List<Map<String, Object>> list() {
        return customerNurseItemService.list();
    }

    /** 
     * 根据客户id查询其分配的所有护理项目 
    */
    public List<Map<String, Object>> getByCustomerId(Integer customerId) {
        return customerNurseItemService.getByCustomerId(customerId);
    }

    /** 
     * 新增分配护理项目 
    */
    public boolean add(CustomerNurseItem customerNurseItem) {
        customerNurseItem.setIsDeleted(0);
        return customerNurseItemService.add(customerNurseItem);
    }

    /** 
     * 修改分配护理项目 
    */
    public boolean update(CustomerNurseItem customerNurseItem) {
        return customerNurseItemService.update(customerNurseItem);
    }

    /** 
     * 逻辑删除 
    */
    public boolean delete(Long id) {
        return customerNurseItemService.delete(id);
    }

    /** 
     * 根据ID查单条 
    */
    public CustomerNurseItem getById(Long id) {
        return customerNurseItemService.getById(id);
    }
}
