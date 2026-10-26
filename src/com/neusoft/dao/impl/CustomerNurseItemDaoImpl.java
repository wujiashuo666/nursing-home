package com.neusoft.dao.impl;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.neusoft.dao.CustomerNurseItemDao;
import com.neusoft.entity.CustomerNurseItem;
import com.neusoft.util.JDBCUtil;

public class CustomerNurseItemDaoImpl implements CustomerNurseItemDao {

    private static final String JOIN_SELECT =
            "SELECT cni.id AS id, cni.item_id AS itemId, nc.nursing_name AS itemName, "
          + "cni.customer_id AS customerId, c.customer_name AS customerName, "
          + "cni.level_id AS levelId, nl.level_name AS levelName, cni.nurse_number AS nurseNumber, "
          + "cni.buy_time AS buyTime, cni.maturity_time AS maturityTime, cni.is_deleted AS isDeleted "
          + "FROM customernurseitem cni "
          + "LEFT JOIN nursecontent nc ON nc.id = cni.item_id "
          + "LEFT JOIN customer c ON c.id = cni.customer_id "
          + "LEFT JOIN nurselevel nl ON nl.id = cni.level_id ";

    @Override
    public List<Map<String, Object>> list() {
        List<Map<String, Object>> result = new ArrayList<>();
        try {
            JDBCUtil.initConnnection();
            JDBCUtil.initPreparedStatement(JOIN_SELECT + "WHERE cni.is_deleted = 0 ORDER BY cni.id");
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
    public List<Map<String, Object>> getByCustomerId(Integer customerId) {
        List<Map<String, Object>> result = new ArrayList<>();
        try {
            JDBCUtil.initConnnection();
            JDBCUtil.initPreparedStatement(JOIN_SELECT + "WHERE cni.customer_id = ? AND cni.is_deleted = 0 ORDER BY cni.id");
            List<Object> params = new ArrayList<>();
            params.add(customerId);
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
    public boolean add(CustomerNurseItem cni) {
        try {
            JDBCUtil.initConnnection();
            JDBCUtil.initPreparedStatement(
                    "INSERT INTO customernurseitem(item_id, customer_id, level_id, nurse_number, "
                  + "is_deleted, buy_time, maturity_time) VALUES(?, ?, ?, ?, 0, ?, ?)");
            List<Object> params = new ArrayList<>();
            params.add(cni.getItemId());
            params.add(cni.getCustomerId());
            params.add(cni.getLevelId());
            params.add(cni.getNurseNumber());
            params.add(cni.getBuyTime());
            params.add(cni.getMaturityTime());
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
    public boolean update(CustomerNurseItem cni) {
        try {
            JDBCUtil.initConnnection();
            JDBCUtil.initPreparedStatement(
                    "UPDATE customernurseitem SET item_id = ?, customer_id = ?, level_id = ?, "
                  + "nurse_number = ?, buy_time = ?, maturity_time = ? WHERE id = ? AND is_deleted = 0");
            List<Object> params = new ArrayList<>();
            params.add(cni.getItemId());
            params.add(cni.getCustomerId());
            params.add(cni.getLevelId());
            params.add(cni.getNurseNumber());
            params.add(cni.getBuyTime());
            params.add(cni.getMaturityTime());
            params.add(cni.getId());
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
            JDBCUtil.initPreparedStatement("UPDATE customernurseitem SET is_deleted = 1 WHERE id = ?");
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
    public CustomerNurseItem getById(Long id) {
        try {
            JDBCUtil.initConnnection();
            JDBCUtil.initPreparedStatement(
                    "SELECT id, item_id, customer_id, level_id, nurse_number, is_deleted, "
                  + "buy_time, maturity_time FROM customernurseitem WHERE id = ? AND is_deleted = 0");
            List<Object> params = new ArrayList<>();
            params.add(id);
            JDBCUtil.initPreparedSql(params);
            ResultSet rs = JDBCUtil.executeQuery();
            CustomerNurseItem cni = rs.next() ? mapRow(rs) : null;
            rs.close();
            return cni;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            JDBCUtil.close();
        }
    }

    @Override
    public boolean incrementCount(Long id, int delta) {
        try {
            JDBCUtil.initConnnection();
            JDBCUtil.initPreparedStatement(
                    "UPDATE customernurseitem SET nurse_number = nurse_number + ? WHERE id = ? AND is_deleted = 0");
            List<Object> params = new ArrayList<>();
            params.add(delta);
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

    private Map<String, Object> mapJoinedRow(ResultSet rs) throws Exception {
        Map<String, Object> row = new HashMap<>();
        row.put("id", rs.getLong("id"));
        row.put("itemId", rs.getInt("itemId"));
        row.put("itemName", rs.getString("itemName"));
        row.put("customerId", rs.getInt("customerId"));
        row.put("customerName", rs.getString("customerName"));
        row.put("levelId", rs.getObject("levelId") == null ? null : rs.getInt("levelId"));
        row.put("levelName", rs.getString("levelName"));
        row.put("nurseNumber", rs.getInt("nurseNumber"));
        row.put("buyTime", rs.getString("buyTime"));
        row.put("maturityTime", rs.getString("maturityTime"));
        row.put("isDeleted", rs.getInt("isDeleted"));
        return row;
    }

    private CustomerNurseItem mapRow(ResultSet rs) throws Exception {
        CustomerNurseItem cni = new CustomerNurseItem();
        cni.setId(rs.getLong("id"));
        cni.setItemId(rs.getInt("item_id"));
        cni.setCustomerId(rs.getInt("customer_id"));
        cni.setLevelId(rs.getObject("level_id") == null ? null : rs.getInt("level_id"));
        cni.setNurseNumber(rs.getInt("nurse_number"));
        cni.setIsDeleted(rs.getInt("is_deleted"));
        cni.setBuyTime(rs.getDate("buy_time"));
        cni.setMaturityTime(rs.getDate("maturity_time"));
        return cni;
    }
}
