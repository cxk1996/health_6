package com.itheima.service;

import com.alibaba.dubbo.config.annotation.Service;
import com.itheima.dao.OrderDao;
import com.itheima.dao.OrderSettingDao;
import com.itheima.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

@Service
@Transactional
public class JobServiceImpl implements JobService {
    @Autowired
    private OrderSettingDao orderSettingDao;

    @Autowired
    private OrderDao orderDao;

    /**
     * 定时清理预约数据
     */
    @Override
    public void clearOrder() {
        //Date date = DateUtils.getToday();
        Date date = null;
        try {
            date = DateUtils.parseString2Date("2019-03-10");
        } catch (Exception e) {
            e.printStackTrace();
        }
        orderDao.clearOrder(date);
        orderSettingDao.clearOrderSetting(date);
    }

}
