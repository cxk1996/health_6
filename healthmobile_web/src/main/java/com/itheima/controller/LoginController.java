package com.itheima.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.itheima.constant.MessageConstant;
import com.itheima.constant.RedisMessageConstant;
import com.itheima.entity.Result;
import com.itheima.pojo.Member;
import com.itheima.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import redis.clients.jedis.JedisPool;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/login")
public class LoginController {

    @Autowired
    private JedisPool jedisPool;

    @Reference
    private MemberService memberService;

    @RequestMapping(value = "/check", method = RequestMethod.POST)
    public Result login(@RequestBody Map map, HttpServletResponse response) {

        String telephone = (String) map.get("telephone");
        String validateCode = (String) map.get("validateCode");

        String codeInRedis = jedisPool.getResource().get(telephone+"_"+ RedisMessageConstant.SENDTYPE_LOGIN);

        if (validateCode == null || codeInRedis == null || !validateCode.equals(codeInRedis)) {
            return new Result(false, MessageConstant.VALIDATECODE_ERROR);
        } else {
            List<Member> members = memberService.selectByCondition(telephone);
            if (members == null){
                Member member = new Member();
                member.setPhoneNumber(telephone);
                member.setRegTime(new Date());
                memberService.add(member);
            }
            Cookie cookie = new Cookie("member_telephone",telephone);
            cookie.setPath("/");
            cookie.setMaxAge(60*60*24*30);
            response.addCookie(cookie);
            return new Result(true,MessageConstant.LOGIN_SUCCESS);
        }

    }
}
