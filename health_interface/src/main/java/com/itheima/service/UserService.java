package com.itheima.service;

import com.itheima.entity.PageResult;
import com.itheima.entity.QueryPageBean;
import com.itheima.entity.Result;
import com.itheima.pojo.User;

public interface UserService {
    User findUserByUsername(String username);

    PageResult findPage(QueryPageBean queryPageBean);
}
