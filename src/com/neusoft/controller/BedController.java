package com.neusoft.controller;

import java.util.List;
import java.util.Map;

import com.neusoft.service.IBedService;
import com.neusoft.service.Impl.IBedServiceImpl;

/**
 * 床位控制层(床位示意图)
 */
public class BedController {
    private IBedService bedService = new IBedServiceImpl();

    /** 
     * 统计 总床位数和床位的空闲、外出、有人 数量 
    */
    public Map<String, Object> statistics() {
        return bedService.statistics();
    }

    /** 
     * 根据楼层(默认第一层)查询房间列表信息及每个房间的床位列表信息 
    */
    public List<Map<String, Object>> listByFloor(String floor) {
        return bedService.listByFloor(floor);
    }
}
