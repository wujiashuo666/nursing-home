package com.neusoft.service.Impl;

import java.util.List;

import com.neusoft.dao.NurseLevelItemDao;
import com.neusoft.dao.impl.NurseLevelItemDaoImpl;
import com.neusoft.entity.NurseLevelItem;
import com.neusoft.service.INurseLevelItemService;

public class INurseLevelItemServiceImpl implements INurseLevelItemService {

    private NurseLevelItemDao nurseLevelItemDao = new NurseLevelItemDaoImpl();

    @Override
    public boolean add(Integer levelId, Integer itemId) {
        return nurseLevelItemDao.add(levelId, itemId);
    }

    @Override
    public boolean delete(Long id) {
        return nurseLevelItemDao.delete(id);
    }

    @Override
    public boolean update(NurseLevelItem nurseLevelItem) {
        return nurseLevelItemDao.update(nurseLevelItem);
    }

    @Override
    public NurseLevelItem getById(Long id) {
        return nurseLevelItemDao.getById(id);
    }

    @Override
    public List<NurseLevelItem> list() {
        return nurseLevelItemDao.list();
    }

    @Override
    public List<Integer> getItemIdsByLevelId(Integer levelId) {
        return nurseLevelItemDao.getItemIdsByLevelId(levelId);
    }
}
