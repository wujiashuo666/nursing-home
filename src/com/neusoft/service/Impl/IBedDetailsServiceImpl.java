package com.neusoft.service.Impl;

import java.util.List;
import java.util.Map;

import com.neusoft.dao.BedDetailsDao;
import com.neusoft.dao.impl.BedDetailsDaoImpl;
import com.neusoft.service.IBedDetailsService;

public class IBedDetailsServiceImpl implements IBedDetailsService {

    private BedDetailsDao bedDetailsDao = new BedDetailsDaoImpl();

    @Override
    public List<Map<String, Object>> findUse(String customerName, String startDate, int status) {
        return bedDetailsDao.findUse(customerName, startDate, status);
    }

    @Override
    public boolean updateEndDate(int customerId, String endDate) {
        return bedDetailsDao.updateEndDate(customerId, endDate);
    }

    @Override
    public List<Map<String, Object>> findFreeBedsByRoom(int roomNo) {
        return bedDetailsDao.findFreeBedsByRoom(roomNo);
    }

    @Override
    public boolean transferBed(int customerId, int newRoomNo, int newBedId) {
        return bedDetailsDao.transferBed(customerId, newRoomNo, newBedId);
    }
}
