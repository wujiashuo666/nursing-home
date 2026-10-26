package com.neusoft.service.Impl;

import java.util.List;
import java.util.Map;

import com.neusoft.dao.BackdownDao;
import com.neusoft.dao.impl.BackdownDaoImpl;
import com.neusoft.entity.Backdown;
import com.neusoft.service.IBackdownService;

public class IBackdownServiceImpl implements IBackdownService {

    private BackdownDao backdownDao = new BackdownDaoImpl();

    @Override
    public List<Map<String, Object>> listByGuard(int guardId, String customerName) {
        return backdownDao.listByGuard(guardId, customerName);
    }

    @Override
    public boolean add(Backdown backdown) {
        return backdownDao.add(backdown);
    }

    @Override
    public Backdown getById(Long id) {
        return backdownDao.getById(id);
    }

    // ==================== 管理端方法实现 ====================

    @Override
    public boolean auditBackDown(Long id, Integer auditStatus, String auditPerson) {
        return backdownDao.auditBackDown(id, auditStatus, auditPerson) > 0;
    }

    @Override
    public List<Backdown> findByCustomerId(Long customerId) {
        return backdownDao.findByCustomerId(customerId);
    }

    @Override
    public List<Backdown> findBackDownPage(String customerName, int pageIndex, int pageSize) {
        return backdownDao.findBackDownPage(customerName, pageIndex, pageSize);
    }

    @Override
    public long getBackDownTotal(String customerName) {
        return backdownDao.getBackDownTotal(customerName);
    }
}
