package com.itheima.dao;

import com.github.pagehelper.Page;
import com.itheima.pojo.User;

public interface UserDao {
    User findUserByUsername(String username);

    Page<User> findPage(String queryString);
}
