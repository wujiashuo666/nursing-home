package com.neusoft.dao.impl;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.neusoft.dao.NurseRecordDao;
import com.neusoft.entity.NurseRecord;
import com.neusoft.util.JDBCUtil;

public class NurseRecordDaoImpl implements NurseRecordDao {

    @Override
    public List<Map<String, Object>> list() {
        List<Map<String, Object>> result = new ArrayList<>();
        try {
            JDBCUtil.initConnnection();
            JDBCUtil.initPreparedStatement(
                    "SELECT nr.id AS id, nr.customer_id AS customerId, c.customer_name AS customerName, "
                  + "nr.item_id AS itemId, nc.nursing_name AS itemName, nr.nursing_time AS nursingTime, "
                  + "nr.nursing_content AS nursingContent, nr.nursing_count AS nursingCount, "
                  + "nr.user_id AS userId, u.nickname AS operator, nr.is_deleted AS isDeleted "
                  + "FROM nurserecord nr "
                  + "LEFT JOIN customer c ON c.id = nr.customer_id "
                  + "LEFT JOIN nursecontent nc ON nc.id = nr.item_id "
                  + "LEFT JOIN user u ON u.id = nr.user_id "
                  + "WHERE nr.is_deleted = 0 ORDER BY nr.nursing_time DESC");
            ResultSet rs = JDBCUtil.executeQuery();
            while (rs.next()) {
                Map<String, Object> row = new HashMap<>();
                row.put("id", rs.getLong("id"));
                row.put("customerId", rs.getInt("customerId"));
                row.put("customerName", rs.getString("customerName"));
                row.put("itemId", rs.getInt("itemId"));
                row.put("itemName", rs.getString("itemName"));
                row.put("nursingTime", rs.getString("nursingTime"));
                row.put("nursingContent", rs.getString("nursingContent"));
                row.put("nursingCount", rs.getInt("nursingCount"));
                row.put("userId", rs.getInt("userId"));
                row.put("operator", rs.getString("operator"));
                row.put("isDeleted", rs.getInt("isDeleted"));
                result.add(row);
            }
            rs.close();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            JDBCUtil.close();
        }
        return result;
    }

    @Override
    public List<Map<String, Object>> listByCustomer(Integer customerId) {
        List<Map<String, Object>> result = new ArrayList<>();
        try {
            JDBCUtil.initConnnection();
            JDBCUtil.initPreparedStatement(
                    "SELECT nr.id AS id, nr.customer_id AS customerId, c.customer_name AS customerName, "
                  + "nr.item_id AS itemId, nc.nursing_name AS itemName, nr.nursing_time AS nursingTime, "
                  + "nr.nursing_content AS nursingContent, nr.nursing_count AS nursingCount, "
                  + "nr.user_id AS userId, u.nickname AS operator, nr.is_deleted AS isDeleted "
                  + "FROM nurserecord nr "
                  + "LEFT JOIN customer c ON c.id = nr.customer_id "
                  + "LEFT JOIN nursecontent nc ON nc.id = nr.item_id "
                  + "LEFT JOIN user u ON u.id = nr.user_id "
                  + "WHERE nr.is_deleted = 0 AND nr.customer_id = ? ORDER BY nr.nursing_time DESC");
            List<Object> params = new ArrayList<>();
            params.add(customerId);
            JDBCUtil.initPreparedSql(params);
            ResultSet rs = JDBCUtil.executeQuery();
            while (rs.next()) {
                Map<String, Object> row = new HashMap<>();
                row.put("id", rs.getLong("id"));
                row.put("customerId", rs.getInt("customerId"));
                row.put("customerName", rs.getString("customerName"));
                row.put("itemId", rs.getInt("itemId"));
                row.put("itemName", rs.getString("itemName"));
                row.put("nursingTime", rs.getString("nursingTime"));
                row.put("nursingContent", rs.getString("nursingContent"));
                row.put("nursingCount", rs.getInt("nursingCount"));
                row.put("userId", rs.getInt("userId"));
                row.put("operator", rs.getString("operator"));
                row.put("isDeleted", rs.getInt("isDeleted"));
                result.add(row);
            }
            rs.close();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            JDBCUtil.close();
        }
        return result;
    }

    @Override
    public boolean add(NurseRecord nr) {
        try {
            JDBCUtil.initConnnection();
            JDBCUtil.initPreparedStatement(
                    "INSERT INTO nurserecord(is_deleted, customer_id, item_id, nursing_time, "
                  + "nursing_content, nursing_count, user_id) VALUES(0, ?, ?, ?, ?, ?, ?)");
            List<Object> params = new ArrayList<>();
            params.add(nr.getCustomerId());
            params.add(nr.getItemId());
            params.add(nr.getNursingTime());
            params.add(nr.getNursingContent());
            params.add(nr.getNursingCount());
            params.add(nr.getUserId());
            JDBCUtil.initPreparedSql(params);
            return JDBCUtil.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        } finally {
            JDBCUtil.close();
        }
    }

    @Override
    public boolean update(NurseRecord nr) {
        try {
            JDBCUtil.initConnnection();
            JDBCUtil.initPreparedStatement(
                    "UPDATE nurserecord SET customer_id = ?, item_id = ?, nursing_time = ?, "
                  + "nursing_content = ?, nursing_count = ?, user_id = ? WHERE id = ? AND is_deleted = 0");
            List<Object> params = new ArrayList<>();
            params.add(nr.getCustomerId());
            params.add(nr.getItemId());
            params.add(nr.getNursingTime());
            params.add(nr.getNursingContent());
            params.add(nr.getNursingCount());
            params.add(nr.getUserId());
            params.add(nr.getId());
            JDBCUtil.initPreparedSql(params);
            return JDBCUtil.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        } finally {
            JDBCUtil.close();
        }
    }

    @Override
    public boolean delete(Long id) {
        try {
            JDBCUtil.initConnnection();
            JDBCUtil.initPreparedStatement("UPDATE nurserecord SET is_deleted = 1 WHERE id = ?");
            List<Object> params = new ArrayList<>();
            params.add(id);
            JDBCUtil.initPreparedSql(params);
            return JDBCUtil.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        } finally {
            JDBCUtil.close();
        }
    }

    @Override
    public NurseRecord getById(Long id) {
        try {
            JDBCUtil.initConnnection();
            JDBCUtil.initPreparedStatement(
                    "SELECT id, is_deleted, customer_id, item_id, nursing_time, "
                  + "nursing_content, nursing_count, user_id FROM nurserecord WHERE id = ? AND is_deleted = 0");
            List<Object> params = new ArrayList<>();
            params.add(id);
            JDBCUtil.initPreparedSql(params);
            ResultSet rs = JDBCUtil.executeQuery();
            NurseRecord nr = rs.next() ? mapRow(rs) : null;
            rs.close();
            return nr;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            JDBCUtil.close();
        }
    }

    private NurseRecord mapRow(ResultSet rs) throws Exception {
        NurseRecord nr = new NurseRecord();
        nr.setId(rs.getLong("id"));
        nr.setIsDeleted(rs.getInt("is_deleted"));
        nr.setCustomerId(rs.getInt("customer_id"));
        nr.setItemId(rs.getInt("item_id"));
        nr.setNursingTime(rs.getTimestamp("nursing_time"));
        nr.setNursingContent(rs.getString("nursing_content"));
        nr.setNursingCount(rs.getInt("nursing_count"));
        nr.setUserId(rs.getInt("user_id"));
        return nr;
    }
}
