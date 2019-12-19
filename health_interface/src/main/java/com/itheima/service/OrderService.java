package com.itheima.service;

import com.itheima.entity.Result;
import com.itheima.pojo.Order;

import java.util.Map;

public interface OrderService {
    Result submitOrder(Map map) throws Exception;

    Map findById(Integer id);
}
