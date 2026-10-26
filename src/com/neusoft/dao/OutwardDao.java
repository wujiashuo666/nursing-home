package com.neusoft.dao;

import java.sql.Date;
import java.util.List;
import java.util.Map;

import com.neusoft.entity.Outward;

/**
 * 外出申请数据访问接口
 */
public interface OutwardDao {

    /**
     * 查询某健康管家服务的客户的外出申请列表(关联客户姓名)，可按客户姓名模糊过滤
     */
    List<Map<String, Object>> listByGuard(int guardId, String customerName);

    /**
     * 新增外出申请(逻辑删除位默认0，审批状态由调用方设置，默认为已提交)
     */
    boolean add(Outward outward);

    /**
     * 登记回院时间(实际回院时间)
     */
    boolean updateReturnTime(Long id, Date actualReturnTime);

    /**
     * 根据ID查单条
     */
    Outward getById(Long id);

    // ==================== 管理端方法 ====================

    /**
     * 审核外出申请
     */
    int auditOutward(Long id, Integer auditStatus, String auditPerson);

    /**
     * 按客户ID查询外出记录
     */
    List<Outward> findByCustomerId(Long customerId);

    /**
     * 分页查询外出记录
     */
    List<Outward> findOutwardPage(String customerName, int pageIndex, int pageSize);

    /**
     * 获取外出记录总数
     */
    long getOutwardTotal(String customerName);
}
