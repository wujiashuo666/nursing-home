package com.neusoft.dao;

import com.neusoft.entity.Backdown;

import java.util.List;
import java.util.Map;

/**
 * 退住申请数据访问接口
 */
public interface BackdownDao {

    /**
     * 查询某健康管家服务的客户的退住申请列表(关联客户姓名)，可按客户姓名模糊过滤
     */
    List<Map<String, Object>> listByGuard(int guardId, String customerName);

    /**
     * 新增退住申请(逻辑删除位默认0，审批状态由调用方设置，默认为已提交)
     */
    boolean add(Backdown backdown);

    /**
     * 根据ID查单条
     */
    Backdown getById(Long id);

    // ==================== 管理端方法 ====================

    /**
     * 审核退住申请
     */
    int auditBackDown(Long id, Integer auditStatus, String auditPerson);

    /**
     * 按客户ID查询退住记录
     */
    List<Backdown> findByCustomerId(Long customerId);

    /**
     * 分页查询退住记录
     */
    List<Backdown> findBackDownPage(String customerName, int pageIndex, int pageSize);

    /**
     * 获取退住记录总数
     */
    long getBackDownTotal(String customerName);
}
