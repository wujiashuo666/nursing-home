package com.neusoft.controller;

import java.util.List;
import java.util.Map;

import com.neusoft.entity.Backdown;
import com.neusoft.service.IBackdownService;
import com.neusoft.service.Impl.IBackdownServiceImpl;

/**
 * 退住申请控制层
 */
public class BackdownController {
    private IBackdownService backdownService = new IBackdownServiceImpl();

    /**
     * 查询某健康管家服务的客户的退住申请列表，可按客户姓名模糊过滤
     */
    public List<Map<String, Object>> listByGuard(int guardId, String customerName) {
        return backdownService.listByGuard(guardId, customerName);
    }

    /**
     * 新增退住申请：逻辑删除位默认0，审批状态默认已提交(0)
     */
    public boolean add(Backdown backdown) {
        backdown.setIsDeleted(0);
        if (backdown.getAuditStatus() == null) {
            backdown.setAuditStatus(0); // 0=已提交
        }
        return backdownService.add(backdown);
    }

    /**
     * 根据ID查单条
     */
    public Backdown getById(Long id) {
        return backdownService.getById(id);
    }

    // ==================== 管理端方法 ====================

    /**
     * 审核退住申请
     */
    public boolean auditBackDown(Long id, Integer auditStatus, String auditPerson) {
        return backdownService.auditBackDown(id, auditStatus, auditPerson);
    }

    /**
     * 按客户ID查询退住记录
     */
    public List<Backdown> findByCustomerId(Long customerId) {
        return backdownService.findByCustomerId(customerId);
    }

    /**
     * 分页查询退住记录
     */
    public List<Backdown> findBackDownPage(String customerName, int pageIndex, int pageSize) {
        return backdownService.findBackDownPage(customerName, pageIndex, pageSize);
    }

    /**
     * 获取退住记录总数
     */
    public long getBackDownTotal(String customerName) {
        return backdownService.getBackDownTotal(customerName);
    }
}
