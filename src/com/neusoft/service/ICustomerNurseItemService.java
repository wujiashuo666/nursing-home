package com.neusoft.service;

import java.util.List;
import java.util.Map;

import com.neusoft.entity.CustomerNurseItem;

/**
 * 客户护理项目业务接口
 */
public interface ICustomerNurseItemService {

    // 查询全部客户护理项目
    List<Map<String, Object>> list();

    // 根据客户id查询其分配的所有护理项目
    List<Map<String, Object>> getByCustomerId(Integer customerId);

    // 新增分配护理项目
    boolean add(CustomerNurseItem customerNurseItem);

    // 修改分配护理项目
    boolean update(CustomerNurseItem customerNurseItem);

    // 逻辑删除
    boolean delete(Long id);

    // 根据ID查单条
    CustomerNurseItem getById(Long id);
}
