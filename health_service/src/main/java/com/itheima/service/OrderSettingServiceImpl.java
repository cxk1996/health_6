package com.itheima.service;

import com.alibaba.dubbo.config.annotation.Service;
import com.itheima.dao.OrderSettingDao;
import com.itheima.pojo.OrderSetting;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service(interfaceClass = OrderSettingService.class)
@Transactional
public class OrderSettingServiceImpl implements OrderSettingService {

    @Autowired
    private OrderSettingDao orderSettingDao;

    @Override
    public void add(List<OrderSetting> orderSettingList) {

        //循环遍历集合
        for (OrderSetting orderSetting : orderSettingList) {

            add(orderSetting);
        }
    }

    @Override
    public List<Map<String, Integer>> getOrderSettingByMonth(String date) {
        String dateBegin = date+"-1";
        String dateEnd = date+"-31";
        Map<String,String> map = new HashMap<>();
        map.put("dateBegin",dateBegin);
        map.put("dateEnd",dateEnd);
        List<OrderSetting> list = orderSettingDao.getOrderSettingByMonth(map);
        List<Map<String, Integer>> data = new ArrayList<>();
        for (OrderSetting orderSetting : list) {
            Map orderSettingMap = new HashMap();
            orderSettingMap.put("date",orderSetting.getOrderDate().getDate());//获得日期（几号）
            orderSettingMap.put("number",orderSetting.getNumber());//可预约人数
            orderSettingMap.put("reservations",orderSetting.getReservations());//已预约人数
            data.add(orderSettingMap);
        }
        return data;
    }

    @Override
    public void editNumberByDate(OrderSetting orderSetting) {
        add(orderSetting);
    }

    private void add(OrderSetting orderSetting) {
        Date orderDate = orderSetting.getOrderDate();
        if (findOrderSettingByDate(orderDate).size()>0) {
            //判断如果该日期预约设置已存在,则更改预约设置
            updateOrderSetting(orderSetting);
        }else {
            //如果不存在,则新增预约设置
            addOrderSetting(orderSetting);
        }
    }

    /**
     * 新增预约设置
     * @param orderSetting
     */
    private void addOrderSetting(OrderSetting orderSetting) {
        orderSettingDao.addOrderSetting(orderSetting);
    }

    /**
     * 更改预约设置
     * @param orderSetting
     */
    private void updateOrderSetting(OrderSetting orderSetting) {
        orderSettingDao.updateOrderSetting(orderSetting);
    }

    /**
     * 查询预约设置是否存在
     * @param orderDate
     * @return
     */
    private List<OrderSetting> findOrderSettingByDate(Date orderDate) {
        return orderSettingDao.findOrderSettingByDate(orderDate);
    }
}
