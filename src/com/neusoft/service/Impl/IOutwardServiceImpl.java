package com.neusoft.service.Impl;

import java.sql.Date;
import java.util.List;
import java.util.Map;

import com.neusoft.dao.OutwardDao;
import com.neusoft.dao.impl.OutwardDaoImpl;
import com.neusoft.entity.Outward;
import com.neusoft.service.IOutwardService;

public class IOutwardServiceImpl implements IOutwardService {

    private OutwardDao outwardDao = new OutwardDaoImpl();

    @Override
    public List<Map<String, Object>> listByGuard(int guardId, String customerName) {
        return outwardDao.listByGuard(guardId, customerName);
    }

    @Override
    public boolean add(Outward outward) {
        return outwardDao.add(outward);
    }

    @Override
    public boolean updateReturnTime(Long id, Date actualReturnTime) {
        return outwardDao.updateReturnTime(id, actualReturnTime);
    }

    @Override
    public Outward getById(Long id) {
        return outwardDao.getById(id);
    }

    // ==================== 管理端方法实现 ====================

    @Override
    public boolean auditOutward(Long id, Integer auditStatus, String auditPerson) {
        return outwardDao.auditOutward(id, auditStatus, auditPerson) > 0;
    }

    @Override
    public List<Outward> findByCustomerId(Long customerId) {
        return outwardDao.findByCustomerId(customerId);
    }

    @Override
    public List<Outward> findOutwardPage(String customerName, int pageIndex, int pageSize) {
        return outwardDao.findOutwardPage(customerName, pageIndex, pageSize);
    }

    @Override
    public long getOutwardTotal(String customerName) {
        return outwardDao.getOutwardTotal(customerName);
    }
}
