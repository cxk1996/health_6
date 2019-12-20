package com.itheima.service;

import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.itheima.constant.MessageConstant;
import com.itheima.dao.PermissionDao;
import com.itheima.dao.RoleDao;
import com.itheima.dao.UserDao;
import com.itheima.entity.PageResult;
import com.itheima.entity.QueryPageBean;
import com.itheima.entity.Result;
import com.itheima.pojo.Permission;
import com.itheima.pojo.Role;
import com.itheima.pojo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
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

    /**
     * 获取当前用户信息
     * @param username
     * @return
     */
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

    /**
     * 用户分页查询
     * @param queryPageBean
     * @return
     */
    @Override
    public PageResult findPage(QueryPageBean queryPageBean) {

        // 1.设置分页参数(当前页码,每页显示记录数)
        PageHelper.startPage(queryPageBean.getCurrentPage(),queryPageBean.getPageSize());

        Page<User> userPage = userDao.findPage(queryPageBean.getQueryString());


        return new PageResult(userPage.getTotal(),userPage.getResult());
    }

}
