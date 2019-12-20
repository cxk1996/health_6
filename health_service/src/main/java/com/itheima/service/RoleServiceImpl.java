package com.itheima.service;

import com.alibaba.dubbo.config.annotation.Service;
import com.itheima.dao.RoleDao;
import com.itheima.pojo.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class RoleServiceImpl implements RoleService{
    @Autowired
    private RoleDao roleDao;

    @Override
    public List<Role> findAll() {
        return roleDao.findAll();
    }
}
