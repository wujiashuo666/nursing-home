package com.neusoft.dao.impl;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import com.neusoft.dao.NurseContentDao;
import com.neusoft.entity.NurseContent;
import com.neusoft.util.JDBCUtil;

public class NurseContentDaoImpl implements NurseContentDao {

    @Override
    public List<NurseContent> list(Integer status, String name) {
        List<NurseContent> result = new ArrayList<>();
        try {
            JDBCUtil.initConnnection();
            StringBuilder sql = new StringBuilder(
                    "SELECT id, serial_number, nursing_name, service_price, message, "
                  + "status, execution_cycle, execution_times, is_deleted "
                  + "FROM nursecontent WHERE is_deleted = 0 ");
            List<Object> params = new ArrayList<>();
            if (status != null) {
                sql.append(" AND status = ?");
                params.add(status);
            }
            if (name != null && !name.isEmpty()) {
                sql.append(" AND nursing_name LIKE ?");
                params.add("%" + name + "%");
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
    public boolean add(NurseContent nc) {
        try {
            JDBCUtil.initConnnection();
            JDBCUtil.initPreparedStatement(
                    "INSERT INTO nursecontent(serial_number, nursing_name, service_price, message, "
                  + "status, execution_cycle, execution_times, is_deleted) "
                  + "VALUES(?, ?, ?, ?, ?, ?, ?, 0)");
            List<Object> params = new ArrayList<>();
            params.add(nc.getSerialNumber());
            params.add(nc.getNursingName());
            params.add(nc.getServicePrice());
            params.add(nc.getMessage());
            params.add(nc.getStatus());
            params.add(nc.getExecutionCycle());
            params.add(nc.getExecutionTimes());
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
    public boolean update(NurseContent nc) {
        try {
            JDBCUtil.initConnnection();
            JDBCUtil.initPreparedStatement(
                    "UPDATE nursecontent SET serial_number = ?, nursing_name = ?, service_price = ?, "
                  + "message = ?, status = ?, execution_cycle = ?, execution_times = ? "
                  + "WHERE id = ? AND is_deleted = 0");
            List<Object> params = new ArrayList<>();
            params.add(nc.getSerialNumber());
            params.add(nc.getNursingName());
            params.add(nc.getServicePrice());
            params.add(nc.getMessage());
            params.add(nc.getStatus());
            params.add(nc.getExecutionCycle());
            params.add(nc.getExecutionTimes());
            params.add(nc.getId());
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
            JDBCUtil.initPreparedStatement("UPDATE nursecontent SET is_deleted = 1 WHERE id = ?");
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
    public NurseContent getById(Long id) {
        try {
            JDBCUtil.initConnnection();
            JDBCUtil.initPreparedStatement(
                    "SELECT id, serial_number, nursing_name, service_price, message, "
                  + "status, execution_cycle, execution_times, is_deleted "
                  + "FROM nursecontent WHERE id = ? AND is_deleted = 0");
            List<Object> params = new ArrayList<>();
            params.add(id);
            JDBCUtil.initPreparedSql(params);
            ResultSet rs = JDBCUtil.executeQuery();
            NurseContent nc = rs.next() ? mapRow(rs) : null;
            rs.close();
            return nc;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            JDBCUtil.close();
        }
    }

    private NurseContent mapRow(ResultSet rs) throws Exception {
        NurseContent nc = new NurseContent();
        nc.setId(rs.getLong("id"));
        nc.setSerialNumber(rs.getString("serial_number"));
        nc.setNursingName(rs.getString("nursing_name"));
        nc.setServicePrice(rs.getString("service_price"));
        nc.setMessage(rs.getString("message"));
        nc.setStatus(rs.getInt("status"));
        nc.setExecutionCycle(rs.getString("execution_cycle"));
        nc.setExecutionTimes(rs.getString("execution_times"));
        nc.setIsDeleted(rs.getInt("is_deleted"));
        return nc;
    }
}
