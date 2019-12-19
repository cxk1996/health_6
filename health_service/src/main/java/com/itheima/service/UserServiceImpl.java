package com.itheima.service;

import com.alibaba.dubbo.config.annotation.Service;
import com.itheima.dao.PermissionDao;
import com.itheima.dao.RoleDao;
import com.itheima.dao.UserDao;
import com.itheima.pojo.Permission;
import com.itheima.pojo.Role;
import com.itheima.pojo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;

@Service(interfaceClass = UserService.class)
@Transactional
public class UserServiceImpl implements UserService {

    @Autowired
    private UserDao userDao;

    @Autowired
    private RoleDao roleDao;

    @Autowired
    private PermissionDao permissionDao;

    @Override
    public User findUserByUsername(String username) {

        User user = userDao.findUserByUsername(username);

        Set<Role> roles = roleDao.findRolesByUserId(user.getId());
        user.setRoles(roles);
        for (Role role : roles) {
            Set<Permission> permissions = permissionDao.findPermissionByRoleId(role.getId());
            role.setPermissions(permissions);
        }

        return user;
    }

}
