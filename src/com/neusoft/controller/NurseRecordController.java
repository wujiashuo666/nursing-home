package com.neusoft.controller;

import java.util.List;
import java.util.Map;

import com.neusoft.entity.NurseRecord;
import com.neusoft.service.INurseRecordService;
import com.neusoft.service.Impl.INurseRecordServiceImpl;

/**
 * 护理记录控制层
 */
public class NurseRecordController {
    private INurseRecordService nurseRecordService = new INurseRecordServiceImpl();

    /** 
     * 查询全部护理记录(关联客户、项目、护理人) 
    */
    public List<Map<String, Object>> list() {
        return nurseRecordService.list();
    }

    /** 
     * 查询某客户的护理记录(关联客户、项目、护理人) 
    */
    public List<Map<String, Object>> listByCustomer(Integer customerId) {
        return nurseRecordService.listByCustomer(customerId);
    }

    /** 
     * 新增护理记录 
    */
    public boolean add(NurseRecord nurseRecord) {
        nurseRecord.setIsDeleted(0);
        return nurseRecordService.add(nurseRecord);
    }

    /** 
     * 修改护理记录 
    */
    public boolean update(NurseRecord nurseRecord) {
        return nurseRecordService.update(nurseRecord);
    }

    /** 
     * 逻辑删除 
    */
    public boolean delete(Long id) {
        return nurseRecordService.delete(id);
    }

    /** 
     * 根据ID查单条 
    */
    public NurseRecord getById(Long id) {
        return nurseRecordService.getById(id);
    }
}
