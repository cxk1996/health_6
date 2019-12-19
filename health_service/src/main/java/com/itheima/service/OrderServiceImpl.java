package com.itheima.service;

import com.alibaba.dubbo.config.annotation.Service;
import com.itheima.constant.MessageConstant;
import com.itheima.dao.MemberDao;
import com.itheima.dao.OrderDao;
import com.itheima.dao.OrderSettingDao;
import com.itheima.entity.Result;
import com.itheima.pojo.Member;
import com.itheima.pojo.Order;
import com.itheima.pojo.OrderSetting;
import com.itheima.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Map;

@Service(interfaceClass = OrderService.class)
@Transactional
public class OrderServiceImpl implements OrderService {
    @Autowired
    private OrderDao orderDao;

    @Autowired
    private OrderSettingDao orderSettingDao;

    @Autowired
    private MemberDao memberDao;


    @Override
    public Result submitOrder(Map map) throws Exception {
        // 1.判断当前日期是否设置了预约
        String orderDate = (String) map.get("orderDate");
        Date date = DateUtils.parseString2Date(orderDate);
        List<OrderSetting> orderSettings = orderSettingDao.findOrderSettingByDate(date);
        if (orderSettings == null || orderSettings.size() == 0) {
            return new Result(false, MessageConstant.SELECTED_DATE_CANNOT_ORDER);
        }

        // 2.判断当前日期是否已预约满
        OrderSetting orderSetting = orderSettings.get(0);
        if (orderSetting.getReservations() >= orderSetting.getNumber()) {
            return new Result(false, MessageConstant.ORDER_FULL);
        }

        // 3.判断是否是会员
        String telephone = (String) map.get("telephone");
        List<Member> members = memberDao.selectByCondition(telephone);
        Member member = null;

        // 3.1 是:判断是否已经预约
        Order order = null;
        if (members !=null && members.size()>0) {
            member = members.get(0);
            Integer setmealId = (Integer) map.get("setmealId");
            Integer memberId = member.getId();
            order = new Order(memberId, date, null, null, setmealId);
            List<Order> orders = orderDao.findOrderByIdAndDate(order);
            if (orders != null && orders.size() > 0) {
                return new Result(false, MessageConstant.HAS_ORDERED);
            }
        } else {
            // 3.1 否:添加为会员
            member = new Member();
            member.setName((String) map.get("name"));
            member.setPhoneNumber(telephone);
            member.setIdCard((String) map.get("idCard"));
            member.setSex((String) map.get("sex"));
            member.setRegTime(new Date());
            memberDao.add(member);
        }

        // 4.添加预约
        // 4.1 预约数加一
        orderSetting.setReservations(orderSetting.getReservations() + 1);
        orderSettingDao.updateOrderSetting(orderSetting);

        // 4.2 保存预约数据
        order = new Order(member.getId(),
                date,
                (String)map.get("orderType"),
                Order.ORDERSTATUS_NO,
                Integer.parseInt((String) map.get("setmealId")));

        orderDao.add(order);


        return new Result(true, MessageConstant.ORDER_SUCCESS, order);
    }

    @Override
    public Map findById(Integer id) {

        return orderDao.findById(id);
    }
}
