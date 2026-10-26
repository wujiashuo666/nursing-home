package com.neusoft.controller;

import java.util.List;

import com.neusoft.entity.NurseLevel;
import com.neusoft.service.INurseLevelService;
import com.neusoft.service.Impl.INurseLevelServiceImpl;

/**
 * 护理等级控制层
 */
public class NurseLevelController {
    private INurseLevelService nurseLevelService = new INurseLevelServiceImpl();

    /** 
     * 查询全部护理等级 
    */
    public List<NurseLevel> list() {
        return nurseLevelService.list();
    }

    /** 
     * 新增护理等级 
    */
    public boolean add(NurseLevel nurseLevel) {
        nurseLevel.setIsDeleted(0);
        return nurseLevelService.add(nurseLevel);
    }

    /** 
     * 修改护理等级 
    */
    public boolean update(NurseLevel nurseLevel) {
        return nurseLevelService.update(nurseLevel);
    }

    /** 
     * 逻辑删除 
    */
    public boolean delete(Long id) {
        return nurseLevelService.delete(id);
    }

    /** 
     * 根据ID查单条 
    */
    public NurseLevel getById(Long id) {
        return nurseLevelService.getById(id);
    }
}
