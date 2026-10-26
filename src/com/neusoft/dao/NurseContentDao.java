package com.neusoft.dao;

import java.util.List;

import com.neusoft.entity.NurseContent;

/**
 * 护理内容(护理项目)数据访问接口
 */
public interface NurseContentDao {

    /** 
     * 多条件查询：状态筛选 + 名称模糊查询 
    */
    List<NurseContent> list(Integer status, String name);

    /** 
     * 新增护理内容(逻辑删除位默认为0) 
    */
    boolean add(NurseContent nurseContent);

    /** 
     * 修改护理内容 
    */
    boolean update(NurseContent nurseContent);

    /** 
     * 逻辑删除(置is_deleted=1) 
    */
    boolean delete(Long id);

    /** 
     * 根据ID查详情 
    */
    NurseContent getById(Long id);
}
