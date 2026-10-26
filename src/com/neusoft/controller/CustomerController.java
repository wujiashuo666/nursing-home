package com.neusoft.controller;

import java.util.List;

import com.neusoft.entity.Customer;
import com.neusoft.service.ICustomerService;
import com.neusoft.service.Impl.ICustomerServiceImpl;

/**
 * 客户控制层
 */
public class CustomerController {
    private ICustomerService customerService = new ICustomerServiceImpl();

    /**
     * 查询某健康管家(user_id)服务的客户列表，可按客户姓名模糊过滤
     */
    public List<Customer> listServedByGuard(int guardId, String customerName) {
        return customerService.listServedByGuard(guardId, customerName);
    }

    /**
     * 根据ID查单条客户
     */
    public Customer getById(Long id) {
        return customerService.getById(id);
    }

    /**
     * 根据用户ID查询健康管家昵称
     */
    public String getGuardNickname(int userId) {
        return customerService.getGuardNickname(userId);
    }

    // ==================== 管理端方法 ====================

    /**
     * 新增客户
     */
    public String addCustomer(Customer customer) {
        try {
            boolean res = customerService.addCustomer(customer);
            return res ? "客户入住登记成功" : "客户入住登记失败";
        } catch (RuntimeException e) {
            return "操作失败：" + e.getMessage();
        }
    }

    /**
     * 修改客户信息
     */
    public String updateCustomer(Customer customer) {
        try {
            boolean res = customerService.updateCustomer(customer);
            return res ? "客户信息修改成功" : "客户信息修改失败";
        } catch (RuntimeException e) {
            return "操作失败：" + e.getMessage();
        }
    }

    /**
     * 逻辑删除客户
     */
    public String logicDeleteCustomer(Long id) {
        try {
            boolean res = customerService.logicDeleteCustomer(id);
            return res ? "客户删除成功" : "客户删除失败";
        } catch (RuntimeException e) {
            return "操作失败：" + e.getMessage();
        }
    }

    /**
     * 分页查询客户列表
     */
    public List<Customer> findCustomerPage(String customerName, Integer levelId, int pageIndex, int pageSize) {
        return customerService.findCustomerPage(customerName, levelId, pageIndex, pageSize);
    }

    /**
     * 获取客户总数
     */
    public long getCustomerTotal(String customerName, Integer levelId) {
        return customerService.getCustomerTotal(customerName, levelId);
    }
}
