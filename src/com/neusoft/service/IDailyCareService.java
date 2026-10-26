package com.neusoft.service;

import java.util.List;
import java.util.Map;


/**
 * 日常护理业务接口(健康管家侧)
 */
public interface IDailyCareService {

    /**
     * 查询某客户的所有护理项目(关联项目名、剩余次数、到期时间，并附带状态)
     */
    List<Map<String, Object>> listItemsByCustomer(Integer customerId);

    /**
     * 执行护理：新增护理记录，并累加客户对应护理项目的护理次数
     *
     * @param customerId          客户id
     * @param customerNurseItemId 客户护理项目分配id(customernurseitem.id)
     * @param nursingContent      护理内容(可空)
     * @param nursingCount        本次护理数量
     * @param guardId             当前健康管家(user_id)
     */
    boolean performNursing(Integer customerId, Long customerNurseItemId,
                           String nursingContent, int nursingCount, int guardId);
}
