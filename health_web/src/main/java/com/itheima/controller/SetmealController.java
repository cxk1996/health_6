package com.itheima.controller;

import com.alibaba.druid.sql.PagerUtils;
import com.alibaba.dubbo.config.annotation.Reference;
import com.github.pagehelper.Page;
import com.itheima.constant.MessageConstant;
import com.itheima.constant.RedisConstant;
import com.itheima.entity.PageResult;
import com.itheima.entity.QueryPageBean;
import com.itheima.entity.Result;
import com.itheima.pojo.Setmeal;
import com.itheima.service.SetmealService;
import com.itheima.utils.QiniuUtils;
import org.springframework.security.core.parameters.P;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import redis.clients.jedis.JedisPool;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/setmeal")
public class SetmealController {
    private JedisPool jedisPool;

    @Reference
    private SetmealService setmealService;

    @RequestMapping("/upload")
    public Result upload(@RequestParam("imgFile") MultipartFile imgFile) {
        try {
            //获取文件原始名
            String originalFilename = imgFile.getOriginalFilename();
            int index = originalFilename.lastIndexOf(".");
            //获取文件后缀
            String suffix = originalFilename.substring(index - 1);

            String fileName = UUID.randomUUID().toString() + suffix;

            QiniuUtils.upload2Qiniu(imgFile.getBytes(), fileName);

            //文件上传成功 记录
            jedisPool.getResource().sadd(RedisConstant.SETMEAL_PIC_RESOURCES,fileName);

            return new Result(true, MessageConstant.PIC_UPLOAD_SUCCESS,fileName);
        } catch (IOException e) {
            e.printStackTrace();
            return new Result(false, MessageConstant.PIC_UPLOAD_FAIL);
        }
    }

    @RequestMapping(value = "/add",method = RequestMethod.POST)
    public Result add(@RequestBody Setmeal setmeal , Integer[] ids){

        try {
            setmealService.add(setmeal,ids);
            //记录新增套后 redis
            jedisPool.getResource().sadd(RedisConstant.SETMEAL_PIC_DB_RESOURCES,setmeal.getImg());
            return new Result(true,MessageConstant.ADD_SETMEAL_SUCCESS);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false,MessageConstant.ADD_SETMEAL_FAIL);
        }

    }

    @RequestMapping(value = "/findPage",method = RequestMethod.POST)
    public Result findPage(@RequestBody QueryPageBean queryPageBean){

        PageResult pageResult = setmealService.findPage(queryPageBean);
        if (pageResult.getRows()!=null&&pageResult.getRows().size()>0){
            return new Result(true,MessageConstant.QUERY_SETMEAL_SUCCESS,pageResult);
        }
        return new Result(false,MessageConstant.QUERY_SETMEAL_FAIL);

    }


}
