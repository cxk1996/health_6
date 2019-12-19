package com.itheima.dao;

import com.github.pagehelper.Page;
import com.itheima.pojo.CheckGroup;

import java.util.List;
import java.util.Map;

public interface CheckGroupDao {

    void addCheckGroup(CheckGroup checkGroup);

    void addCheckGroup_CheckItem(Map<String, Integer> map);

    Page<CheckGroup> queryByCondition(String queryString);

    CheckGroup findGroupById(Integer id);

    List<Integer> findItemIdsByGroupId(Integer id);

    void editCheckGroup(CheckGroup checkGroup);

    void deleteGroupAndItemByGroupId(Integer id);

    void delete(Integer id);

    List<CheckGroup> findAll();
}
