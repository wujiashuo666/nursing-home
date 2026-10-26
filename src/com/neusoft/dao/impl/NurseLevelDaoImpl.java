package com.neusoft.dao.impl;

import com.neusoft.dao.NurseLevelDao;
import com.neusoft.entity.NurseLevel;
import com.neusoft.util.JDBCUtil;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class NurseLevelDaoImpl implements NurseLevelDao {

    @Override
    public List<NurseLevel> list() {
        List<NurseLevel> result = new ArrayList<>();
        try {
            JDBCUtil.initConnnection();
            JDBCUtil.initPreparedStatement(
                    "SELECT id, level_name, level_status, is_deleted "
                  + "FROM nurselevel WHERE is_deleted = 0 ORDER BY id");
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
    public boolean add(NurseLevel nl) {
        try {
            JDBCUtil.initConnnection();
            JDBCUtil.initPreparedStatement(
                    "INSERT INTO nurselevel(level_name, level_status, is_deleted) VALUES(?, ?, 0)");
            List<Object> params = new ArrayList<>();
            params.add(nl.getLevelName());
            params.add(nl.getLevelStatus());
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
    public boolean update(NurseLevel nl) {
        try {
            JDBCUtil.initConnnection();
            JDBCUtil.initPreparedStatement(
                    "UPDATE nurselevel SET level_name = ?, level_status = ? WHERE id = ? AND is_deleted = 0");
            List<Object> params = new ArrayList<>();
            params.add(nl.getLevelName());
            params.add(nl.getLevelStatus());
            params.add(nl.getId());
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
            JDBCUtil.initPreparedStatement("UPDATE nurselevel SET is_deleted = 1 WHERE id = ?");
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
    public NurseLevel getById(Long id) {
        try {
            JDBCUtil.initConnnection();
            JDBCUtil.initPreparedStatement(
                    "SELECT id, level_name, level_status, is_deleted "
                  + "FROM nurselevel WHERE id = ? AND is_deleted = 0");
            List<Object> params = new ArrayList<>();
            params.add(id);
            JDBCUtil.initPreparedSql(params);
            ResultSet rs = JDBCUtil.executeQuery();
            NurseLevel nl = rs.next() ? mapRow(rs) : null;
            rs.close();
            return nl;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            JDBCUtil.close();
        }
    }

    private NurseLevel mapRow(ResultSet rs) throws Exception {
        NurseLevel nl = new NurseLevel();
        nl.setId(rs.getLong("id"));
        nl.setLevelName(rs.getString("level_name"));
        nl.setLevelStatus(rs.getInt("level_status"));
        nl.setIsDeleted(rs.getInt("is_deleted"));
        return nl;
    }
}