package com.neusoft.controller;

import java.util.List;
import java.util.Map;

import com.neusoft.service.IDailyCareService;
import com.neusoft.service.Impl.IDailyCareServiceImpl;

/**
 * 日常护理控制层(健康管家侧)
 */
public class DailyCareController {
    private IDailyCareService dailyCareService = new IDailyCareServiceImpl();

    /**
     * 查询某客户的所有护理项目(含状态)
     */
    public List<Map<String, Object>> listItemsByCustomer(Integer customerId) {
        return dailyCareService.listItemsByCustomer(customerId);
    }

    /**
     * 执行护理：新增护理记录并累加客户护理次数
     */
    public boolean performNursing(Integer customerId, Long customerNurseItemId,
                                  String nursingContent, int nursingCount, int guardId) {
        return dailyCareService.performNursing(customerId, customerNurseItemId,
                nursingContent, nursingCount, guardId);
    }
}
