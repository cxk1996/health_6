package com.itheima.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.aliyuncs.exceptions.ClientException;
import com.itheima.constant.MessageConstant;
import com.itheima.constant.RedisMessageConstant;
import com.itheima.entity.Result;
import com.itheima.pojo.Order;
import com.itheima.service.OrderService;
import com.itheima.utils.SMSUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import redis.clients.jedis.JedisPool;

import java.util.Map;

@RestController
@RequestMapping("/order")
public class OrderController {

    @Reference
    private OrderService orderService;
    @Autowired
    private JedisPool jedisPool;
    
    @RequestMapping(value = "/submit",method = RequestMethod.POST)
    public Result submitOrder(@RequestBody Map map){

        String telephone = (String) map.get("telephone");

        String codeInRedis = jedisPool.getResource().get(telephone + "_" + RedisMessageConstant.SENDTYPE_ORDER);

        Object code = map.get("validateCode");

        Result result = null;
        if (codeInRedis !=null && codeInRedis.equals(code)){
            // 校验手机验证码成功
            try {
                map.put("orderType", Order.ORDERTYPE_WEIXIN);

                result = orderService.submitOrder(map);

            } catch (Exception e) {
                e.printStackTrace();
                return result;
            }
            if (result.isFlag()){
                //预约成功，发送短信通知
                String orderDate = (String) map.get("orderDate");

                try {
                    SMSUtils.sendShortMessage(SMSUtils.ORDER_NOTICE,telephone,orderDate);
                } catch (ClientException e) {
                    e.printStackTrace();
                }
            }
            return result;

        }else {
            //校验手机验证码失败
            return new Result(false,MessageConstant.VALIDATECODE_ERROR);
        }

    }

    @RequestMapping(value = "/findById",method = RequestMethod.POST)
    public Result findById(Integer id){
        try {
            Map map = orderService.findById(id);
            return new Result(true,MessageConstant.QUERY_ORDER_SUCCESS,map);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(true,MessageConstant.QUERY_ORDER_FAIL);
        }
    }

}
