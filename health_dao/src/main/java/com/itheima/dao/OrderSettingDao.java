package com.itheima.dao;

import com.itheima.pojo.OrderSetting;

import java.util.Date;
import java.util.List;
import java.util.Map;

public interface OrderSettingDao {

    /**
     * 查询预约设置是否存在
     * @param orderDate
     * @return
     */
    List<OrderSetting> findOrderSettingByDate(Date orderDate);

    /**
     * 更改预约设置
     * @param orderSetting
     */
    void updateOrderSetting(OrderSetting orderSetting);

    /**
     * 新增预约设置
     * @param orderSetting
     */
    void addOrderSetting(OrderSetting orderSetting);

    /**
     * 通过月份查询预约设置
     * @param map
     * @return
     */
    List<OrderSetting> getOrderSettingByMonth(Map<String, String> map);

    /**
     * 定时清理预约设置
     * @param date
     */
    void clearOrderSetting(Date date);
}
