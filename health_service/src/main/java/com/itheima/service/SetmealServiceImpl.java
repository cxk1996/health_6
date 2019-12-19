package com.itheima.service;

import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.itheima.dao.SetmealDao;
import com.itheima.entity.PageResult;
import com.itheima.entity.QueryPageBean;
import com.itheima.pojo.Setmeal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service(interfaceClass = SetmealService.class)
@Transactional
public class SetmealServiceImpl implements SetmealService {
    @Autowired
    private SetmealDao setmealDao;

    @Override
    public void add(Setmeal setmeal, Integer[] ids) {
        /**
         * 添加新增套餐
         */
        setmealDao.add(setmeal);

        this.setMealAndGroup(setmeal.getId(),ids);
    }

    @Override
    public PageResult findPage(QueryPageBean queryPageBean) {
        // 1.设置分页参数(当前页码,每页显示记录数)
        PageHelper.startPage(queryPageBean.getCurrentPage(),queryPageBean.getPageSize());

        Page<Setmeal> setmealPage = setmealDao.findPage(queryPageBean.getQueryString());

        return new PageResult(setmealPage.getTotal(),setmealPage.getResult());
    }

    @Override
    public List<Setmeal> getSetmeal() {
        return setmealDao.getSetmeal();
    }

    @Override
    public Setmeal findById(Integer id) {
        return setmealDao.findById(id);
    }

    public void setMealAndGroup(Integer setmeal_id,Integer[] ids){

        for (Integer id : ids) {
            Map<String,Integer> map = new HashMap<>();
            map.put("setmeal_id",setmeal_id);
            map.put("checkgroup_id",id);
            setmealDao.setMealAndGroup(map);
        }

    }
}
