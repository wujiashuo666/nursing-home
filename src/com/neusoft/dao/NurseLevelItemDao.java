package com.neusoft.dao;

import java.util.List;

import com.neusoft.entity.NurseLevelItem;

/**
 * 护理等级项目(护理等级-护理内容关联)数据访问接口
 */
public interface NurseLevelItemDao {

    // 新增关联(levelId + itemId)
    boolean add(Integer levelId, Integer itemId);

    // 删除关联(该表无逻辑删除位，物理删除)
    boolean delete(Long id);

    // 修改关联
    boolean update(NurseLevelItem nurseLevelItem);

    // 根据ID查单条
    NurseLevelItem getById(Long id);

    // 查询全部关联列表
    List<NurseLevelItem> list();

    // 根据护理等级id获取所有护理项目id
    List<Integer> getItemIdsByLevelId(Integer levelId);
}
