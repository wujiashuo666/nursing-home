package com.neusoft.dao.impl;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.neusoft.dao.BedDao;
import com.neusoft.util.JDBCUtil;

public class BedDaoImpl implements BedDao {

    @Override
    public Map<String, Object> statistics() {
        Map<String, Object> result = new HashMap<>();
        try {
            JDBCUtil.initConnnection();
            JDBCUtil.initPreparedStatement(
                    "SELECT COUNT(*) AS total, " +
                    "SUM(CASE WHEN bed_status = 1 THEN 1 ELSE 0 END) AS free, " +
                    "SUM(CASE WHEN bed_status = 2 THEN 1 ELSE 0 END) AS occupied, " +
                    "SUM(CASE WHEN bed_status = 3 THEN 1 ELSE 0 END) AS outward " +
                    "FROM bed");
            ResultSet resultSet = JDBCUtil.executeQuery();
            if (resultSet.next()) {
                result.put("total", resultSet.getLong("total"));
                result.put("free", resultSet.getLong("free"));
                result.put("occupied", resultSet.getLong("occupied"));
                result.put("outward", resultSet.getLong("outward"));
            }
            resultSet.close();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            JDBCUtil.close();
        }
        return result;
    }

    @Override
    public List<Map<String, Object>> listByFloor(String floor) {
        List<Map<String, Object>> result = new ArrayList<>();
        try {
            JDBCUtil.initConnnection();

            // 查该楼层所有房间
            JDBCUtil.initPreparedStatement("SELECT id, room_floor, room_no FROM room WHERE room_floor = ? ORDER BY room_no");
            List<Object> p1 = new ArrayList<>();
            p1.add(floor);
            JDBCUtil.initPreparedSql(p1);
            ResultSet resultSet = JDBCUtil.executeQuery();
            List<Integer> roomNos = new ArrayList<>();
            while (resultSet.next()) {
                roomNos.add(resultSet.getInt("room_no"));
            }
            resultSet.close();

            // 循环每个房间查床位
            for (Integer roomNo : roomNos) {
                Map<String, Object> roomMap = new HashMap<>();
                roomMap.put("roomFloor", floor);
                roomMap.put("roomNo", roomNo);
                List<Map<String, Object>> beds = new ArrayList<>();

                JDBCUtil.initPreparedStatement("SELECT id, bed_no, bed_status FROM bed WHERE room_no = ? ORDER BY bed_no");
                List<Object> p2 = new ArrayList<>();
                p2.add(roomNo);
                JDBCUtil.initPreparedSql(p2);
                ResultSet rs = JDBCUtil.executeQuery();
                while (rs.next()) {
                    Map<String, Object> bedMap = new HashMap<>();
                    bedMap.put("bedId", rs.getLong("id"));
                    bedMap.put("bedNo", rs.getString("bed_no"));
                    bedMap.put("bedStatus", rs.getInt("bed_status"));
                    beds.add(bedMap);
                }
                rs.close();

                roomMap.put("beds", beds);
                result.add(roomMap);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            JDBCUtil.close();
        }
        return result;
    }
}
