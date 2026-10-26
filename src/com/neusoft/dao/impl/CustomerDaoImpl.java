package com.neusoft.dao.impl;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import com.neusoft.dao.CustomerDao;
import com.neusoft.entity.Customer;
import com.neusoft.util.JDBCUtil;

public class CustomerDaoImpl implements CustomerDao {

    private static final String COLUMNS =
            "id, is_deleted, customer_name, customer_age, customer_sex, idcard, room_no, "
          + "building_no, checkin_date, expiration_date, contact_tel, bed_id, psychosomatic_state, "
          + "attention, birthday, height, weight, blood_type, filepath, user_id, level_id, family_member";

    @Override
    public List<Customer> listServedByGuard(int guardId, String customerName) {
        List<Customer> result = new ArrayList<>();
        try {
            JDBCUtil.initConnnection();
            StringBuilder sql = new StringBuilder(
                    "SELECT " + COLUMNS + " FROM customer WHERE is_deleted = 0 AND user_id = ? ");
            List<Object> params = new ArrayList<>();
            params.add(guardId);
            if (customerName != null && !customerName.isEmpty()) {
                sql.append(" AND customer_name LIKE ?");
                params.add("%" + customerName + "%");
            }
            sql.append(" ORDER BY id");
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
    public Customer getById(Long id) {
        try {
            JDBCUtil.initConnnection();
            JDBCUtil.initPreparedStatement("SELECT " + COLUMNS + " FROM customer WHERE id = ? AND is_deleted = 0");
            List<Object> params = new ArrayList<>();
            params.add(id);
            JDBCUtil.initPreparedSql(params);
            ResultSet rs = JDBCUtil.executeQuery();
            Customer c = rs.next() ? mapRow(rs) : null;
            rs.close();
            return c;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            JDBCUtil.close();
        }
    }

    @Override
    public String getGuardNickname(int userId) {
        try {
            JDBCUtil.initConnnection();
            JDBCUtil.initPreparedStatement("SELECT nickname FROM user WHERE id = ? AND is_deleted = 0");
            List<Object> params = new ArrayList<>();
            params.add(userId);
            JDBCUtil.initPreparedSql(params);
            ResultSet rs = JDBCUtil.executeQuery();
            String nickname = rs.next() ? rs.getString("nickname") : null;
            rs.close();
            return nickname;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            JDBCUtil.close();
        }
    }

    // ==================== 管理端方法实现 ====================

    @Override
    public int addCustomer(Customer customer) {
        try {
            JDBCUtil.initConnnection();
            String sql = "INSERT INTO customer (is_deleted, customer_name, customer_age, customer_sex, idcard, "
                    + "room_no, building_no, checkin_date, expiration_date, contact_tel, bed_id, "
                    + "psychosomatic_state, attention, birthday, height, weight, blood_type, "
                    + "user_id, level_id, family_member) VALUES (0, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
            List<Object> params = new ArrayList<>();
            params.add(customer.getCustomerName());
            params.add(customer.getCustomerAge());
            params.add(customer.getCustomerSex());
            params.add(customer.getIdcard());
            params.add(customer.getRoomNo());
            params.add(customer.getBuildingNo());
            params.add(customer.getCheckinDate());
            params.add(customer.getExpirationDate());
            params.add(customer.getContactTel());
            params.add(customer.getBedId());
            params.add(customer.getPsychosomaticState());
            params.add(customer.getAttention());
            params.add(customer.getBirthday());
            params.add(customer.getHeight());
            params.add(customer.getWeight());
            params.add(customer.getBloodType());
            params.add(customer.getUserId());
            params.add(customer.getLevelId());
            params.add(customer.getFamilyMember());
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
    public int updateCustomer(Customer customer) {
        try {
            JDBCUtil.initConnnection();
            StringBuilder sql = new StringBuilder("UPDATE customer SET ");
            List<Object> params = new ArrayList<>();
            if (customer.getCustomerName() != null) {
                sql.append("customer_name = ?, ");
                params.add(customer.getCustomerName());
            }
            if (customer.getCustomerAge() != 0) {
                sql.append("customer_age = ?, ");
                params.add(customer.getCustomerAge());
            }
            if (customer.getCustomerSex() != 0) {
                sql.append("customer_sex = ?, ");
                params.add(customer.getCustomerSex());
            }
            if (customer.getIdcard() != null) {
                sql.append("idcard = ?, ");
                params.add(customer.getIdcard());
            }
            if (customer.getRoomNo() != null) {
                sql.append("room_no = ?, ");
                params.add(customer.getRoomNo());
            }
            if (customer.getBuildingNo() != null) {
                sql.append("building_no = ?, ");
                params.add(customer.getBuildingNo());
            }
            if (customer.getCheckinDate() != null) {
                sql.append("checkin_date = ?, ");
                params.add(customer.getCheckinDate());
            }
            if (customer.getExpirationDate() != null) {
                sql.append("expiration_date = ?, ");
                params.add(customer.getExpirationDate());
            }
            if (customer.getContactTel() != null) {
                sql.append("contact_tel = ?, ");
                params.add(customer.getContactTel());
            }
            if (customer.getBedId() != 0) {
                sql.append("bed_id = ?, ");
                params.add(customer.getBedId());
            }
            if (customer.getLevelId() != 0) {
                sql.append("level_id = ?, ");
                params.add(customer.getLevelId());
            }
            if (customer.getFamilyMember() != null) {
                sql.append("family_member = ?, ");
                params.add(customer.getFamilyMember());
            }
            // 去掉最后一个逗号和空格
            sql.setLength(sql.length() - 2);
            sql.append(" WHERE id = ? AND is_deleted = 0");
            params.add(customer.getId());
            JDBCUtil.initPreparedStatement(sql.toString());
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
    public int logicDeleteCustomer(Long id) {
        try {
            JDBCUtil.initConnnection();
            String sql = "UPDATE customer SET is_deleted = 1 WHERE id = ?";
            List<Object> params = new ArrayList<>();
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
    public List<Customer> findCustomerPage(String customerName, Integer levelId, int pageIndex, int pageSize) {
        List<Customer> result = new ArrayList<>();
        try {
            JDBCUtil.initConnnection();
            StringBuilder sql = new StringBuilder(
                    "SELECT " + COLUMNS + " FROM customer WHERE is_deleted = 0 ");
            List<Object> params = new ArrayList<>();
            if (customerName != null && !customerName.isEmpty()) {
                sql.append(" AND customer_name LIKE ?");
                params.add("%" + customerName + "%");
            }
            if (levelId != null) {
                sql.append(" AND level_id = ?");
                params.add(levelId);
            }
            sql.append(" ORDER BY id LIMIT ?, ?");
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
    public long getCustomerTotal(String customerName, Integer levelId) {
        try {
            JDBCUtil.initConnnection();
            StringBuilder sql = new StringBuilder("SELECT COUNT(*) FROM customer WHERE is_deleted = 0 ");
            List<Object> params = new ArrayList<>();
            if (customerName != null && !customerName.isEmpty()) {
                sql.append(" AND customer_name LIKE ?");
                params.add("%" + customerName + "%");
            }
            if (levelId != null) {
                sql.append(" AND level_id = ?");
                params.add(levelId);
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

    private Customer mapRow(ResultSet rs) throws Exception {
        Customer c = new Customer();
        c.setId(rs.getLong("id"));
        c.setIsDeleted(rs.getInt("is_deleted"));
        c.setCustomerName(rs.getString("customer_name"));
        c.setCustomerAge(rs.getInt("customer_age"));
        c.setCustomerSex(rs.getInt("customer_sex"));
        c.setIdcard(rs.getString("idcard"));
        c.setRoomNo(rs.getString("room_no"));
        c.setBuildingNo(rs.getString("building_no"));
        c.setCheckinDate(rs.getDate("checkin_date"));
        c.setExpirationDate(rs.getDate("expiration_date"));
        c.setContactTel(rs.getString("contact_tel"));
        c.setBedId(rs.getInt("bed_id"));
        c.setPsychosomaticState(rs.getString("psychosomatic_state"));
        c.setAttention(rs.getString("attention"));
        c.setBirthday(rs.getDate("birthday"));
        c.setHeight(rs.getString("height"));
        c.setWeight(rs.getString("weight"));
        c.setBloodType(rs.getString("blood_type"));
        c.setFilepath(rs.getString("filepath"));
        c.setUserId(rs.getInt("user_id"));
        c.setLevelId(rs.getInt("level_id"));
        c.setFamilyMember(rs.getString("family_member"));
        return c;
    }
}
