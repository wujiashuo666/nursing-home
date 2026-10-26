package com.neusoft.dao;

import java.util.List;
import java.util.Map;

/**
 * 床位数据访问接口(床位示意图)
 */
public interface BedDao {

    /** 
     * 统计 总床位数和床位的空闲、外出、有人 数量 
    */
    Map<String, Object> statistics();

    /** 
     * 根据楼层(默认第一层)查询房间列表信息及每个房间的床位列表信息 
    */
    List<Map<String, Object>> listByFloor(String floor);
}
