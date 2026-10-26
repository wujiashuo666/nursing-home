package com.neusoft.service.Impl;

import java.util.List;

import com.neusoft.dao.NurseLevelDao;
import com.neusoft.dao.impl.NurseLevelDaoImpl;
import com.neusoft.entity.NurseLevel;
import com.neusoft.service.INurseLevelService;

public class INurseLevelServiceImpl implements INurseLevelService {

    private NurseLevelDao nurseLevelDao = new NurseLevelDaoImpl();

    @Override
    public List<NurseLevel> list() {
        return nurseLevelDao.list();
    }

    @Override
    public boolean add(NurseLevel nurseLevel) {
        return nurseLevelDao.add(nurseLevel);
    }

    @Override
    public boolean update(NurseLevel nurseLevel) {
        return nurseLevelDao.update(nurseLevel);
    }

    @Override
    public boolean delete(Long id) {
        return nurseLevelDao.delete(id);
    }

    @Override
    public NurseLevel getById(Long id) {
        return nurseLevelDao.getById(id);
    }
}
