package com.neusoft.service;

import java.util.List;

import com.neusoft.entity.NurseLevel;

/**
 * 护理等级业务接口
 */
public interface INurseLevelService {

    // 查询全部护理等级
    List<NurseLevel> list();

    // 新增护理等级
    boolean add(NurseLevel nurseLevel);

    // 修改护理等级
    boolean update(NurseLevel nurseLevel);

    // 逻辑删除
    boolean delete(Long id);

    // 根据ID查单条
    NurseLevel getById(Long id);
}
