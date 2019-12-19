package com.itheima.dao;

import com.github.pagehelper.Page;
import com.itheima.entity.PageResult;
import com.itheima.entity.QueryPageBean;
import com.itheima.pojo.CheckItem;

import java.util.List;

public interface CheckItemDao {

    void add(CheckItem checkItem);

    Page<CheckItem> queryByCondition(String queryString);

    int findById(int id);

    void deleteById(int id);

    CheckItem editFindById(Integer id);

    void edit(CheckItem checkItem);

    List<CheckItem> findAll();
}
