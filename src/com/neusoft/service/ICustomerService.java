package com.neusoft.service;

import java.util.List;

import com.neusoft.entity.Customer;

/**
 * 客户业务接口
 */
public interface ICustomerService {

    /**
     * 查询某健康管家(user_id)服务的客户列表，可按客户姓名模糊过滤
     */
    List<Customer> listServedByGuard(int guardId, String customerName);

    /**
     * 根据ID查单条客户
     */
    Customer getById(Long id);

    /**
     * 根据用户ID查询健康管家昵称
     */
    String getGuardNickname(int userId);

    // ==================== 管理端方法 ====================

    /**
     * 新增客户
     */
    boolean addCustomer(Customer customer);

    /**
     * 修改客户信息
     */
    boolean updateCustomer(Customer customer);

    /**
     * 逻辑删除客户
     */
    boolean logicDeleteCustomer(Long id);

    /**
     * 分页查询客户列表
     */
    List<Customer> findCustomerPage(String customerName, Integer levelId, int pageIndex, int pageSize);

    /**
     * 获取客户总数
     */
    long getCustomerTotal(String customerName, Integer levelId);
}
