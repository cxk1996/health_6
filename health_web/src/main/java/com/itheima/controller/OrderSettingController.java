package com.itheima.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.itheima.constant.MessageConstant;
import com.itheima.entity.Result;
import com.itheima.pojo.OrderSetting;
import com.itheima.service.OrderSettingService;
import com.itheima.utils.POIUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/ordersetting")
public class OrderSettingController {

    @Reference
    private OrderSettingService orderSettingService;

    @RequestMapping("/upload")
    public Result upload(@RequestParam("excelFile") MultipartFile file) {

        try {
            List<String[]> list = POIUtils.readExcel(file);

            if (list != null && list.size() > 0) {
                List<OrderSetting> orderSettingList = new ArrayList<>();
                for (String[] strings : list) {
                    OrderSetting orderSetting = new OrderSetting(new Date(strings[0]), Integer.parseInt(strings[1]));
                    orderSettingList.add(orderSetting);
                }

                orderSettingService.add(orderSettingList);
                return new Result(true, MessageConstant.IMPORT_ORDERSETTING_SUCCESS);
            } else {

                return new Result(false, "上传文件为空");
            }
        } catch (IOException e) {
            e.printStackTrace();
            return new Result(false, MessageConstant.IMPORT_ORDERSETTING_FAIL);
        }

    }

    @RequestMapping(value = "/getOrderSettingByMonth",method = RequestMethod.GET)
    public Result getOrderSettingByMonth(String date){

        try {
            List<Map<String,Integer>> list = orderSettingService.getOrderSettingByMonth(date);
            return new Result(true,MessageConstant.GET_ORDERSETTING_SUCCESS,list);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(true,MessageConstant.GET_ORDERSETTING_FAIL);
        }
    }

    @RequestMapping(value = "/editNumberByDate",method = RequestMethod.POST)
    public Result editNumberByDate(@RequestBody OrderSetting orderSetting){

        try {
            orderSettingService.editNumberByDate(orderSetting);
            return new Result(true,MessageConstant.ORDERSETTING_SUCCESS);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(true,MessageConstant.ORDERSETTING_FAIL);
        }

    }

}
