package com.itheima.dao;

import com.itheima.pojo.Order;

import java.util.Date;
import java.util.List;
import java.util.Map;

public interface OrderDao {
    List<Order> findOrderByIdAndDate(Order order);

    void add(Order order);

    Map findById(Integer id);

    Integer findOrderCountByDate(String reportDate);

    Integer findOrderCountAfterDate(String thisWeekMonday);

    Integer findVisitsCountByDate(String reportDate);

    Integer findVisitsCountAfterDate(String thisWeekMonday);

    /**
     * 定时清理预约数据
     * @param date
     */
    void clearOrder(Date date);
}
