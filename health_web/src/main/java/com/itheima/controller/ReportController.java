package com.itheima.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.itheima.constant.MessageConstant;
import com.itheima.entity.Result;
import com.itheima.service.ReportService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.text.SimpleDateFormat;
import java.util.*;

@RestController
@RequestMapping("report")
public class ReportController {

    @Reference
    private ReportService reportService;

    /**
     * 会员数量折线图
     */
    @RequestMapping(value = "/getMemberReport",method = RequestMethod.GET)
    public Result getMemberReport(){
        Calendar calendar = Calendar.getInstance();//得到日历对象
        calendar.add(Calendar.MONTH,-12);//一年前时间
        //定义list集合存放年月
        List<String> stringList = new ArrayList<>();
        for (int i = 0;i<12;i++){
            calendar.add(Calendar.MONTH,1);//循环每次+1个月
            stringList.add(new SimpleDateFormat("yyyy-MM").format(calendar.getTime()));//当前时间放入list集合中
        }
        //调用服务获取年月对应会员人数
        List<Integer> integerList =  reportService.getMembersByYearMoth(stringList);

        //months memberCount
        Map<String,Object> map = new HashMap<>();
        map.put("months",stringList);
        map.put("memberCount",integerList);
        return new Result(true, MessageConstant.GET_MEMBER_NUMBER_REPORT_SUCCESS,map);
    }

}
