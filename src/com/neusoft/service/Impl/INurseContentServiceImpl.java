package com.neusoft.service.Impl;

import java.util.List;

import com.neusoft.dao.NurseContentDao;
import com.neusoft.dao.impl.NurseContentDaoImpl;
import com.neusoft.entity.NurseContent;
import com.neusoft.service.INurseContentService;

public class INurseContentServiceImpl implements INurseContentService {

    private NurseContentDao nurseContentDao = new NurseContentDaoImpl();

    @Override
    public List<NurseContent> list(Integer status, String name) {
        return nurseContentDao.list(status, name);
    }

    @Override
    public boolean add(NurseContent nurseContent) {
        return nurseContentDao.add(nurseContent);
    }

    @Override
    public boolean update(NurseContent nurseContent) {
        return nurseContentDao.update(nurseContent);
    }

    @Override
    public boolean delete(Long id) {
        return nurseContentDao.delete(id);
    }

    @Override
    public NurseContent getById(Long id) {
        return nurseContentDao.getById(id);
    }
}
