package com.neusoft.dao;

import java.util.List;

import com.neusoft.entity.User;

/**
 * 用户数据访问接口
 */
public interface UserDao {

    /**
     * 根据ID查单条用户
     */
    User getById(Long id);

    /**
     * 根据登录账号查用户
     */
    User getByUsername(String username);

    /**
     * 查询所有用户列表
     */
    List<User> findAll();

    /**
     * 新增用户
     */
    int addUser(User user);

    /**
     * 修改用户信息
     */
    int updateUser(User user);

    /**
     * 逻辑删除用户
     */
    int logicDeleteUser(Long id);
}
