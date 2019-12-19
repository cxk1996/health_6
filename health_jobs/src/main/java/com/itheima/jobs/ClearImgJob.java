package com.itheima.jobs;

import com.itheima.constant.RedisConstant;
import com.itheima.utils.QiniuUtils;
import org.springframework.beans.factory.annotation.Autowired;
import redis.clients.jedis.JedisPool;

import java.util.Set;

public class ClearImgJob {

    @Autowired
    private JedisPool jedisPool;

    public void clearImg() {

        //1.计算set的差值
        Set<String> set = jedisPool.getResource().sdiff(RedisConstant.SETMEAL_PIC_RESOURCES, RedisConstant.SETMEAL_PIC_DB_RESOURCES);
        //2.遍历集合
        //1.获取两个集合差值
        Set<String> fileNames = jedisPool.getResource().sdiff(RedisConstant.SETMEAL_PIC_RESOURCES, RedisConstant.SETMEAL_PIC_DB_RESOURCES);
        //2.遍历集合，
        if (fileNames != null && fileNames.size() > 0) {
            for (String fileName : fileNames) {
                //循环调用七牛云删除图片方法，删除垃圾图片
                QiniuUtils.deleteFileFromQiniu(fileName);
                //3.删除setmealPicResources中垃圾图片记录
                jedisPool.getResource().srem(RedisConstant.SETMEAL_PIC_RESOURCES, fileName);
            }
        }
    }

}
