package com.neusoft.service.Impl;

import java.util.List;
import java.util.Map;

import com.neusoft.dao.BedDao;
import com.neusoft.dao.impl.BedDaoImpl;
import com.neusoft.service.IBedService;

public class IBedServiceImpl implements IBedService {

    private BedDao bedDao = new BedDaoImpl();

    @Override
    public Map<String, Object> statistics() {
        return bedDao.statistics();
    }

    @Override
    public List<Map<String, Object>> listByFloor(String floor) {
        return bedDao.listByFloor(floor);
    }
}
