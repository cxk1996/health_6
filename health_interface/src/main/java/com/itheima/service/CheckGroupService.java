package com.itheima.service;

import com.itheima.entity.PageResult;
import com.itheima.entity.QueryPageBean;
import com.itheima.pojo.CheckGroup;

import java.util.List;

public interface CheckGroupService {
    void add(CheckGroup checkGroup, Integer[] ids);

    PageResult findPage(QueryPageBean queryPageBean);

    CheckGroup findGroupById(Integer groupId);

    List<Integer> findItemIdsByGroupId(Integer groupId);

    void edit(CheckGroup checkGroup, Integer[] itemIds);

    void delete(Integer id);

    List<CheckGroup> findAll();

}
