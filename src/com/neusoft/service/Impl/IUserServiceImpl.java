package com.neusoft.service.Impl;

import java.util.List;

import com.neusoft.dao.UserDao;
import com.neusoft.dao.impl.UserDaoImpl;
import com.neusoft.entity.User;
import com.neusoft.service.IUserService;

public class IUserServiceImpl implements IUserService {

    private UserDao userDao = new UserDaoImpl();

    @Override
    public User getById(Long id) {
        return userDao.getById(id);
    }

    @Override
    public User getByUsername(String username) {
        return userDao.getByUsername(username);
    }

    @Override
    public List<User> findAll() {
        return userDao.findAll();
    }

    @Override
    public boolean addUser(User user) {
        return userDao.addUser(user) > 0;
    }

    @Override
    public boolean updateUser(User user) {
        return userDao.updateUser(user) > 0;
    }

    @Override
    public boolean logicDeleteUser(Long id) {
        return userDao.logicDeleteUser(id) > 0;
    }
}
