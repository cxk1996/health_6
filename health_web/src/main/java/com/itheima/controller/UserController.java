package com.itheima.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.itheima.constant.MessageConstant;
import com.itheima.entity.PageResult;
import com.itheima.entity.QueryPageBean;
import com.itheima.entity.Result;
import com.itheima.service.UserService;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
public class UserController {

    @Reference
    private UserService userService;
    /**
     * 通过SecurityContext获取认证对象，最终通过认证对象获取用户对象
     */
    @RequestMapping(value = "/findUserName",method = RequestMethod.GET)
    public Result findUserName() {
        //getPrincipal() 用户对象 可以使用User强转
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return new Result(true, MessageConstant.GET_USERNAME_SUCCESS,user.getUsername());
    }

    @RequestMapping(value = "/findPage",method = RequestMethod.POST)
    public PageResult findPage(@RequestBody QueryPageBean queryPageBean){

        return userService.findPage(queryPageBean);
    }
}
