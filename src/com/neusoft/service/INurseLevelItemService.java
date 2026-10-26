package com.neusoft.service;

import java.util.List;

import com.neusoft.entity.NurseLevelItem;

/**
 * 护理等级项目业务接口
 */
public interface INurseLevelItemService {

    // 新增关联
    boolean add(Integer levelId, Integer itemId);

    // 删除关联
    boolean delete(Long id);

    // 修改关联
    boolean update(NurseLevelItem nurseLevelItem);

    // 根据ID查单条
    NurseLevelItem getById(Long id);

    // 查询全部关联
    List<NurseLevelItem> list();

    // 根据护理等级id获取所有护理项目id
    List<Integer> getItemIdsByLevelId(Integer levelId);
}
