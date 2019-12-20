package com.itheima.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.itheima.constant.MessageConstant;
import com.itheima.entity.Result;
import com.itheima.pojo.Role;
import com.itheima.service.RoleService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/role")
public class RoleController {

    @Reference
    private RoleService roleService;

    @RequestMapping(value = "/findAll",method = RequestMethod.GET)
    public Result findAll() {
        try {
            List<Role> roleList = roleService.findAll();
            if (roleList != null && roleList.size() > 0) {
                return new Result(true, MessageConstant.GET_ROLE_SUCCESS, roleList);
            } else {
                return new Result(false, MessageConstant.GET_ROLE_FAIL);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, MessageConstant.GET_ROLE_FAIL);
        }
    }
}
