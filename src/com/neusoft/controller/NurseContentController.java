package com.neusoft.controller;

import java.util.List;

import com.neusoft.entity.NurseContent;
import com.neusoft.service.INurseContentService;
import com.neusoft.service.Impl.INurseContentServiceImpl;

/**
 * 护理内容(护理项目)控制层
 */
public class NurseContentController {
    private INurseContentService nurseContentService = new INurseContentServiceImpl();

    /** 
     * 多条件查询：状态筛选 + 名称模糊查询 
    */
    public List<NurseContent> list(Integer status, String name) {
        return nurseContentService.list(status, name);
    }

    /** 
     * 新增护理内容 
    */
    public boolean add(NurseContent nurseContent) {
        nurseContent.setIsDeleted(0);
        return nurseContentService.add(nurseContent);
    }

    /** 
     * 修改护理内容 
    */
    public boolean update(NurseContent nurseContent) {
        return nurseContentService.update(nurseContent);
    }

    /** 
     * 逻辑删除 
    */
    public boolean delete(Long id) {
        return nurseContentService.delete(id);
    }

    /** 
     * 根据ID查详情 
    */
    public NurseContent getById(Long id) {
        return nurseContentService.getById(id);
    }
}
