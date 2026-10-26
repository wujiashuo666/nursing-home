package com.neusoft.controller;

import java.util.List;
import java.util.Map;

import com.neusoft.service.IBedDetailsService;
import com.neusoft.service.Impl.IBedDetailsServiceImpl;

/**
 * 床位使用详情控制层(床位管理)
 */
public class BedDetailsController {
    private IBedDetailsService bedDetailsService = new IBedDetailsServiceImpl();

    /** 
     * 多条件查询床位使用详情：客户姓名(模糊)、入住日期、使用状态(1正在使用 2历史) 
    */
    public List<Map<String, Object>> findUse(String customerName, String startDate, int status) {
        return bedDetailsService.findUse(customerName, startDate, status);
    }

    /** 
     * 修改客户"正在使用"的床位使用结束时间(只能修改结束时间) 
    */
    public boolean updateEndDate(int customerId, String endDate) {
        return bedDetailsService.updateEndDate(customerId, endDate);
    }

    /** 
     * 查询某房间的空闲床位(以供调换选择) 
    */
    public List<Map<String, Object>> findFreeBedsByRoom(int roomNo) {
        return bedDetailsService.findFreeBedsByRoom(roomNo);
    }

    /** 
     * 床位调换
    */
    public boolean transferBed(int customerId, int newRoomNo, int newBedId) {
        return bedDetailsService.transferBed(customerId, newRoomNo, newBedId);
    }
}
