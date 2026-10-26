package com.neusoft.dao.impl;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import com.neusoft.dao.UserDao;
import com.neusoft.entity.User;
import com.neusoft.util.JDBCUtil;

public class UserDaoImpl implements UserDao {

    private static final String COLUMNS = "id, username, password, nickname, phone_number, role_id, is_deleted";

    @Override
    public User getById(Long id) {
        try {
            JDBCUtil.initConnnection();
            JDBCUtil.initPreparedStatement("SELECT " + COLUMNS + " FROM user WHERE id = ? AND is_deleted = 0");
            List<Object> params = new ArrayList<>();
            params.add(id);
            JDBCUtil.initPreparedSql(params);
            ResultSet rs = JDBCUtil.executeQuery();
            User u = rs.next() ? mapRow(rs) : null;
            rs.close();
            return u;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            JDBCUtil.close();
        }
    }

    @Override
    public User getByUsername(String username) {
        try {
            JDBCUtil.initConnnection();
            JDBCUtil.initPreparedStatement("SELECT " + COLUMNS + " FROM user WHERE username = ? AND is_deleted = 0");
            List<Object> params = new ArrayList<>();
            params.add(username);
            JDBCUtil.initPreparedSql(params);
            ResultSet rs = JDBCUtil.executeQuery();
            User u = rs.next() ? mapRow(rs) : null;
            rs.close();
            return u;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            JDBCUtil.close();
        }
    }

    @Override
    public List<User> findAll() {
        List<User> result = new ArrayList<>();
        try {
            JDBCUtil.initConnnection();
            JDBCUtil.initPreparedStatement("SELECT " + COLUMNS + " FROM user WHERE is_deleted = 0 ORDER BY id");
            JDBCUtil.initPreparedSql(new ArrayList<>());
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
    public int addUser(User user) {
        try {
            JDBCUtil.initConnnection();
            String sql = "INSERT INTO user (username, password, nickname, phone_number, role_id, is_deleted) VALUES (?, ?, ?, ?, ?, 0)";
            List<Object> params = new ArrayList<>();
            params.add(user.getUsername());
            params.add(user.getPassword());
            params.add(user.getNickname());
            params.add(user.getPhone());
            params.add(user.getRole());
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
    public int updateUser(User user) {
        try {
            JDBCUtil.initConnnection();
            StringBuilder sql = new StringBuilder("UPDATE user SET ");
            List<Object> params = new ArrayList<>();
            if (user.getNickname() != null && !user.getNickname().isEmpty()) {
                sql.append("nickname = ?, ");
                params.add(user.getNickname());
            }
            if (user.getPhone() != null && !user.getPhone().isEmpty()) {
                sql.append("phone_number = ?, ");
                params.add(user.getPhone());
            }
            if (user.getRole() != null) {
                sql.append("role_id = ?, ");
                params.add(user.getRole());
            }
            if (user.getPassword() != null && !user.getPassword().isEmpty()) {
                sql.append("password = ?, ");
                params.add(user.getPassword());
            }
            if (params.isEmpty()) {
                System.out.println("没有要修改的内容");
                return 0;
            }
            sql.setLength(sql.length() - 2);
            sql.append(" WHERE id = ? AND is_deleted = 0");
            params.add(user.getId());
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
    public int logicDeleteUser(Long id) {
        try {
            JDBCUtil.initConnnection();
            String sql = "UPDATE user SET is_deleted = 1 WHERE id = ?";
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

    private User mapRow(ResultSet rs) throws Exception {
        User u = new User();
        u.setId(rs.getLong("id"));
        u.setUsername(rs.getString("username"));
        u.setPassword(rs.getString("password"));
        u.setNickname(rs.getString("nickname"));
        u.setPhone(rs.getString("phone_number"));
        u.setRole(rs.getInt("role_id"));
        u.setIsDeleted(rs.getInt("is_deleted"));
        return u;
    }
}
