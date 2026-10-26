package com.neusoft.dao.impl;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import com.neusoft.dao.NurseLevelItemDao;
import com.neusoft.entity.NurseLevelItem;
import com.neusoft.util.JDBCUtil;

public class NurseLevelItemDaoImpl implements NurseLevelItemDao {

    @Override
    public boolean add(Integer levelId, Integer itemId) {
        try {
            JDBCUtil.initConnnection();
            JDBCUtil.initPreparedStatement(
                    "INSERT INTO nurselevelitem(level_id, item_id) VALUES(?, ?)");
            List<Object> params = new ArrayList<>();
            params.add(levelId);
            params.add(itemId);
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
            JDBCUtil.initPreparedStatement("DELETE FROM nurselevelitem WHERE id = ?");
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
    public boolean update(NurseLevelItem item) {
        try {
            JDBCUtil.initConnnection();
            JDBCUtil.initPreparedStatement(
                    "UPDATE nurselevelitem SET level_id = ?, item_id = ? WHERE id = ?");
            List<Object> params = new ArrayList<>();
            params.add(item.getLevelId());
            params.add(item.getItemId());
            params.add(item.getId());
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
    public NurseLevelItem getById(Long id) {
        try {
            JDBCUtil.initConnnection();
            JDBCUtil.initPreparedStatement(
                    "SELECT id, level_id, item_id FROM nurselevelitem WHERE id = ?");
            List<Object> params = new ArrayList<>();
            params.add(id);
            JDBCUtil.initPreparedSql(params);
            ResultSet rs = JDBCUtil.executeQuery();
            NurseLevelItem item = rs.next() ? mapRow(rs) : null;
            rs.close();
            return item;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            JDBCUtil.close();
        }
    }

    @Override
    public List<NurseLevelItem> list() {
        List<NurseLevelItem> result = new ArrayList<>();
        try {
            JDBCUtil.initConnnection();
            JDBCUtil.initPreparedStatement(
                    "SELECT id, level_id, item_id FROM nurselevelitem ORDER BY id");
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
    public List<Integer> getItemIdsByLevelId(Integer levelId) {
        List<Integer> result = new ArrayList<>();
        try {
            JDBCUtil.initConnnection();
            JDBCUtil.initPreparedStatement(
                    "SELECT item_id FROM nurselevelitem WHERE level_id = ? ORDER BY item_id");
            List<Object> params = new ArrayList<>();
            params.add(levelId);
            JDBCUtil.initPreparedSql(params);
            ResultSet rs = JDBCUtil.executeQuery();
            while (rs.next()) {
                result.add(rs.getInt("item_id"));
            }
            rs.close();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            JDBCUtil.close();
        }
        return result;
    }

    private NurseLevelItem mapRow(ResultSet rs) throws Exception {
        NurseLevelItem item = new NurseLevelItem();
        item.setId(rs.getLong("id"));
        item.setLevelId(rs.getInt("level_id"));
        item.setItemId(rs.getInt("item_id"));
        return item;
    }
}
