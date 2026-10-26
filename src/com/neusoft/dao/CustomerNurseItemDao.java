package com.neusoft.dao;

import java.util.List;
import java.util.Map;

import com.neusoft.entity.CustomerNurseItem;

/**
 * 客户护理项目数据访问接口
 */
public interface CustomerNurseItemDao {

    /** 
     * 查询全部客户护理项目(关联客户姓名、护理项目名称、护理等级名称) 
    */
    List<Map<String, Object>> list();

    /** 
     * 根据客户id查询其分配的所有护理项目 
    */
    List<Map<String, Object>> getByCustomerId(Integer customerId);

    /** 
     * 新增分配护理项目(逻辑删除位默认为0) 
    */
    boolean add(CustomerNurseItem customerNurseItem);

    /** 
     * 修改分配护理项目 
    */
    boolean update(CustomerNurseItem customerNurseItem);

    /** 
     * 逻辑删除(置is_deleted=1) 
    */
    boolean delete(Long id);

    /** 
     * 根据ID查单条 
    */
    CustomerNurseItem getById(Long id);

    /**
     * 累加客户的护理次数(日常护理生成记录时同步更新 nurse_number = nurse_number + delta)
     */
    boolean incrementCount(Long id, int delta);
}
