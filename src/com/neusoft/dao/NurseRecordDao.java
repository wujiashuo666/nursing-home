package com.neusoft.dao;

import java.util.List;
import java.util.Map;

import com.neusoft.entity.NurseRecord;

/**
 * 护理记录数据访问接口
 */
public interface NurseRecordDao {

    // 查询全部护理记录(关联客户姓名、护理项目名称、护理人显示)
    List<Map<String, Object>> list();

    // 查询某客户的护理记录(关联客户姓名、护理项目名称、护理人显示)
    List<Map<String, Object>> listByCustomer(Integer customerId);

    // 新增护理记录(逻辑删除位默认为0)
    boolean add(NurseRecord nurseRecord);

    // 修改护理记录
    boolean update(NurseRecord nurseRecord);

    // 逻辑删除(置is_deleted=1)
    boolean delete(Long id);

    // 根据ID查单条
    NurseRecord getById(Long id);
}
