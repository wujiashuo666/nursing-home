package com.neusoft.controller;

import java.util.List;

import com.neusoft.entity.NurseLevelItem;
import com.neusoft.service.INurseLevelItemService;
import com.neusoft.service.Impl.INurseLevelItemServiceImpl;

/**
 * 护理等级项目(护理等级-护理内容关联)控制层
 */
public class NurseLevelItemController {
    private INurseLevelItemService nurseLevelItemService = new INurseLevelItemServiceImpl();

    /** 
     * 新增关联 
    */
    public boolean add(Integer levelId, Integer itemId) {
        return nurseLevelItemService.add(levelId, itemId);
    }

    /** 
     * 删除关联 
    */
    public boolean delete(Long id) {
        return nurseLevelItemService.delete(id);
    }

    /** 
     * 修改关联 
    */
    public boolean update(NurseLevelItem nurseLevelItem) {
        return nurseLevelItemService.update(nurseLevelItem);
    }

    /** 
     * 根据ID查单条 
    */
    public NurseLevelItem getById(Long id) {
        return nurseLevelItemService.getById(id);
    }

    /** 
     * 查询全部关联 
    */
    public List<NurseLevelItem> list() {
        return nurseLevelItemService.list();
    }

    /** 
     * 根据护理等级id获取所有护理项目id 
    */
    public List<Integer> getItemIdsByLevelId(Integer levelId) {
        return nurseLevelItemService.getItemIdsByLevelId(levelId);
    }
}
