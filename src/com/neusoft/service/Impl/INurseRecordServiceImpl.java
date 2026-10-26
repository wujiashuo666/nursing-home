package com.neusoft.service.Impl;

import java.util.List;
import java.util.Map;

import com.neusoft.dao.NurseRecordDao;
import com.neusoft.dao.impl.NurseRecordDaoImpl;
import com.neusoft.entity.NurseRecord;
import com.neusoft.service.INurseRecordService;

public class INurseRecordServiceImpl implements INurseRecordService {

    private NurseRecordDao nurseRecordDao = new NurseRecordDaoImpl();

    @Override
    public List<Map<String, Object>> list() {
        return nurseRecordDao.list();
    }

    @Override
    public List<Map<String, Object>> listByCustomer(Integer customerId) {
        return nurseRecordDao.listByCustomer(customerId);
    }

    @Override
    public boolean add(NurseRecord nurseRecord) {
        return nurseRecordDao.add(nurseRecord);
    }

    @Override
    public boolean update(NurseRecord nurseRecord) {
        return nurseRecordDao.update(nurseRecord);
    }

    @Override
    public boolean delete(Long id) {
        return nurseRecordDao.delete(id);
    }

    @Override
    public NurseRecord getById(Long id) {
        return nurseRecordDao.getById(id);
    }
}
