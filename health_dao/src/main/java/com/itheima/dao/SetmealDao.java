package com.itheima.dao;

import com.github.pagehelper.Page;
import com.itheima.pojo.Setmeal;

import java.util.List;
import java.util.Map;

public interface SetmealDao {
    void add(Setmeal setmeal);

    void setMealAndGroup(Map<String, Integer> map);

    Page<Setmeal> findPage(String queryString);

    List<Setmeal> getSetmeal();

    Setmeal findById(Integer id);
}
