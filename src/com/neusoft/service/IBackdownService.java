package com.neusoft.service;

import java.util.List;
import java.util.Map;

import com.neusoft.entity.Backdown;

/**
 * 退住申请业务接口
 */
public interface IBackdownService {

    /**
     * 查询某健康管家服务的客户的退住申请列表(关联客户姓名)，可按客户姓名模糊过滤
     */
    List<Map<String, Object>> listByGuard(int guardId, String customerName);

    /**
     * 新增退住申请
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
    boolean auditBackDown(Long id, Integer auditStatus, String auditPerson);

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
