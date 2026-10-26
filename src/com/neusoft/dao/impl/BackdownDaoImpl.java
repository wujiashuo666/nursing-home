package com.neusoft.dao.impl;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.neusoft.dao.BackdownDao;
import com.neusoft.entity.Backdown;
import com.neusoft.util.JDBCUtil;

public class BackdownDaoImpl implements BackdownDao {

    private static final String JOIN_SELECT =
            "SELECT b.id AS id, b.customer_id AS customerId, c.customer_name AS customerName, "
          + "b.retreattime AS retreatTime, b.retreattype AS retreatType, "
          + "b.retreatreason AS retreatReason, b.auditstatus AS auditStatus, "
          + "b.auditperson AS auditPerson, b.audittime AS auditTime, b.remarks AS remarks "
          + "FROM backdown b LEFT JOIN customer c ON c.id = b.customer_id ";

    @Override
    public List<Map<String, Object>> listByGuard(int guardId, String customerName) {
        List<Map<String, Object>> result = new ArrayList<>();
        try {
            JDBCUtil.initConnnection();
            StringBuilder sql = new StringBuilder(
                    JOIN_SELECT + "WHERE b.is_deleted = 0 AND c.is_deleted = 0 AND c.user_id = ? ");
            List<Object> params = new ArrayList<>();
            params.add(guardId);
            if (customerName != null && !customerName.isEmpty()) {
                sql.append(" AND c.customer_name LIKE ?");
                params.add("%" + customerName + "%");
            }
            sql.append(" ORDER BY b.id");
            JDBCUtil.initPreparedStatement(sql.toString());
            JDBCUtil.initPreparedSql(params);
            ResultSet rs = JDBCUtil.executeQuery();
            while (rs.next()) {
                result.add(mapJoinedRow(rs));
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
    public boolean add(Backdown b) {
        try {
            JDBCUtil.initConnnection();
            JDBCUtil.initPreparedStatement(
                    "INSERT INTO backdown(remarks, is_deleted, customer_id, retreattime, "
                  + "retreattype, retreatreason, auditstatus) VALUES(?, 0, ?, ?, ?, ?, ?)");
            List<Object> params = new ArrayList<>();
            params.add(b.getRemarks());
            params.add(b.getCustomerId());
            params.add(b.getRetreatTime());
            params.add(b.getRetreatType());
            params.add(b.getRetreatReason());
            params.add(b.getAuditStatus());
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
    public Backdown getById(Long id) {
        try {
            JDBCUtil.initConnnection();
            JDBCUtil.initPreparedStatement(
                    "SELECT id, remarks, is_deleted, customer_id, retreattime, retreattype, "
                  + "retreatreason, auditstatus, auditperson, audittime FROM backdown WHERE id = ? AND is_deleted = 0");
            List<Object> params = new ArrayList<>();
            params.add(id);
            JDBCUtil.initPreparedSql(params);
            ResultSet rs = JDBCUtil.executeQuery();
            Backdown b = rs.next() ? mapRow(rs) : null;
            rs.close();
            return b;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            JDBCUtil.close();
        }
    }

    // ==================== 管理端方法实现 ====================

    @Override
    public int auditBackDown(Long id, Integer auditStatus, String auditPerson) {
        try {
            JDBCUtil.initConnnection();
            String sql = "UPDATE backdown SET auditstatus = ?, auditperson = ?, audittime = NOW() WHERE id = ? AND is_deleted = 0";
            List<Object> params = new ArrayList<>();
            params.add(auditStatus);
            params.add(auditPerson);
            params.add(id);
            JDBCUtil.initPreparedStatement(sql);
            JDBCUtil.initPreparedSql(params);
            return JDBCUtil.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        } finally {
            JDBCUtil.close();
        }
    }

    @Override
    public List<Backdown> findByCustomerId(Long customerId) {
        List<Backdown> result = new ArrayList<>();
        try {
            JDBCUtil.initConnnection();
            String sql = "SELECT id, remarks, is_deleted, customer_id, retreattime, retreattype, "
                    + "retreatreason, auditstatus, auditperson, audittime FROM backdown "
                    + "WHERE customer_id = ? AND is_deleted = 0 ORDER BY id DESC";
            List<Object> params = new ArrayList<>();
            params.add(customerId);
            JDBCUtil.initPreparedStatement(sql);
            JDBCUtil.initPreparedSql(params);
            ResultSet rs = JDBCUtil.executeQuery();
            while (rs.next()) {
                result.add(mapRow(rs));
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
    public List<Backdown> findBackDownPage(String customerName, int pageIndex, int pageSize) {
        List<Backdown> result = new ArrayList<>();
        try {
            JDBCUtil.initConnnection();
            StringBuilder sql = new StringBuilder(
                    "SELECT b.id, b.remarks, b.is_deleted, b.customer_id, b.retreattime, b.retreattype, "
                  + "b.retreatreason, b.auditstatus, b.auditperson, b.audittime "
                  + "FROM backdown b LEFT JOIN customer c ON c.id = b.customer_id AND c.is_deleted = 0 "
                  + "WHERE b.is_deleted = 0 ");
            List<Object> params = new ArrayList<>();
            if (customerName != null && !customerName.isEmpty()) {
                sql.append(" AND c.customer_name LIKE ?");
                params.add("%" + customerName + "%");
            }
            sql.append(" ORDER BY b.id DESC LIMIT ?, ?");
            params.add((pageIndex - 1) * pageSize);
            params.add(pageSize);
            JDBCUtil.initPreparedStatement(sql.toString());
            JDBCUtil.initPreparedSql(params);
            ResultSet rs = JDBCUtil.executeQuery();
            while (rs.next()) {
                result.add(mapRow(rs));
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
    public long getBackDownTotal(String customerName) {
        try {
            JDBCUtil.initConnnection();
            StringBuilder sql = new StringBuilder(
                    "SELECT COUNT(*) FROM backdown b LEFT JOIN customer c ON c.id = b.customer_id AND c.is_deleted = 0 "
                  + "WHERE b.is_deleted = 0 ");
            List<Object> params = new ArrayList<>();
            if (customerName != null && !customerName.isEmpty()) {
                sql.append(" AND c.customer_name LIKE ?");
                params.add("%" + customerName + "%");
            }
            JDBCUtil.initPreparedStatement(sql.toString());
            JDBCUtil.initPreparedSql(params);
            ResultSet rs = JDBCUtil.executeQuery();
            long total = rs.next() ? rs.getLong(1) : 0;
            rs.close();
            return total;
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        } finally {
            JDBCUtil.close();
        }
    }

    private Map<String, Object> mapJoinedRow(ResultSet rs) throws Exception {
        Map<String, Object> row = new HashMap<>();
        row.put("id", rs.getLong("id"));
        row.put("customerId", rs.getInt("customerId"));
        row.put("customerName", rs.getString("customerName"));
        row.put("retreatTime", rs.getObject("retreatTime") == null ? null : rs.getString("retreatTime"));
        row.put("retreatType", rs.getInt("retreatType"));
        row.put("retreatReason", rs.getString("retreatReason"));
        row.put("auditStatus", rs.getInt("auditStatus"));
        row.put("auditPerson", rs.getString("auditPerson"));
        row.put("auditTime", rs.getObject("auditTime") == null ? null : rs.getString("auditTime"));
        row.put("remarks", rs.getString("remarks"));
        return row;
    }

    private Backdown mapRow(ResultSet rs) throws Exception {
        Backdown b = new Backdown();
        b.setId(rs.getLong("id"));
        b.setRemarks(rs.getString("remarks"));
        b.setIsDeleted(rs.getInt("is_deleted"));
        b.setCustomerId(rs.getInt("customer_id"));
        b.setRetreatTime(rs.getDate("retreattime"));
        b.setRetreatType(rs.getInt("retreattype"));
        b.setRetreatReason(rs.getString("retreatreason"));
        b.setAuditStatus(rs.getInt("auditstatus"));
        b.setAuditPerson(rs.getString("auditperson"));
        b.setAuditTime(rs.getDate("audittime"));
        return b;
    }
}
