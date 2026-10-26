package com.neusoft.controller;

import java.sql.Date;
import java.util.List;
import java.util.Map;

import com.neusoft.entity.Outward;
import com.neusoft.service.IOutwardService;
import com.neusoft.service.Impl.IOutwardServiceImpl;

/**
 * 外出申请控制层
 */
public class OutwardController {
    private IOutwardService outwardService = new IOutwardServiceImpl();

    /**
     * 查询某健康管家服务的客户的外出申请列表，可按客户姓名模糊过滤
     */
    public List<Map<String, Object>> listByGuard(int guardId, String customerName) {
        return outwardService.listByGuard(guardId, customerName);
    }

    /**
     * 新增外出申请：逻辑删除位默认0，审批状态默认已提交(0)
     */
    public boolean add(Outward outward) {
        outward.setIsDeleted(0);
        if (outward.getAuditStatus() == null) {
            outward.setAuditStatus(0); // 0=已提交
        }
        return outwardService.add(outward);
    }

    /**
     * 登记回院时间(实际回院时间)
     */
    public boolean updateReturnTime(Long id, Date actualReturnTime) {
        return outwardService.updateReturnTime(id, actualReturnTime);
    }

    /**
     * 根据ID查单条
     */
    public Outward getById(Long id) {
        return outwardService.getById(id);
    }

    // ==================== 管理端方法 ====================

    /**
     * 审核外出申请
     */
    public boolean auditOutward(Long id, Integer auditStatus, String auditPerson) {
        return outwardService.auditOutward(id, auditStatus, auditPerson);
    }

    /**
     * 按客户ID查询外出记录
     */
    public List<Outward> findByCustomerId(Long customerId) {
        return outwardService.findByCustomerId(customerId);
    }

    /**
     * 分页查询外出记录
     */
    public List<Outward> findOutwardPage(String customerName, int pageIndex, int pageSize) {
        return outwardService.findOutwardPage(customerName, pageIndex, pageSize);
    }

    /**
     * 获取外出记录总数
     */
    public long getOutwardTotal(String customerName) {
        return outwardService.getOutwardTotal(customerName);
    }
}
