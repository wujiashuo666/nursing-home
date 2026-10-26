package com.neusoft.service;

import java.util.List;
import java.util.Map;

/**
 * 床位使用详情业务接口(床位管理)
 */
public interface IBedDetailsService {

    /** 
     * 多条件查询床位使用详情：客户姓名(模糊)、入住日期、使用状态(1正在使用 2历史) 
    */
    List<Map<String, Object>> findUse(String customerName, String startDate, int status);

    /** 
     * 修改客户"正在使用"的床位使用结束时间(只能修改结束时间) 
    */
    boolean updateEndDate(int customerId, String endDate);

    /** 
     * 查询某房间的空闲床位(以供调换选择) 
    */
    List<Map<String, Object>> findFreeBedsByRoom(int roomNo);

    /** 
     * 床位调换 
    */
    boolean transferBed(int customerId, int newRoomNo, int newBedId);
}
