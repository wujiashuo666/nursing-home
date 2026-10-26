package com.neusoft.controller;

import java.util.List;

import com.neusoft.entity.User;
import com.neusoft.service.IUserService;
import com.neusoft.service.Impl.IUserServiceImpl;

/**
 * 用户控制层
 */
public class UserController {
    private IUserService userService = new IUserServiceImpl();

    /**
     * 根据ID查单条用户
     */
    public User getById(Long id) {
        return userService.getById(id);
    }

    /**
     * 根据登录账号查用户
     */
    public User getByUsername(String username) {
        return userService.getByUsername(username);
    }

    /**
     * 查询所有用户列表
     */
    public List<User> findAll() {
        return userService.findAll();
    }

    /**
     * 新增用户
     */
    public String addUser(User user) {
        try {
            boolean res = userService.addUser(user);
            return res ? "用户添加成功" : "用户添加失败";
        } catch (RuntimeException e) {
            return "操作失败：" + e.getMessage();
        }
    }

    /**
     * 修改用户信息
     */
    public String updateUser(User user) {
        try {
            boolean res = userService.updateUser(user);
            return res ? "用户信息修改成功" : "用户信息修改失败";
        } catch (RuntimeException e) {
            return "操作失败：" + e.getMessage();
        }
    }

    /**
     * 逻辑删除用户
     */
    public String logicDeleteUser(Long id) {
        try {
            boolean res = userService.logicDeleteUser(id);
            return res ? "用户删除成功" : "用户删除失败";
        } catch (RuntimeException e) {
            return "操作失败：" + e.getMessage();
        }
    }
}
