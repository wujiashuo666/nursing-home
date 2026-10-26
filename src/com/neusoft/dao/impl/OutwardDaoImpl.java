package com.neusoft.dao.impl;

import java.sql.Date;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.neusoft.dao.OutwardDao;
import com.neusoft.entity.Outward;
import com.neusoft.util.JDBCUtil;

public class OutwardDaoImpl implements OutwardDao {

    private static final String JOIN_SELECT =
            "SELECT o.id AS id, o.customer_id AS customerId, c.customer_name AS customerName, "
          + "o.outgoingreason AS outgoingReason, o.outgoingtime AS outgoingTime, "
          + "o.expectedreturntime AS expectedReturnTime, o.actualreturntime AS actualReturnTime, "
          + "o.escorted AS escorted, o.relation AS relation, o.escortedtel AS escortedTel, "
          + "o.auditstatus AS auditStatus, o.auditperson AS auditPerson, o.audittime AS auditTime "
          + "FROM outward o LEFT JOIN customer c ON c.id = o.customer_id ";

    @Override
    public List<Map<String, Object>> listByGuard(int guardId, String customerName) {
        List<Map<String, Object>> result = new ArrayList<>();
        try {
            JDBCUtil.initConnnection();
            StringBuilder sql = new StringBuilder(
                    JOIN_SELECT + "WHERE o.is_deleted = 0 AND c.is_deleted = 0 AND c.user_id = ? ");
            List<Object> params = new ArrayList<>();
            params.add(guardId);
            if (customerName != null && !customerName.isEmpty()) {
                sql.append(" AND c.customer_name LIKE ?");
                params.add("%" + customerName + "%");
            }
            sql.append(" ORDER BY o.id");
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
    public boolean add(Outward o) {
        try {
            JDBCUtil.initConnnection();
            JDBCUtil.initPreparedStatement(
                    "INSERT INTO outward(remarks, is_deleted, customer_id, outgoingreason, "
                  + "outgoingtime, expectedreturntime, actualreturntime, escorted, relation, "
                  + "escortedtel, auditstatus) VALUES(?, 0, ?, ?, ?, ?, ?, ?, ?, ?, ?)");
            List<Object> params = new ArrayList<>();
            params.add(o.getRemarks());
            params.add(o.getCustomerId());
            params.add(o.getOutgoingReason());
            params.add(o.getOutgoingTime());
            params.add(o.getExpectedReturnTime());
            params.add(o.getActualReturnTime());
            params.add(o.getEscorted());
            params.add(o.getRelation());
            params.add(o.getEscortedTel());
            params.add(o.getAuditStatus());
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
    public boolean updateReturnTime(Long id, Date actualReturnTime) {
        try {
            JDBCUtil.initConnnection();
            JDBCUtil.initPreparedStatement("UPDATE outward SET actualreturntime = ? WHERE id = ? AND is_deleted = 0");
            List<Object> params = new ArrayList<>();
            params.add(actualReturnTime);
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
    public Outward getById(Long id) {
        try {
            JDBCUtil.initConnnection();
            JDBCUtil.initPreparedStatement(
                    "SELECT id, remarks, is_deleted, customer_id, outgoingreason, outgoingtime, "
                  + "expectedreturntime, actualreturntime, escorted, relation, escortedtel, "
                  + "auditstatus, auditperson, audittime FROM outward WHERE id = ? AND is_deleted = 0");
            List<Object> params = new ArrayList<>();
            params.add(id);
            JDBCUtil.initPreparedSql(params);
            ResultSet rs = JDBCUtil.executeQuery();
            Outward o = rs.next() ? mapRow(rs) : null;
            rs.close();
            return o;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            JDBCUtil.close();
        }
    }

    // ==================== 管理端方法实现 ====================

    @Override
    public int auditOutward(Long id, Integer auditStatus, String auditPerson) {
        try {
            JDBCUtil.initConnnection();
            String sql = "UPDATE outward SET auditstatus = ?, auditperson = ?, audittime = NOW() WHERE id = ? AND is_deleted = 0";
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
    public List<Outward> findByCustomerId(Long customerId) {
        List<Outward> result = new ArrayList<>();
        try {
            JDBCUtil.initConnnection();
            String sql = "SELECT id, remarks, is_deleted, customer_id, outgoingreason, outgoingtime, "
                    + "expectedreturntime, actualreturntime, escorted, relation, escortedtel, "
                    + "auditstatus, auditperson, audittime FROM outward "
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
    public List<Outward> findOutwardPage(String customerName, int pageIndex, int pageSize) {
        List<Outward> result = new ArrayList<>();
        try {
            JDBCUtil.initConnnection();
            StringBuilder sql = new StringBuilder(
                    "SELECT o.id, o.remarks, o.is_deleted, o.customer_id, o.outgoingreason, o.outgoingtime, "
                  + "o.expectedreturntime, o.actualreturntime, o.escorted, o.relation, o.escortedtel, "
                  + "o.auditstatus, o.auditperson, o.audittime "
                  + "FROM outward o LEFT JOIN customer c ON c.id = o.customer_id AND c.is_deleted = 0 "
                  + "WHERE o.is_deleted = 0 ");
            List<Object> params = new ArrayList<>();
            if (customerName != null && !customerName.isEmpty()) {
                sql.append(" AND c.customer_name LIKE ?");
                params.add("%" + customerName + "%");
            }
            sql.append(" ORDER BY o.id DESC LIMIT ?, ?");
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
    public long getOutwardTotal(String customerName) {
        try {
            JDBCUtil.initConnnection();
            StringBuilder sql = new StringBuilder(
                    "SELECT COUNT(*) FROM outward o LEFT JOIN customer c ON c.id = o.customer_id AND c.is_deleted = 0 "
                  + "WHERE o.is_deleted = 0 ");
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
        row.put("outgoingReason", rs.getString("outgoingReason"));
        row.put("outgoingTime", rs.getObject("outgoingTime") == null ? null : rs.getString("outgoingTime"));
        row.put("expectedReturnTime", rs.getObject("expectedReturnTime") == null ? null : rs.getString("expectedReturnTime"));
        row.put("actualReturnTime", rs.getObject("actualReturnTime") == null ? null : rs.getString("actualReturnTime"));
        row.put("escorted", rs.getString("escorted"));
        row.put("relation", rs.getString("relation"));
        row.put("escortedTel", rs.getString("escortedTel"));
        row.put("auditStatus", rs.getInt("auditStatus"));
        row.put("auditPerson", rs.getString("auditPerson"));
        row.put("auditTime", rs.getObject("auditTime") == null ? null : rs.getString("auditTime"));
        return row;
    }

    private Outward mapRow(ResultSet rs) throws Exception {
        Outward o = new Outward();
        o.setId(rs.getLong("id"));
        o.setRemarks(rs.getString("remarks"));
        o.setIsDeleted(rs.getInt("is_deleted"));
        o.setCustomerId(rs.getInt("customer_id"));
        o.setOutgoingReason(rs.getString("outgoingreason"));
        o.setOutgoingTime(rs.getDate("outgoingtime"));
        o.setExpectedReturnTime(rs.getDate("expectedreturntime"));
        o.setActualReturnTime(rs.getDate("actualreturntime"));
        o.setEscorted(rs.getString("escorted"));
        o.setRelation(rs.getString("relation"));
        o.setEscortedTel(rs.getString("escortedtel"));
        o.setAuditStatus(rs.getInt("auditstatus"));
        o.setAuditPerson(rs.getString("auditperson"));
        o.setAuditTime(rs.getDate("audittime"));
        return o;
    }
}
