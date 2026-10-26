package com.neusoft.service;

import java.util.List;
import java.util.Map;

import com.neusoft.entity.NurseRecord;

/**
 * 护理记录业务接口
 */
public interface INurseRecordService {

    // 查询全部护理记录(关联客户、项目、护理人)
    List<Map<String, Object>> list();

    // 查询某客户的护理记录(关联客户、项目、护理人)
    List<Map<String, Object>> listByCustomer(Integer customerId);

    // 新增护理记录
    boolean add(NurseRecord nurseRecord);

    // 修改护理记录
    boolean update(NurseRecord nurseRecord);

    // 逻辑删除
    boolean delete(Long id);

    // 根据ID查单条
    NurseRecord getById(Long id);
}
