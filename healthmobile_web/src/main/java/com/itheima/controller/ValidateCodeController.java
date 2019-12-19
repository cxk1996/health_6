package com.itheima.controller;

import com.aliyuncs.exceptions.ClientException;
import com.itheima.constant.MessageConstant;
import com.itheima.constant.RedisMessageConstant;
import com.itheima.entity.Result;
import com.itheima.utils.SMSUtils;
import com.itheima.utils.ValidateCodeUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import redis.clients.jedis.JedisPool;

@RestController
@RequestMapping("/validateCode")
public class ValidateCodeController {

    @Autowired
    private JedisPool jedisPool;

    @RequestMapping(value = "/send4Order",method = RequestMethod.POST)
    public Result send4Order(String telephone){

        try {
            // 生成4位验证码
            String param = ValidateCodeUtils.generateValidateCode(4).toString();
            // 发送验证码
            if (false) {
                SMSUtils.sendShortMessage(SMSUtils.VALIDATE_CODE,telephone,param);
            }
            System.out.println("telephone = "+telephone+"::::code = "+param);

            // 缓存验证码
            jedisPool.getResource().setex(telephone+"_"+ RedisMessageConstant.SENDTYPE_ORDER,300,param);

            return new Result(true, MessageConstant.SEND_VALIDATECODE_SUCCESS);

        } catch (ClientException e) {
            e.printStackTrace();
            return new Result(false, MessageConstant.SEND_VALIDATECODE_FAIL);
        }

    }

    @RequestMapping(value = "/send4Login",method = RequestMethod.POST)
    public Result send4Login(String telephone){
        try {
            // 生成4位验证码
            String param = ValidateCodeUtils.generateValidateCode(4).toString();
            // 发送验证码
            if (false) {
                SMSUtils.sendShortMessage(SMSUtils.ORDER_NOTICE,telephone,param);
            }
            System.out.println("telephone = "+telephone+"::::code = "+param);

            // 缓存验证码
            jedisPool.getResource().setex(telephone+"_"+ RedisMessageConstant.SENDTYPE_LOGIN,300,param);

            return new Result(true, MessageConstant.SEND_VALIDATECODE_SUCCESS);

        } catch (ClientException e) {
            e.printStackTrace();
            return new Result(false, MessageConstant.SEND_VALIDATECODE_FAIL);
        }
    }

}
