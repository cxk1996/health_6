package com.itheima.service;

import com.itheima.entity.PageResult;
import com.itheima.entity.QueryPageBean;
import com.itheima.entity.Result;
import com.itheima.pojo.CheckItem;

import java.util.List;

public interface CheckItemService {

    void add(CheckItem checkItem);

    PageResult findPage(QueryPageBean queryPageBean);

    int findById(int id);

    void deleteById(int id);


    CheckItem editFindById(Integer id);

    void edit(CheckItem checkItem);

    List<CheckItem> findAll();

}
