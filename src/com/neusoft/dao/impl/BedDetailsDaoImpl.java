package com.neusoft.dao.impl;

import java.sql.Connection;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.neusoft.dao.BedDetailsDao;
import com.neusoft.util.JDBCUtil;

public class BedDetailsDaoImpl implements BedDetailsDao {

    @Override
    public List<Map<String, Object>> findUse(String customerName, String startDate, int status) {
        List<Map<String, Object>> result = new ArrayList<>();
        try {
            JDBCUtil.initConnnection();
            StringBuilder sql = new StringBuilder(
                    "SELECT bd.id AS bedDetailsId, bd.customer_id AS customerId, c.customer_name AS customerName, "
                  + "bd.bed_id AS bedId, b.bed_no AS bedNo, b.room_no AS roomNo, r.room_floor AS roomFloor, "
                  + "c.building_no AS buildingNo, bd.start_date AS startDate, bd.end_date AS endDate, "
                  + "CASE WHEN bd.is_deleted = 1 OR (bd.end_date IS NOT NULL AND bd.end_date < CURDATE()) "
                  + "     THEN 2 ELSE 1 END AS status "
                  + "FROM beddetails bd "
                  + "LEFT JOIN customer c ON c.id = bd.customer_id AND c.is_deleted = 0 "
                  + "LEFT JOIN bed b ON b.id = bd.bed_id "
                  + "LEFT JOIN room r ON r.room_no = b.room_no "
                  + "WHERE 1=1 ");

            List<Object> params = new ArrayList<>();
            if (customerName != null && !customerName.isEmpty()) {
                sql.append(" AND c.customer_name LIKE ?");
                params.add("%" + customerName + "%");
            }
            if (startDate != null && !startDate.isEmpty()) {
                sql.append(" AND bd.start_date = ?");
                params.add(startDate);
            }
            if (status == 0 || status == 1) {
                sql.append(" AND bd.is_deleted = 0 AND (bd.end_date IS NULL OR bd.end_date >= CURDATE())");
            } else if (status == 2) {
                sql.append(" AND (bd.is_deleted = 1 OR (bd.end_date IS NOT NULL AND bd.end_date < CURDATE()))");
            }
            sql.append(" ORDER BY bd.start_date DESC");

            JDBCUtil.initPreparedStatement(sql.toString());
            JDBCUtil.initPreparedSql(params);
            ResultSet resultSet = JDBCUtil.executeQuery();
            while (resultSet.next()) {
                Map<String, Object> row = new HashMap<>();
                row.put("bedDetailsId", resultSet.getLong("bedDetailsId"));
                row.put("customerId", resultSet.getInt("customerId"));
                row.put("customerName", resultSet.getString("customerName"));
                row.put("bedId", resultSet.getInt("bedId"));
                row.put("bedNo", resultSet.getString("bedNo"));
                row.put("roomNo", resultSet.getInt("roomNo"));
                row.put("roomFloor", resultSet.getString("roomFloor"));
                row.put("buildingNo", resultSet.getString("buildingNo"));
                row.put("startDate", resultSet.getString("startDate"));
                row.put("endDate", resultSet.getString("endDate"));
                row.put("status", resultSet.getInt("status"));
                result.add(row);
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
    public boolean updateEndDate(int customerId, String endDate) {
        try {
            JDBCUtil.initConnnection();
            JDBCUtil.initPreparedStatement("UPDATE beddetails SET end_date = ? WHERE customer_id = ? AND is_deleted = 0 AND (end_date IS NULL OR end_date >= CURDATE())");
            List<Object> params = new ArrayList<>();
            if (endDate == null) {
                params.add(null);
            } else {
                params.add(endDate);
            }
            params.add(customerId);
            JDBCUtil.initPreparedSql(params);
            int rows = JDBCUtil.executeUpdate();
            return rows > 0;
        } finally {
            JDBCUtil.close();
        }
    }

    @Override
    public List<Map<String, Object>> findFreeBedsByRoom(int roomNo) {
        List<Map<String, Object>> result = new ArrayList<>();
        try {
            JDBCUtil.initConnnection();
            JDBCUtil.initPreparedStatement(
                    "SELECT id, bed_no FROM bed WHERE room_no = ? AND bed_status = 1 ORDER BY bed_no");
            List<Object> params = new ArrayList<>();
            params.add(roomNo);
            JDBCUtil.initPreparedSql(params);
            ResultSet resultSet = JDBCUtil.executeQuery();
            while (resultSet.next()) {
                Map<String, Object> bed = new HashMap<>();
                bed.put("bedId", resultSet.getLong("id"));
                bed.put("bedNo", resultSet.getString("bed_no"));
                result.add(bed);
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
    public boolean transferBed(int customerId, int newRoomNo, int newBedId) {
        try {
            JDBCUtil.initConnnection();
            Connection connection = JDBCUtil.getConnection();
            connection.setAutoCommit(false);   // 开启事务

            // 查客户当前"正在使用"的旧床位详情
            JDBCUtil.initPreparedStatement(
                    "SELECT id, bed_id FROM beddetails " +
                    "WHERE customer_id = ? AND is_deleted = 0 " +
                    "  AND (end_date IS NULL OR end_date >= CURDATE()) LIMIT 1");
            List<Object> p1 = new ArrayList<>();
            p1.add(customerId);
            JDBCUtil.initPreparedSql(p1);
            ResultSet rs = JDBCUtil.executeQuery();
            if (!rs.next()) {
                throw new RuntimeException("该客户没有正在使用的床位，无法调换");
            }
            int oldBedDetailsId = rs.getInt("id");
            int oldBedId = rs.getInt("bed_id");
            rs.close();

            // 校验新床位(存在、属于该房间、且空闲)
            JDBCUtil.initPreparedStatement("SELECT room_no, bed_status FROM bed WHERE id = ?");
            List<Object> p2 = new ArrayList<>();
            p2.add(newBedId);
            JDBCUtil.initPreparedSql(p2);
            ResultSet rs2 = JDBCUtil.executeQuery();
            if (!rs2.next()) {
                throw new RuntimeException("新床位不存在");
            }
            int dbRoomNo = rs2.getInt("room_no");
            int dbStatus = rs2.getInt("bed_status");
            rs2.close();
            if (dbRoomNo != newRoomNo) throw new RuntimeException("新床位不属于所选房间");
            if (dbStatus != 1)        throw new RuntimeException("目标床位不是空闲状态，无法调换");

            String today = java.time.LocalDate.now().toString();

            // 旧床位详情失效(is_deleted=1，结束时间=当天)
            JDBCUtil.initPreparedStatement("UPDATE beddetails SET is_deleted = 1, end_date = ? WHERE id = ?");
            List<Object> p3 = new ArrayList<>();
            p3.add(today);
            p3.add(oldBedDetailsId);
            JDBCUtil.initPreparedSql(p3);
            JDBCUtil.executeUpdate();

            // 新增新床位详情(入住时间=当天)
            JDBCUtil.initPreparedStatement(
                    "INSERT INTO beddetails(start_date, end_date, bed_details, customer_id, bed_id, is_deleted) " +
                    "VALUES(?, NULL, ?, ?, ?, 0)");
            List<Object> p4 = new ArrayList<>();
            p4.add(today);
            p4.add("床位调换");
            p4.add(customerId);
            p4.add(newBedId);
            JDBCUtil.initPreparedSql(p4);
            JDBCUtil.executeUpdate();

            // 旧床变空闲(1)，新床变有人(2)
            JDBCUtil.initPreparedStatement("UPDATE bed SET bed_status = 1 WHERE id = ?");
            List<Object> p5 = new ArrayList<>();
            p5.add(oldBedId);
            JDBCUtil.initPreparedSql(p5);
            JDBCUtil.executeUpdate();

            JDBCUtil.initPreparedStatement("UPDATE bed SET bed_status = 2 WHERE id = ?");
            List<Object> p6 = new ArrayList<>();
            p6.add(newBedId);
            JDBCUtil.initPreparedSql(p6);
            JDBCUtil.executeUpdate();

            // 修改客户信息(新房间号、新床位号、楼号(固定606))
            JDBCUtil.initPreparedStatement(
                    "UPDATE customer SET room_no = ?, bed_id = ?, building_no = ? WHERE id = ?");
            List<Object> p7 = new ArrayList<>();
            p7.add(String.valueOf(newRoomNo));
            p7.add(newBedId);
            p7.add("606");
            p7.add(customerId);
            JDBCUtil.initPreparedSql(p7);
            JDBCUtil.executeUpdate();

            connection.commit();            // 全部成功才提交
            return true;
        } catch (RuntimeException e) {
            // 业务错误(如"该客户没有正在使用的床位")：回滚后抛出，由上层显示原因
            try {
                if (JDBCUtil.getConnection() != null) JDBCUtil.getConnection().rollback();
            } catch (Exception ex) { }
            throw e;
        } catch (Exception e) {
            // 数据库/其他异常：回滚并记录
            try {
                if (JDBCUtil.getConnection() != null) JDBCUtil.getConnection().rollback();
            } catch (Exception ex) { }
            e.printStackTrace();
            return false;
        } finally {
            JDBCUtil.close();
        }
    }
}
