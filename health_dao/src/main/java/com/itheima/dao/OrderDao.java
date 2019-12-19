package com.itheima.dao;

import com.itheima.pojo.Order;

import java.util.Date;
import java.util.List;
import java.util.Map;

public interface OrderDao {
    List<Order> findOrderByIdAndDate(Order order);

    void add(Order order);

    Map findById(Integer id);
}
